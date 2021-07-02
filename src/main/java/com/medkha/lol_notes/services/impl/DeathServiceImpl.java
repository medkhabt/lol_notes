package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.DeathService;

@Service
public class DeathServiceImpl implements DeathService{
	private static Logger log = LoggerFactory.getLogger(DeathServiceImpl.class);

	private final DeathRepository deathRepository;

	public DeathServiceImpl(DeathRepository deathRepository) {
		this.deathRepository = deathRepository;
	}
	@Override
	public Death createDeath(Death death){
		try {
			Death createdDeath = deathRepository.save(death);
			log.info("createDeath: Death with id: {} created successfully.", createdDeath.getId());
			return this.deathRepository.save(death);
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			log.error("createDeath: Death Object is null and cannot be proceed");
			throw new IllegalArgumentException("Death Object is null and cannot be processed", err);
		}
	}

	@Override
	public Death updateDeath(Death death){
		try {
			
			findById(death.getId());
			Death updatedDeath = deathRepository.save(death);
			log.info("updateDeath: Death with id: {} updated successfully.", updatedDeath.getId());
			return updatedDeath;
			
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			log.error("updateDeath: Death Object is null and cannot be proceed");
			throw new IllegalArgumentException("Death Object is null and cannot be processed", err);
		}
	}

	@Override
	public void deleteDeathById(Long id){
		findById(id);
		try {
			deathRepository.deleteById(id);
			log.info("deleteDeathById: death with id: " + id + " was deleted successfully.");
		}catch(IllegalArgumentException err) {
			log.error("deleteDeathById: death id is null, so can't proceed.");
			throw new IllegalArgumentException("death id is null, so can't proceed.");
		}
	}

	@Override
	public Set<Death> findAllDeaths() {
		Set<Death> findallDeathsSet = new HashSet<>(); 
		this.deathRepository.findAll().forEach(findallDeathsSet::add);
		log.info("findAllDeaths: {} deaths were found successfully.", findallDeathsSet.size());
		return findallDeathsSet;
	}

	@Override
	public Death findById(Long id) {
		try {
			Death foundDeath = deathRepository.findById(id).orElseThrow();
			log.info("findById: Death with id: " + id + " was found successfully.");
			return foundDeath;
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			log.error("findById: Death Object has null id, and cannot be processed");
			throw new IllegalArgumentException("Death Object has null id, and cannot be processed", err);
		} catch (NoSuchElementException err) {
			log.error("findById: No Element of type Death with id {} was found in the database.", id);
			throw new NoElementFoundException("No Element of type Death with id " + id + " was found in the database.", err); 
		}
	}



}
