package com.medkha.lol_notes.services.impl;

import com.medkha.lol_notes.dto.*;
import com.medkha.lol_notes.dto.enums.GameTrackingStatus;
import com.medkha.lol_notes.services.LiveGameService;
import com.medkha.lol_notes.services.RiotLookUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class RiotLookUpServiceImpl implements RiotLookUpService {

    private static final Logger log = LoggerFactory.getLogger(RiotLookUpServiceImpl.class);
    private static final long  __RETRY_PERIOD__ = 4;
    private LiveGameService liveGameService;

    @Value("${lol_notes.dev-key}")
    private Resource devKeyResource;
    private String devKey;
    private final RestTemplate restTemplate;
    public RiotLookUpServiceImpl(RestTemplate restTemplate, @Lazy LiveGameService liveGameService) {
        this.restTemplate = restTemplate;
        this.liveGameService = liveGameService;
    }

    @PostConstruct
    private void postConstruct() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(devKeyResource.getInputStream()));
            this.devKey = reader.readLine();
        } catch (IOException e) {
            throw new IllegalStateException("Couldn't read the file specified in the property `lol_notes.dev-key`");
        }
    }
    @Override
    @Async
    public CompletableFuture<PlayerDTO> getActivePlayerInLiveGameAsync() {
        log.info("Looking up active Player in the live game");

        return getCall(
                () -> {
                    PlayerDTO playerDTO = restTemplate.getForObject("https://localhost:2999/liveclientdata/activeplayer", PlayerDTO.class);
                    playerDTO.id = restTemplate.getForObject("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + playerDTO.summonerName + "?api_key=" + devKey, IdPlayerDTO.class).id;
                    return playerDTO;
                }
        );
    }

    @Override
    @Async
    public CompletableFuture<List<PlayerDTO>> getAllPlayersInLiveGameAsync() {
        log.info("Looking up for all players in the live game");
        return getCall(
                () -> {
                    ResponseEntity<List<PlayerDTO>> playerListResponse =
                            restTemplate.exchange("https://localhost:2999/liveclientdata/playerlist",
                                    HttpMethod.GET, null, new ParameterizedTypeReference<List<PlayerDTO>>() {
                                    });
                    return  playerListResponse.getBody();
                }
        );
    }

    @Override
    @Async
    public CompletableFuture<LiveGameDTO> getLiveGameAsync() {
        log.info("looking up for live game general information");
        return  getActivePlayerInLiveGameAsync().thenApply(
                activePlayer ->
                {
                    try {
                        return getCall(
                            () ->{
                                LiveGameDTO liveGame = restTemplate.getForObject("https://localhost:2999/liveclientdata/gamestats", LiveGameDTO.class);
                                if(liveGame.gameMode.equals("PRACTICETOOL") || liveGame.gameMode.equals("CUSTOM")) {
                                    //TODO remove this after test, i don't want to save practicetool games.
                                    return liveGame;
                                }
                                return restTemplate.getForObject("https://euw1.api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + activePlayer.id + "?api_key=" + devKey, LiveGameDTO.class);
                            }

                        ).get();
                    } catch (InterruptedException| ExecutionException e) {
                        log.error(e.getMessage() + " [stack] : " + e.getStackTrace() );
                        throw new IllegalStateException(e.getMessage());
                    }
                }
        );

    }

    @Override
    public CompletableFuture<AllEventsDTO> getEventsAsync() {
        CompletableFuture<AllEventsDTO> allEventsFuture = getCall( () ->
                restTemplate.getForObject("https://127.0.0.1:2999/liveclientdata/eventdata", AllEventsDTO.class)
        );
//        if(isEndOfGame(allEventsFuture)) {
//            throw new EndOfGameException("Game Ends.");
//        }
        return allEventsFuture;
    }

    /**
     *
     * @param userName
     * @param sizeOptional (default is 20, max is 100 if it exceeds the max, it is considered 20.) TODO: MAKE it possible to exceeds 1000
     * @return
     */
    @Override
    public CompletableFuture<Set<GameFinishedDTO>> getMatchHistory( String userName, Optional<Integer> queueId,  Optional<Integer> sizeOptional) {
        //TODO: take in consideration the amount of data to get.
        //20 requests every 1 seconds(s)
        //100 requests every 2 minutes(s)
        int size = sizeOptional.orElse(20);
        final int __TIME_LIMIT__ = (size<100)? 80: 1300;

        Set<GameFinishedDTO> matchHistory = new HashSet<>(size,1);
        String puuid = restTemplate.getForObject("https://euw1.api.riotgames.com/lol/summoner/v4/summoners/by-name/" + userName + "?api_key=" + devKey , IdPlayerDTO.class).puuid;
        do{
            try{
                ResponseEntity<Set<String>> matchIdList =
                        restTemplate.exchange("https://europe.api.riotgames.com/lol/match/v5/matches/by-puuid/" + puuid + "/ids?start=" + (sizeOptional.orElse(20) - size) + "&count=" + ((size>=100)?100:size) + queueId.map(id->"&queue=" + id).orElse("") + "&api_key="+ devKey,
                                HttpMethod.GET, null, new ParameterizedTypeReference<Set<String>>() {
                                });
                // stream optimization ruins the call limit rate. Opted for 'for_loop'.
                for(String matchId : matchIdList.getBody()){
                    matchHistory.add(restTemplate.getForObject("https://europe.api.riotgames.com/lol/match/v5/matches/"+ matchId+"?api_key="+devKey, GameFinishedDTO.class));
                    TimeUnit.MILLISECONDS.sleep(__TIME_LIMIT__);
                }
                if(matchIdList.getBody().size()<100 && matchIdList.getBody().size()< sizeOptional.orElse(20)){
                    log.info("RiotLookUpServiceImpl::getMatchHistory : final match history request, there are no more games.");
                    break;
                }
            }catch (HttpClientErrorException.TooManyRequests e) {
                log.error("RiotLookUpServiceImpl::getMatchHistory : failed in getting all match history: [message: " + e.getMessage() + "]");
            }catch(InterruptedException e){
                log.error("RiotLookUpServiceImpl::getMatchHistory : interrupted sleep.");
            }
        }while((size-=100) > 0);
        log.info("The size of the get MatchHistory list is : " + matchHistory.size());
        return CompletableFuture.completedFuture(matchHistory);
    }


    private <T> CompletableFuture<T> getCall( Supplier<T> supplier) {
        boolean inGame = false;
        T result = null;
        while (!inGame && this.liveGameService.getGameTrackingStatus().equals(GameTrackingStatus.ENABLED)) {
            try {
                TimeUnit.SECONDS.sleep(__RETRY_PERIOD__);
                result = supplier.get();
                inGame = true;
            } catch (RestClientException | InterruptedException e) {
                    log.info("[LIVE GAME TRACK] Waiting for a Game to start");
                    log.info("Exception message is : " + e.getMessage() + ", [stack] : " + e.getStackTrace().toString());
                    for(StackTraceElement stackTraceElement: e.getStackTrace()){
                        log.info("* " + stackTraceElement.getClassName() + "::" + stackTraceElement.getClassName() +" line: " + stackTraceElement.getLineNumber());
                    }
            }
        }
        return CompletableFuture.completedFuture(result);
    }

}
