package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.entities.Death;

public interface DeathService {
	
	public Death createDeath(Death death) throws Exception;
	public Death updateDeath(Death death) throws Exception; 
	public void deleteDeathById(Long id) throws Exception; 
	public Set<Death> findAllDeaths(); 
	public Death findById(Long id); 
	public Boolean existsInDataBase(Long id);
}
