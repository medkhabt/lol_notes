package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.DeathId;

public interface DeathService {
	
	public Death createDeath(Death death);
	public Death updateDeath(Death death); 
	public void deleteDeathById(DeathId id); 
	public Set<Death> findAllDeaths(); 
	public Death findById(DeathId id); 
}
