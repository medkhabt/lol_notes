package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.entities.Death;

public interface DeathService {
	
	public Death createDeath(Death death);
	public Death updateDeath(Death death); 
	public void deleteDeathById(Long id); 
	public Set<Death> findAllDeaths(); 
	public Death findById(Long id); 
}
