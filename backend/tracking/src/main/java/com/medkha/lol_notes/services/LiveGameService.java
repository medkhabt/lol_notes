package com.medkha.lol_notes.services;

import com.medkha.lol_notes.dto.enums.GameTrackingStatus;
import com.medkha.lol_notes.dto.enums.PlayerGameStatus;
import org.springframework.scheduling.annotation.Async;

public interface LiveGameService {
    @Async
    void findLiveGame(Runnable runnablePlayerInGame);
    void setPlayerGameStatus(PlayerGameStatus playerGameStatus);
    void setGameTrackingStatus(GameTrackingStatus gameTrackingStatus);
    GameTrackingStatus getGameTrackingStatus();
}
