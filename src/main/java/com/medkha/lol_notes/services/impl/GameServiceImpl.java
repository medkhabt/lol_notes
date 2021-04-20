package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.services.GameService;

@Service
public class GameServiceImpl implements GameService{

	@Autowired 
	private GameRepository gameRepository; 

	
	@Override
	public Game createGame(Game game){
		try {
			return this.gameRepository.save(game); 					
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			throw new IllegalArgumentException("Game Object is null and cannot be processed", err);
		}
	}

	@Override
	public Game updateGame(Game game){
		try {
			findById(game.getId()); 
			return this.gameRepository.save(game); 
	
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			throw new IllegalArgumentException("Game Object is null and cannot be processed", err);
		}
	}

	@Override
	public void deleteGame(Long id) {
		findById(id); 
		this.gameRepository.deleteById(id);
	}

	@Override
	public Set<Game> findAllGames() {
		Set<Game> findallGamesSet = new HashSet<>(); 
		this.gameRepository.findAll().forEach(findallGamesSet::add);
		return findallGamesSet;
	}

	@Override
	public Game findById(Long id) {
		try {
			return this.gameRepository.findById(id).orElseThrow();
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			throw new IllegalArgumentException("Game Object has null id, and cannot be processed", err); 
		} catch (NoSuchElementException err) { 
			throw new NoElementFoundException("No Element of type Game with id " + id + " was found in the database.", err); 
		}
	}

	@Override
	public Boolean existsInDataBase(Long id) {
		return id != null && findById(id) != null; 	
	}

}
