package com.medkha.lol_notes.services.filters;

import java.util.Set;

import com.medkha.lol_notes.entities.Death;

public interface DeathFilterService {
	public Set<Death> getDeathsByGame(Long gameId); 
}
