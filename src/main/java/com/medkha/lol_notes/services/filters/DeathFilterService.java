package com.medkha.lol_notes.services.filters;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.interfaces.DeathFilterEntity;

public interface DeathFilterService {

	@Deprecated
	public Set<Death> getDeathsByGame(Game game);

	@Deprecated
	public Set<Death> getDeathsByReason(Reason reason);

	@Deprecated
	Stream<Death> getDeathsByFilter(Stream<Death> deaths, Predicate<Death> deathFilterByReasonPredicate);

	Stream<Death> getDeathsByFilter(Stream<Death> deaths, DeathFilterEntity deathFilterEntity);
}
