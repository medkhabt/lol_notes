package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.DeathService;

@Service
public class DeathServiceImpl implements DeathService{
	private static Logger log = LoggerFactory.getLogger(DeathServiceImpl.class);

	private final DeathRepository deathRepository;
	private final MapperService mapperService;

	public DeathServiceImpl(DeathRepository deathRepository, MapperService mapperService) {
		this.deathRepository = deathRepository;
		this.mapperService = mapperService;
	}
	@Override
	public DeathDTO createDeath(DeathDTO death){
		try {
			Death createdDeath = deathRepository.save(mapperService.convert(death, Death.class));
			DeathDTO convertedCreatedDeath = mapperService.convert(createdDeath, DeathDTO.class);
			log.info("createDeath: Death with id: {} created successfully.", createdDeath.getId());
			return convertedCreatedDeath;
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			log.error("createDeath: Death Object is null and cannot be proceed");
			throw new IllegalArgumentException("Death Object is null and cannot be processed", err);
		}
	}

	@Override
	@Transactional
	public DeathDTO updateDeath(DeathDTO death){
		try {
			findById(death.getId());
			Death updatedDeath = deathRepository.save(mapperService.convert(death, Death.class));
			DeathDTO updatedDeathDTO = this.mapperService.convert(updatedDeath, DeathDTO.class);
			log.info("updateDeath: Death with id: {} updated successfully.", updatedDeathDTO.getId());
			return updatedDeathDTO;
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			log.error("updateDeath: Death Object is null and cannot be proceed");
			throw new IllegalArgumentException("Death Object is null and cannot be processed", err);
		}
	}

	@Override
	public void deleteDeathById(Long id){
		findById(id);
			deathRepository.deleteById(id);
			String messageSuccess= String.format("deleteDeathById: death with id: %d was deleted successfully.", id);
			log.info(messageSuccess);
	}

	@Override
	public Set<DeathDTO> findAllDeaths() {
		Set<Death> findallDeathsSet = new HashSet<>();
		this.deathRepository.findAll().forEach(findallDeathsSet::add);
		log.info("findAllDeaths: {} deaths were found successfully.", findallDeathsSet.size());
		return mapperService.convertSet(findallDeathsSet, DeathDTO.class);
	}

	@Override
	public Integer countAllDeaths() {
		int result = this.deathRepository.countAllDeaths();
		log.info("countAllDeaths: {} deaths were found successfully", result);
		return result;
	}

	@Override
	public DeathDTO findById(Long id) {
		try {
			Death foundDeath = deathRepository.findById(id).orElseThrow();
			DeathDTO convertedFoundDeath = mapperService.convert(foundDeath, DeathDTO.class);
			String messgeSuccess = String.format("findById: Death with id: %d was found successfully.", id);
			log.info(messgeSuccess);
			return convertedFoundDeath;
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			log.error("findById: Death Object has null id, and cannot be processed");
			throw new IllegalArgumentException("Death Object has null id, and cannot be processed", err);
		} catch (NoSuchElementException err) {
			log.error("findById: No Element of type Death with id {} was found in the database.", id);
			throw new NoElementFoundException("No Element of type Death with id " + id + " was found in the database.", err); 
		}
	}



}
