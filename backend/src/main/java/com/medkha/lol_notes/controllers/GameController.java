package com.medkha.lol_notes.controllers;

import java.util.Set;

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
	public Set<Game> allGames(){
		return this.gameService.findAllGames(); 
	}
	
	@GetMapping(value = "/{gameId}", produces = "application/json")
	public Game getGame(@PathVariable("gameId") Long gameId) {
		return this.gameService.findById(gameId); 
	}
	@PostMapping(value = "/createGame", consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Game postGame(@RequestBody Game game) throws Exception {
		return this.gameService.createGame(game); 
	}
	
	@PutMapping(path = "/updateGame/{gameId}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Game putGame(@PathVariable("gameId") Long gameId, 
							@RequestBody Game game) throws Exception { 
		return this.gameService.updateGame(game); 
	}
	
	@DeleteMapping("/deleteGame/{gameId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteGame(@PathVariable("gameId") Long gameId) {
		this.gameService.deleteGame(gameId);
	}
}
