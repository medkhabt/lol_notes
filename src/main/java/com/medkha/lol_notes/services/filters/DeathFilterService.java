package com.medkha.lol_notes.services.filters;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import com.medkha.lol_notes.dto.DeathDTO;

public interface DeathFilterService {
	public Stream<DeathDTO> getDeathsByFilter(List<Predicate<DeathDTO>> listDeathPredicate);
	public Double getRatioDeathsByFilter(List<Predicate<DeathDTO>> listDeathPredicate);
}
