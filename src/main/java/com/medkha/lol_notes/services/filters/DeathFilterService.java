package com.medkha.lol_notes.services.filters;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.medkha.lol_notes.entities.Death;

public interface DeathFilterService {
	public Stream<Death> getDeathsByFilter(List<Predicate<Death>> listDeathPredicate);
}
