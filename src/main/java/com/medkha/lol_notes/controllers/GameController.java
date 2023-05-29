package com.medkha.lol_notes.controllers;


import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import javax.validation.Valid;

import com.medkha.lol_notes.dto.*;
import com.medkha.lol_notes.dto.enums.GameTrackingStatus;
import com.medkha.lol_notes.dto.enums.PlayerGameStatus;
import com.medkha.lol_notes.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;


@RestController
@RequestMapping(path="games",
				produces="application/json")
public class GameController {
	private static final Logger log = LoggerFactory.getLogger(GameController.class);
	private final GameService gameService;
	private final LiveGameService liveGameService;
	private final ChampionService championService;
	private final QueueService queueService;
	private final RiotLookUpService riotLookUpService;
	private SseEmitter sseEmitter;
	public GameController(
			GameService gameService,
			LiveGameService liveGameService,
			ChampionService championService,
			QueueService queueService,
			RiotLookUpService riotLookUpService) {
		this.gameService = gameService;
		this.liveGameService = liveGameService;
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
		this.sseEmitter = new SseEmitter(Long.MAX_VALUE);
		sseEmitter.send(SseEmitter.event().name("INIT").data("Connected"));
		Consumer<AllEventsDTO> logEventsWhileInGame =
				(events) -> {
					log.info("****** LIST OF EVENTS THAT HAPPENED IN GAME*******");
					EventInGameDTO endOfGame = new EventInGameDTO();
					endOfGame.EventName = "GameEnd";
					if(events.Events.contains(endOfGame)) {
						this.liveGameService.setPlayerGameStatus(PlayerGameStatus.IDLE);
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
				};
		this.liveGameService.findLiveGame(
				() -> {
					CompletableFuture<AllEventsDTO> eventsFuture = this.riotLookUpService.getEventsAsync();
					eventsFuture.thenAccept(logEventsWhileInGame);
				}
		);
		return sseEmitter;
	}

	@GetMapping("/stop-track-live-games")
	@ResponseStatus(HttpStatus.OK)
	public void disableTracking() {
		this.liveGameService.setGameTrackingStatus(GameTrackingStatus.DISABLED);
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
