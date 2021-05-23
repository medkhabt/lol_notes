package com.medkha.lol_notes.services.impl.filters;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@Service
public class DeathFilterServiceImpl implements DeathFilterService{
	
	@Autowired private DeathRepository deathRepository;
	
	
	@Override
	public Set<Death> getDeathsByGame(Game game) {
		if(game == null) { 
			throw new IllegalArgumentException("Game is null so can't proceed getting deaths in a game."); 
		}
		
		Set<Death> deathsInGame  = this.deathRepository.findByGame(game);
		
		if(deathsInGame.isEmpty()) { 
			throw new NoElementFoundException("No deaths found for game with id: " + game.getId()); 
		}
		
			
			return deathsInGame;

	}

}
