package com.medkha.lol_notes.controllers;


import java.io.IOException;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import javax.validation.Valid;

import com.medkha.lol_notes.dto.*;
import com.medkha.lol_notes.dto.enums.GameTrackingStatus;
import com.medkha.lol_notes.dto.enums.PlayerGameStatus;
import com.medkha.lol_notes.repositories.MatchHistoryRepository;
import com.medkha.lol_notes.services.*;
import com.medkha.lol_notes.util.ServerSentEventSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

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
	private final MatchHistoryRepository matchHistoryRepository;
	private SseEmitter sseEmitter;
	public GameController(
			GameService gameService,
			LiveGameService liveGameService,
			ChampionService championService,
			QueueService queueService,
			RiotLookUpService riotLookUpService,
			MatchHistoryRepository matchHistoryRepository) {
		this.gameService = gameService;
		this.liveGameService = liveGameService;
		this.championService = championService;
		this.queueService = queueService;
		this.riotLookUpService = riotLookUpService;
		this.matchHistoryRepository = matchHistoryRepository;
	}

	@GetMapping(value = "live-game", consumes = MediaType.ALL_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public SseEmitter trackLiveGame() throws IOException {
		this.sseEmitter = new SseEmitter(Long.MAX_VALUE);
		ServerSentEventSession sses = new ServerSentEventSession(sseEmitter);
		sses.sseEmitter.send(SseEmitter.event().name("INIT").data("Connected"));
		this.liveGameService.findLiveGame(
				() -> {
					CompletableFuture<AllEventsDTO> eventsFuture = this.riotLookUpService.getEventsAsync();
					eventsFuture.thenAccept((events)->{
						try {
							EventInGameDTO endOfGame = new EventInGameDTO();
							endOfGame.eventName = "GameEnd";
							if(events.Events.contains(endOfGame)) {
								this.liveGameService.setPlayerGameStatus(PlayerGameStatus.IDLE);
								log.info("Game Ended");
							}
							getDeathEvent(sses, events, this.liveGameService.getActivePlayer().map(p-> p.summonerName));
						} catch (IOException e) {
							log.info("SSe Emitter removed. exception message: " + e.getMessage());
						}
					});
				}
		);
		return sseEmitter;
	}
    private void getDeathEvent(ServerSentEventSession sses, AllEventsDTO events, Optional<String> playerName) throws IOException {
		EventInGameDTO temp;
		for(int i = sses.lastCheckedIndex + 1; i < events.Events.size(); i++) {
			temp = events.Events.get(i);
			if(temp.eventName.equals("ChampionKill") &&
					(playerName.isEmpty()  // Get all championKill events.
						|| (playerName.isPresent() && playerName.equals(events.Events.get(i).victimName))
					)
			){
				sses.sseEmitter.send(sseEmitter.event().name("New DeathEvent").data(events.Events.get(i)));
			}
		}
		sses.lastCheckedIndex = events.Events.size() - 1;
		events.Events.stream().filter(e -> e.eventName.equals("ChampionKill")).count();
	}
	@GetMapping("/stop-track-live-games")
	@ResponseStatus(HttpStatus.OK)
	public void disableTracking() {
		this.liveGameService.setGameTrackingStatus(GameTrackingStatus.DISABLED);
	}

	@GetMapping("/export-match-history")
	@ResponseStatus(HttpStatus.OK)
	public void exportMatchHistory(@RequestParam String summonerName, @RequestParam int queueId, @RequestParam int count){
		this.riotLookUpService.getMatchHistory(summonerName, Optional.of(queueId), Optional.of(count)).thenAccept(matchHistoryRepository::exportMatchHistory);
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
