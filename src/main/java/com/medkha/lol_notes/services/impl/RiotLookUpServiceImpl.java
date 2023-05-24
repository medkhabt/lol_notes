package com.medkha.lol_notes.services.impl;

import com.medkha.lol_notes.controllers.GameController;
import com.medkha.lol_notes.dto.LiveGameDTO;
import com.medkha.lol_notes.dto.PlayerDTO;
import com.medkha.lol_notes.services.RiotLookUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Service
public class RiotLookUpServiceImpl implements RiotLookUpService {

    private static final Logger log = LoggerFactory.getLogger(RiotLookUpServiceImpl.class);
    private static final long  __RETRY_PERIOD__ = 10;
    private RestTemplate restTemplate;
    public RiotLookUpServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    @Async
    public CompletableFuture<PlayerDTO> getActivePlayerInLiveGameAsync() {
        log.info("Looking up active Player in the live game");
        return getCall(
                () -> restTemplate.getForObject("https://localhost:2999/liveclientdata/activeplayer", PlayerDTO.class)
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
        return getCall(
                () -> restTemplate.getForObject("https://localhost:2999/liveclientdata/gamestats", LiveGameDTO.class)
        );
    }

    private <T> CompletableFuture<T> getCall(Supplier<T> supplier) {
        boolean inGame = false;
        T result = null;
        while(!inGame) {
            try {
                TimeUnit.SECONDS.sleep(__RETRY_PERIOD__);
                result = supplier.get();
                inGame = true;
            }catch (RestClientException | InterruptedException e) {
                log.info("[LIVE GAME TRACK] Waiting for a Game to start");
                log.debug("Exception message is : " + e.getMessage());
            }
        }
        return CompletableFuture.completedFuture(result);
    }
}
