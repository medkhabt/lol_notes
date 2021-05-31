package com.medkha.lol_notes.services.impl.filters;

import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

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
			log.error("getDeathsByGame: Game is null so can't proceed getting deaths in a game.");
			throw new IllegalArgumentException("Game is null so can't proceed getting deaths in a game."); 
		}

		Set<Death> deathsInGame  = this.deathRepository.findByGame(game);
		if(deathsInGame.isEmpty()) {
			log.error("getDeathsByGame: No deaths found by game with id: " + game.getId());
			throw new NoElementFoundException("getDeathsByGame: No deaths found by game with id: " + game.getId());
		}
		else {
			log.info("getDeathsByGame: Found {} deaths by game with id {} successfully.", deathsInGame, game.getId());
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

	@Override
	public Predicate<Death> getDeathFilterByReasonPredicate(Reason reason) {
		if (reason == null) {
			log.warn("getDeathFilterByReasonPredicate: Reason to filter with is null, this filter is neglected.");
			return (Death death) -> true;
		}
		return (Death death) -> {
			log.info("getDeathFilterByReasonPredicate: Filter by Reason with id: {}", reason.getId());
			Boolean result = death.getReason().getId().equals(reason.getId());
			log.info("Death with id: {} has Reason with id: {} equals Filter Reason with id:{} ? {} ",
						death.getId(), death.getReason().getId(), reason.getId(), result);
			return result;
		};
	}

	@Override
	public Predicate<Death> getDeathFilterByGamePredicate(Game game) {
		if (game == null) {
			log.warn("getDeathFilterByGamePredicate: Game to filter with is null, this filter is neglected.");
			return (Death death) -> true;
		}
		return (Death death) -> {
			log.info("getDeathFilterByGamePredicate: Filter by Game with id: {}", game.getId());
			Boolean result = death.getGame().getId().equals(game.getId());
			log.info("Death with id: {} has Game with id: {} equals Filter Game with id:{} ? {} ",
					death.getId(), death.getGame().getId(), game.getId(), result);
			return result;
		};
	}

	@Override
	public Stream<Death> getDeathsByFilter(Stream<Death> deaths, Predicate<Death> deathFilterByReasonPredicate) {
		log.info("enter getDeathsByFilter: ");
		deaths = deaths.filter(deathFilterByReasonPredicate);
		log.info("getDeathsByFilter: filtered successfully.");
		return deaths;
	}

}
