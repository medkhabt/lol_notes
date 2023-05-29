package com.medkha.lol_notes.services.impl;

import com.medkha.lol_notes.dto.*;
import com.medkha.lol_notes.services.RiotLookUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class RiotLookUpServiceImpl implements RiotLookUpService {

    private static final Logger log = LoggerFactory.getLogger(RiotLookUpServiceImpl.class);
    private static final long  __RETRY_PERIOD__ = 4;

    @Value("${lol_notes.dev-key}")
    private Resource devKeyResource;
    private String devKey;
    private final RestTemplate restTemplate;
    public RiotLookUpServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
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


    private <T> CompletableFuture<T> getCall( Supplier<T> supplier) {
        boolean inGame = false;
        T result = null;
        while (!inGame) {
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
//    private boolean isEndOfGame(CompletableFuture<AllEventsDTO> eventsFuture) {
//      try {
//          CompletableFuture.allOf(eventsFuture);
//          EventInGameDTO endOfGame = new EventInGameDTO();
//          endOfGame.EventName = "GameEnd";
//          return eventsFuture.get().Events.contains(endOfGame);
//      } catch (ExecutionException | InterruptedException e) {
//          log.error(e.getMessage() + ", stack:" + e.getStackTrace());
//          // Should I return a runtime exception here?
//          return false;
//      }
//
//    }
}
