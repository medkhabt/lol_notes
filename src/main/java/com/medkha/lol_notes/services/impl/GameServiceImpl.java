package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.services.ChampionService;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.RoleAndLaneService;

@Service
public class GameServiceImpl implements GameService{
	
	private static final Logger log = 
			LoggerFactory.getLogger(GameServiceImpl.class); 

	private final ChampionService championService;
	private final GameRepository gameRepository;
	private final RoleAndLaneService roleAndLaneService;
	public GameServiceImpl(GameRepository gameRepository, ChampionService championService, RoleAndLaneService roleAndLaneService) {
		this.gameRepository = gameRepository;
		this.championService = championService;
		this.roleAndLaneService = roleAndLaneService;
	}

	
	@Override
	@Transactional
	public Game createGame(Game game){
		try {
			championService.getChampionById(game.getChampionId());
			isLaneExceptionHandler(game);
			isRoleExceptionHandler(game);
			Game createdGame = this.gameRepository.save(game); 
			log.info("createGame: Game with id: " + createdGame.getId() + " created successfully.");
			return createdGame;  				
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			log.error("createGame: Game Object is null and cannot be proceed");
			throw new IllegalArgumentException("Game Object is null and cannot be proceed", err);
		}
	}

	private void isRoleExceptionHandler(Game game) {
		if(!roleAndLaneService.isLane(game.getLaneName())) {
			log.error("isRoleExceptionHandler: No Lane with name {} was found.", game.getLaneName());
			throw new NoElementFoundException("No Lane with name " + game.getLaneName() + " was found.");
		}
	}

	private void isLaneExceptionHandler(Game game) {
		if(!roleAndLaneService.isRole(game.getRoleName())) {
			log.error("isLaneExceptionHandler: No Role with name {} was found.", game.getRoleName());
			throw new NoElementFoundException("No Role with name " + game.getLaneName() + " was found.");
		}
	}

	@Override
	@Transactional
	public Game updateGame(Game game){
		try {
			findById(game.getId());
			championService.getChampionById(game.getChampionId());
			isRoleExceptionHandler(game);
			isLaneExceptionHandler(game);
			Game updatedGame = this.gameRepository.save(game); 
			
			log.info("updateGame: Game with id: " + updatedGame.getId() + " was updated successfully.");
			return updatedGame; 
	
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			
			log.error("updateGame: Game Object is null and cannot be proceed");
			throw new IllegalArgumentException("Game Object is null and cannot be processed", err);
		}
	}

	@Override
	public void deleteGame(Long id) {
		findById(id); 
		// this try catch is just a safe net, if findById doesn't include null id traitement. 
		try {			
			this.gameRepository.deleteById(id);
			log.info("deleteGame: game with id: " + id + " was deleted successfully.");
		} catch (IllegalArgumentException err) {
			log.error("deleteGame: game id is null, so can't proceed.");
			throw new IllegalArgumentException("game id is null, so can't proceed.", err); 
		}
	}

	@Override
	public Set<Game> findAllGames() {
		Set<Game> findallGamesSet = new HashSet<>(); 
		this.gameRepository.findAll().forEach(findallGamesSet::add);
		
			log.info("findAllGames: " + findallGamesSet.size() + " games were found.");
		
		return findallGamesSet;
	}

	@Override
	public Game findById(Long id) {
		try {
			Game foundGame = this.gameRepository.findById(id).orElseThrow();
			log.info("findById: Game with id: " + id + " was found successfully.");
			return foundGame; 
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			
			log.error("findById: Game Object has null id, and cannot be processed");
			throw new IllegalArgumentException("Game Object has null id, and cannot be processed", err); 
		} catch (NoSuchElementException err) { 
			
			log.error("No Element of type Game with id " + id + " was found in the database.");
			throw new NoElementFoundException("No Element of type Game with id " + id + " was found in the database.", err); 
		}
	}

	@Override
	public Boolean existsInDataBase(Long id) {
		return id != null && findById(id) != null; 	
	}

}
