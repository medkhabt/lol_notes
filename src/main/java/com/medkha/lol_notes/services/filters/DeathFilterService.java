package com.medkha.lol_notes.services.filters;

import java.util.Set;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;

public interface DeathFilterService {
	
	public Set<Death> getDeathsByGame(Game game); 
	
	public Set<Death> getDeathsByReason(Reason reason); 
}
