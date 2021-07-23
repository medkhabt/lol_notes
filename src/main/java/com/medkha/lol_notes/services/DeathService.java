package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.dto.DeathDTO;

public interface DeathService {
	public DeathDTO createDeath(DeathDTO death);
	public DeathDTO updateDeath(DeathDTO death);
	public void deleteDeathById(Long id); 
	public Set<DeathDTO> findAllDeaths();
	public Integer countAllDeaths();
	public DeathDTO findById(Long id);

}
