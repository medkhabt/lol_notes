package com.medkha.lol_notes.services.impl;

import com.medkha.lol_notes.dto.*;
import com.medkha.lol_notes.dto.enums.GameTrackingStatus;
import com.medkha.lol_notes.dto.enums.PlayerGameStatus;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

@Service
public class LiveGameServiceImpl implements LiveGameService {
    private static final Logger log = LoggerFactory.getLogger(LiveGameServiceImpl.class);
    private final RiotLookUpService riotLookUpService;
    private final GameService gameService;
    private final ChampionService championService;
    private final QueueService queueService;
    //TODO: this is Temporary and we considering this project in its current version that is runnable only
    // in the client computer, as it's close to the client's machine [usage of localhost to interact with the game]
    // , further modules could be deployed in a server.
    private PlayerGameStatus playerGameStatus;
    private GameTrackingStatus gameTrackingStatus;
    private PlayerDTO activePlayer;

    public LiveGameServiceImpl(
            RiotLookUpService riotLookUpService,
            GameService gameService,
            ChampionService championService,
            QueueService queueService){
        this.riotLookUpService = riotLookUpService;
        this.gameService = gameService;
        this.championService = championService;
        this.queueService = queueService;
        this.gameTrackingStatus = GameTrackingStatus.DISABLED;
        this.playerGameStatus = PlayerGameStatus.IDLE;
    }

    @Override
    public void findLiveGame(Runnable runnablePlayerInGame) {
        this.gameTrackingStatus = GameTrackingStatus.ENABLED;
        while(this.gameTrackingStatus.equals(GameTrackingStatus.ENABLED)){
            CompletableFuture<LiveGameDTO> liveGameStatsFuture = riotLookUpService.getLiveGameAsync();
            CompletableFuture<PlayerDTO> activePlayerFuture = riotLookUpService.getActivePlayerInLiveGameAsync();
            CompletableFuture<List<PlayerDTO>> allPlayersFuture = riotLookUpService.getAllPlayersInLiveGameAsync();

            // No need for CompletableFuture anymore.
            try {
                CompletableFuture.allOf(liveGameStatsFuture, activePlayerFuture, allPlayersFuture);
                LiveGameDTO liveGameStats = liveGameStatsFuture.get();
                this.activePlayer= activePlayerFuture.get();
                List<PlayerDTO> players = allPlayersFuture.get();

                // Tracking is canceled.
                if(liveGameStats == null || activePlayer == null || players == null) {
                    break;
                }

                GameDTO game = fillGameDTO(activePlayer, players, liveGameStats);
                log.info("Active player info: " + activePlayer);
                this.gameService.createGame(game);
                this.playerGameStatus = PlayerGameStatus.IN_GAME;
                while (this.playerGameStatus.equals(PlayerGameStatus.IN_GAME)) {
                    runnablePlayerInGame.run();
                    TimeUnit.SECONDS.sleep(3);
                }
                this.activePlayer = null ;
            } catch (InterruptedException e) {
                log.error("A thread is interrupted, exception stack: " + e.getStackTrace() );
            } catch (ExecutionException e) {
                log.error("Couldn't retrieve the result from the one of the futures, exception stack: " + e.getStackTrace());
            }
        }
        log.info("No more searching for a game.");

    }

    @Override
    public void setPlayerGameStatus(PlayerGameStatus playerGameStatus) {
        this.playerGameStatus = playerGameStatus;
    }

    @Override
    public void setGameTrackingStatus(GameTrackingStatus gameTrackingStatus) {
        this.gameTrackingStatus = gameTrackingStatus;
    }

    @Override
    public GameTrackingStatus getGameTrackingStatus() {
        return this.gameTrackingStatus;
    }

    @Override
    public Optional<PlayerDTO> getActivePlayer() {
        return Optional.ofNullable(this.activePlayer);
    }

    private GameDTO fillGameDTO(PlayerDTO activePlayer, List<PlayerDTO> players, LiveGameDTO liveGameStats) {
        GameDTO game = new GameDTO();
        players.forEach( p ->{
            if(p.summonerName.equals(activePlayer.summonerName)) {
                activePlayer.championName = p.championName;
            }
            log.info( MessageFormat.format(
                    "- {0} {1}",
                    p.summonerName,
                    p.summonerName.equals(activePlayer.summonerName) ?  ": [activePlayer]" : ""));
        });
        ChampionEssentielsDto champion = championService.getChampionByName(activePlayer.championName);
        game.setChampionId(champion.getId());
        if(liveGameStats.gameMode.equals("PRACTICETOOL")) {
            liveGameStats.gameMode = "CUSTOM";
        }
        QueueDTO queue = this.queueService.getAllQueuesWithoutDeprecate().stream().filter(
                q -> q.getQueueName().equals(liveGameStats.gameMode)
        ).findFirst().orElseThrow(() -> new NoElementFoundException("Couldn't find queue of name " + liveGameStats.gameMode));
        game.setQueueId(queue.getId());
        // TODO: Fix the the time diff with timezones.
        // TODO: Fix the gameStart getting from api , remove the 3 last digits.
        if(!liveGameStats.gameMode.equals("CUSTOM"))
            game.setCreatedOn(Date.from(Instant.now().minusSeconds(Long.parseLong(liveGameStats.gameLength))));
        return game;
    }
}
