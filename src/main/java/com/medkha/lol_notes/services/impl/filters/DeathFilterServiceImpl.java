package com.medkha.lol_notes.services.impl.filters;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@Service
public class DeathFilterServiceImpl implements DeathFilterService{
	
	@Autowired private DeathRepository deathRepository;
	@Autowired private GameService gameService;
	
	
	@Override
	public Set<Death> getDeathsByGame(Long gameId) {
		Game game = this.gameService.findById(gameId); 
		return this.deathRepository.findByGame(game);
	}

}
