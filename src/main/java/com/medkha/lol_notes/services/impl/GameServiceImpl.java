package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.services.GameService;

@Service
public class GameServiceImpl implements GameService{

	@Autowired 
	private GameRepository gameRepository; 

	
	@Override
	public Game createGame(Game game) throws Exception {
		if(game.getId() == null) { 
			return this.gameRepository.save(game); 
		} 
		else { 
			throw new Exception("Id is not null.");
		}
	}

	@Override
	public Game updateGame(Game game) throws Exception {
		if(existsInDataBase(game.getId())) {
			return this.gameRepository.save(game); 
		}
		else { 
			throw new Exception("Reason instance not existing ( " + game.toString() + " ).");
		}
	}

	@Override
	public void deleteGame(Long id) {
		if(existsInDataBase(id)) { 
			this.gameRepository.deleteById(id);
		}
	}

	@Override
	public Set<Game> findAllGames() {
		Set<Game> findallGamesSet = new HashSet<>(); 
		this.gameRepository.findAll().forEach(findallGamesSet::add);
		return findallGamesSet;
	}

	@Override
	public Game findById(Long id) {
		return this.gameRepository.findById(id).orElse(null);
	}

	@Override
	public Boolean existsInDataBase(Long id) {
		return id != null && findById(id) != null; 	
	}

}
