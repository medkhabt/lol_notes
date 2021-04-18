package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.DeathId;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
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
			throw new IllegalArgumentException("Death Object is null and cannot be processed", err);
		}
	}

	@Override
	public Death updateDeath(Death death){
		return null; 
	}

	@Override
	public void deleteDeathById(DeathId id){
		
	}

	@Override
	public Set<Death> findAllDeaths() {
		Set<Death> findallDeathsSet = new HashSet<>(); 
		this.deathRepository.findAll().forEach(findallDeathsSet::add);
		return findallDeathsSet;
	}

	@Override
	public Death findById(DeathId id) {
		try { 
			return this.deathRepository.findById(id).orElseThrow();
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			throw new IllegalArgumentException("Death Object has null id, and cannot be processed", err);
		} catch (NoSuchElementException err) { 
			throw new NoElementFoundException("No Element of type Death with id " + id + " was found in the database.", err); 
		}
	}



}
