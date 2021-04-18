package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.DeathService;

@Service
public class DeathServiceImpl implements DeathService{

	@Autowired
	private DeathRepository deathRepository;
	
	
	
	@Override
	public Death createDeath(Death death){
		try {
			return this.deathRepository.save(death);
			
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			throw new IllegalArgumentException("Reason Object is null and cannot be processed", err);
		}
	}

	@Override
	public Death updateDeath(Death death){
		return null; 
	}

	@Override
	public void deleteDeathById(Long id){
		
	}

	@Override
	public Set<Death> findAllDeaths() {
		Set<Death> findallDeathsSet = new HashSet<>(); 
		this.deathRepository.findAll().forEach(findallDeathsSet::add);
		return findallDeathsSet;
	}

	@Override
	public Death findById(Long id) {
		return this.deathRepository.findById(id).orElse(null);
	}



}
