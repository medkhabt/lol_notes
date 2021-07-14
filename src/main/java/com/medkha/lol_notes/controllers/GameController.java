package com.medkha.lol_notes.controllers;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.services.GameService;

@RestController
@RequestMapping(path="games",
				produces="application/json")
public class GameController {
	@Autowired
	private GameService gameService;
	
	public GameController(GameService gameService) {
		this.gameService = gameService; 
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
//	@ResponseStatus(HttpStatus.CREATED)
//	public GameDTO postGame(@Valid @RequestBody Game game) throws Exception {
//		return this.gameService.createGame(game);
//	}
//
//	@PutMapping(path = "/{gameId}", consumes = "application/json")
//	@ResponseStatus(HttpStatus.OK)
//	public GameDTO putGame(@PathVariable("gameId") Long gameId,
//						@Valid @RequestBody Game game) throws Exception {
//		game.setId(gameId);
//		return this.gameService.updateGame(game);
//	}
	
	@DeleteMapping("/{gameId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGame(@PathVariable("gameId") Long gameId) {
		this.gameService.deleteGame(gameId);
	}
}
