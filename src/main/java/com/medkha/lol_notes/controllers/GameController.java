package com.medkha.lol_notes.controllers;


import java.io.IOException;
import java.text.MessageFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.validation.Valid;

import com.medkha.lol_notes.dto.*;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.services.ChampionService;
import com.medkha.lol_notes.services.QueueService;
import com.medkha.lol_notes.services.RiotLookUpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.services.GameService;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequestMapping(path="games",
				produces="application/json")
public class GameController {
	private static final Logger log = LoggerFactory.getLogger(GameController.class);
	private final GameService gameService;
	private final ChampionService championService;
	private final QueueService queueService;
	private final RiotLookUpService riotLookUpService;
	private SseEmitter sseEmitter;
	private boolean isTrackingLiveGame = false;
	public GameController(
			GameService gameService,
			ChampionService championService,
			QueueService queueService,
			RiotLookUpService riotLookUpService) {
		this.gameService = gameService;
		this.championService = championService;
		this.queueService = queueService;
		this.riotLookUpService = riotLookUpService;
	}

	@GetMapping(value = "live-game", consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public SseEmitter trackLiveGame() throws IOException {
		// TODO put on the service side (there is no queue mode for practice tool. )
		// TODO Clean this, and find a unique identifier for the online games, so i create just one game per liveGame.
		// TODO Call again this method after a game is finished.
		this.sseEmitter = new SseEmitter();
		sseEmitter.send(SseEmitter.event().name("INIT").data("Connected"));
		findLiveGame(sseEmitter);
		return sseEmitter;
	}
	@Async
	 void findLiveGame(SseEmitter sseEmitter) {
		this.isTrackingLiveGame = true;
//		while(this.isTrackingLiveGame){
			CompletableFuture<LiveGameDTO> liveGameStatsFuture = riotLookUpService.getLiveGameAsync();
			CompletableFuture<PlayerDTO> activePlayerFuture = riotLookUpService.getActivePlayerInLiveGameAsync();
			CompletableFuture<List<PlayerDTO>> allPlayersFuture = riotLookUpService.getAllPlayersInLiveGameAsync();
			liveGameStatsFuture.thenAccept(liveGameStats -> {
				try {
					CompletableFuture.allOf(activePlayerFuture, allPlayersFuture);
					PlayerDTO activePlayer= activePlayerFuture.get();
					List<PlayerDTO> players = allPlayersFuture.get();
					GameDTO game = fillGameDTO(activePlayer, players, liveGameStats);
					log.info("Active player info: " + activePlayer);
					this.gameService.createGame(game);
					activePlayer.inGame = true;
					while (activePlayer.inGame) {
						CompletableFuture<AllEventsDTO> eventsFuture = this.riotLookUpService.getEventsAsync();
						eventsFuture.thenAccept(
								events -> {
									log.info("****** LIST OF EVENTS THAT HAPPENED IN GAME*******");
									EventInGameDTO endOfGame = new EventInGameDTO();
									endOfGame.EventName = "GameEnd";
									if(events.Events.contains(endOfGame)) {
										activePlayer.inGame = false;
										log.info("Game Ended");
									}
									events.Events.forEach(
											e -> log.info("- " + e.EventName + ", id: " + e.EventId)
									);
									try {
										sseEmitter.send(sseEmitter.event().name("LiveGameEvents").data(events.Events));
									} catch (IOException e) {
										log.info("SSe Emitter removed. exception message: " + e.getMessage());
									}
								}
						);
						TimeUnit.SECONDS.sleep(3);
					}

				} catch (InterruptedException e) {
					log.error("A thread is interrupted, exception stack: " + e.getStackTrace() );
				} catch (ExecutionException e) {
					log.error("Couldn't retrieve the result from the one of the futures, exception stack: " + e.getStackTrace());
				}
			});
//		}

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
	@GetMapping("/stop-track-live-games")
	@ResponseStatus(HttpStatus.OK)
	public void disableTracking() {
		this.isTrackingLiveGame = false;
	}

	@GetMapping(produces = "application/json")
	public Set<GameDTO> allGames(){
		return this.gameService.findAllGames(); 
	}
	
	@GetMapping(value = "/{gameId}", produces = "application/json")
	public GameDTO getGame(@PathVariable("gameId") Long gameId) {
		return this.gameService.findById(gameId); 
	}
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public GameDTO postGame(@Valid @RequestBody GameDTO game) {
		return this.gameService.createGame(game);
	}
	@PutMapping(path = "/{gameId}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public GameDTO putGame(@PathVariable("gameId") Long gameId,
						@Valid @RequestBody GameDTO game) {
		game.setId(gameId);
		return this.gameService.updateGame(game);
	}
	
	@DeleteMapping("/{gameId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGame(@PathVariable("gameId") Long gameId) {
		this.gameService.deleteGame(gameId);
	}
}
