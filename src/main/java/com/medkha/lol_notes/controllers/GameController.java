package com.medkha.lol_notes.controllers;


import java.text.MessageFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import com.medkha.lol_notes.dto.*;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.services.ChampionService;
import com.medkha.lol_notes.services.QueueService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.client.RestTemplate;


@RestController
@RequestMapping(path="games",
				produces="application/json")
public class GameController {
	private static final Logger log = LoggerFactory.getLogger(GameController.class);
	private GameService gameService;
	private ChampionService championService;
	private QueueService queueService;
	private RestTemplate restTemplate;
	public GameController(GameService gameService, ChampionService championService, QueueService queueService, RestTemplate restTemplate) {
		this.gameService = gameService;
		this.championService = championService;
		this.restTemplate = restTemplate;
		this.queueService = queueService;
	}

	@GetMapping(value = "live-game", produces = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public GameDTO trackLiveGame() {
		ResponseEntity<List<PlayerDTO>> playerListResponse =
				restTemplate.exchange("https://localhost:2999/liveclientdata/playerlist",
						HttpMethod.GET, null, new ParameterizedTypeReference<List<PlayerDTO>>() {
						});
		List<PlayerDTO> players = playerListResponse.getBody();
		LiveGameDTO liveGameStats = restTemplate.getForObject("https://localhost:2999/liveclientdata/gamestats", LiveGameDTO.class);
		log.info("List of Player: ");
//		liveclientdata/gamestats
		PlayerDTO activePlayer = restTemplate.getForObject("https://localhost:2999/liveclientdata/activeplayer", PlayerDTO.class);

		players.forEach( p ->{
			if(p.summonerName.equals(activePlayer.summonerName)) {
				activePlayer.championName = p.championName;
			}
			log.info( MessageFormat.format(
					"- {0} {1}",
					p.summonerName,
					p.summonerName.equals(activePlayer.summonerName) ?  ": [activePlayer]" : ""));
		});
		log.info("Active player info: " + activePlayer.toString());
		// TODO put on the service side (there is no queue mode for practice tool. )

		GameDTO game = new GameDTO();
		ChampionEssentielsDto champion = championService.getChampionByName(activePlayer.championName);
		game.setChampionId(champion.getId());
		if(liveGameStats.gameMode.equals("PRACTICETOOL")) {
			liveGameStats.gameMode = "CUSTOM";
		}
		QueueDTO queue = this.queueService.getAllQueuesWithoutDeprecate().stream().filter(
				q -> q.getQueueName().equals(liveGameStats.gameMode)
		).findFirst().orElseThrow(() -> new NoElementFoundException("Couldn't find queue of name " + liveGameStats.gameMode));
		game.setQueueId(queue.getId());
		long millis = (long) (Double.parseDouble(liveGameStats.gameTime) * 1000);
		log.info("Date time millis: " + millis );
		Date createdOn = Date.from(Instant.now().minusMillis(millis));
		log.info("created On : " + createdOn) ;
		game.setCreatedOn(createdOn);
		return this.gameService.createGame(game);
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
