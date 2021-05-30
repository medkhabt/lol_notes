package com.medkha.lol_notes.services.filters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;

public interface DeathFilterService {


	public Set<Death> getDeathsByGame(Game game); 
	
	public Set<Death> getDeathsByReason(Reason reason);

	Predicate<Death> getDeathFilterByReasonPredicate(Reason reason);
	Predicate<Death> getDeathFilterByGamePredicate(Game game);

	Stream<Death> getDeathsByFilter(Stream<Death> deaths, Predicate<Death> deathFilterByReasonPredicate);
}
