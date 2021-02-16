package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.entities.Game;

public interface GameService {
	
	public Game createGame(Game game) throws Exception;
	public Game updateGame(Game game) throws Exception; 
	public void deleteGame(Long id);
	public Set<Game> findAllGames(); 
	public Game findById(Long id); 
	public Boolean existsInDataBase(Long id);
}
