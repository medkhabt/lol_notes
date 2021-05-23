package com.medkha.lol_notes.services.filters;

import java.util.Set;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;

public interface DeathFilterService {
	public Set<Death> getDeathsByGame(Game game); 
}
