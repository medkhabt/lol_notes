package com.medkha.lol_notes.services.impl.filters;

import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@Service
public class DeathFilterServiceImpl implements DeathFilterService{
	
	private static Logger log = LoggerFactory.getLogger(DeathFilterService.class); 
	
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


	@Override
	public Set<Death> getDeathsByReason(Reason reason) {
		if(reason == null) {
			log.error("getDeathsByReason: Reason is null so can't proceed getting deaths by reason."); 
			throw new IllegalArgumentException("Reason is null so can't proceed getting deaths by reason."); 
		}
		
		Set<Death> deathsByReason = this.deathRepository.findByReason(reason); 
		
		if(deathsByReason.isEmpty()) { 
			log.error("getDeathsByReason: No deaths found by reason with id: " + reason.getId() );
			throw new NoElementFoundException("No deaths found by reason with id: " + reason.getId()); 
		}
		else { 
			log.info("getDeathsByReason: found " + deathsByReason.size() + " deaths by reason with id: " + reason.getId() + " successfully.");
		}
		
		return deathsByReason;
	}

}
