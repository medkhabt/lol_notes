package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.Set;

import javax.transaction.Transactional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.GameService;

@Service
public class DeathServiceImpl implements DeathService{

	@Autowired
	private DeathRepository deathRepository;
	
	@Autowired
	private GameService gameSerivce;
	
	@Override
	@Transactional
	public Death createDeath(Death death) throws Exception {
		return this.deathRepository.save(death); 
	}

	@Override
	@Transactional
	public Death updateDeath(Death death) throws Exception {
		return null; 
	}

	@Override
	@Transactional
	public void deleteDeathById(Long id) throws Exception {
		
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

	@Override
	public Boolean existsInDataBase(Long id) {
		return id != null && findById(id) != null; 	
	}

}
