package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.modelmapper.internal.bytebuddy.description.method.MethodDescription.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.mapper.MapperService;
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
	private final MapperService mapperService;
	public GameServiceImpl(GameRepository gameRepository, ChampionService championService, RoleAndLaneService roleAndLaneService, MapperService mapperService) {
		this.gameRepository = gameRepository;
		this.championService = championService;
		this.roleAndLaneService = roleAndLaneService;
		this.mapperService = mapperService;
	}

	
	@Override
	@Transactional
	public GameDTO createGame(GameDTO gameDTO){
		try {
			championService.getChampionById(gameDTO.getChampionId());
			isLaneExceptionHandler(gameDTO);
			isRoleExceptionHandler(gameDTO);
			Game createdGame = this.gameRepository.save(mapperService.convert(gameDTO, Game.class));
			log.info("createGame: Game with id: " + createdGame.getId() + " created successfully.");
			return mapperService.convert(createdGame, GameDTO.class);
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			log.error("createGame: Game Object is null and cannot be proceed");
			throw new IllegalArgumentException("Game Object is null and cannot be proceed", err);
		}
	}

	private void isRoleExceptionHandler(GameDTO game) {
		if(!roleAndLaneService.isLane(game.getLaneName())) {
			log.error("isRoleExceptionHandler: No Lane with name {} was found.", game.getLaneName());
			throw new NoElementFoundException("No Lane with name " + game.getLaneName() + " was found.");
		}
	}

	private void isLaneExceptionHandler(GameDTO game) {
		if(!roleAndLaneService.isRole(game.getRoleName())) {
			log.error("isLaneExceptionHandler: No Role with name {} was found.", game.getRoleName());
			throw new NoElementFoundException("No Role with name " + game.getLaneName() + " was found.");
		}
	}

	@Override
	@Transactional
	public GameDTO updateGame(GameDTO gameDTO){
		try {
			findById(gameDTO.getId());
			championService.getChampionById(gameDTO.getChampionId());
			isRoleExceptionHandler(gameDTO);
			isLaneExceptionHandler(gameDTO);
			Game updatedGame = this.gameRepository.save(mapperService.convert(gameDTO, Game.class));
			log.info("updateGame: Game with id: " + updatedGame.getId() + " was updated successfully.");
			return mapperService.convert(updatedGame, GameDTO.class);
	
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
	public Set<GameDTO> findAllGames() {
		Set<Game> findallGamesSet = new HashSet<>(); 
		this.gameRepository.findAll().forEach(findallGamesSet::add);
		log.info("findAllGames: " + findallGamesSet.size() + " games were found.");
		
		return this.mapperService.convertSet(findallGamesSet, GameDTO.class);
	}

	@Override
	public GameDTO findById(Long id) {
		try {
			Game foundGame = this.gameRepository.findById(id).orElseThrow();
			log.info("findById: Game with id: " + id + " was found successfully.");
			return this.mapperService.convert(foundGame, GameDTO.class);
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
