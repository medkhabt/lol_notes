package com.medkha.lol_notes.services;

import com.medkha.lol_notes.dto.*;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public interface RiotLookUpService {
    @Async
    CompletableFuture<PlayerDTO> getActivePlayerInLiveGameAsync();
    @Async
    CompletableFuture<List<PlayerDTO>> getAllPlayersInLiveGameAsync();
    @Async
    CompletableFuture<LiveGameDTO> getLiveGameAsync();
    @Async
    CompletableFuture<AllEventsDTO> getEventsAsync();

    @Async
    CompletableFuture<Set<GameFinishedDTO>> getMatchHistory( String userName, Optional<Integer> queueId,  Optional<Integer> sizeOptional);
}
