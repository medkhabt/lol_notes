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
	public Death createDeath(Death death) throws Exception {
		Death tempDeath = death.copyDeath(); 
		
		
		if(death.getId() == null) { 
			
			tempDeath.setGame(null);
			tempDeath.setReasonOfDeath(null);	
			tempDeath = this.deathRepository.save(tempDeath); 
			
			if(death.getGame().getId() != null) {
				death.getGame().addDeath(death);
				tempDeath.setGame(death.getGame());
				tempDeath.setReasonOfDeath(death.getReasonOfDeath());
				this.gameSerivce.updateGame(death.getGame()); 
			}
			
			return this.deathRepository.save(tempDeath); 
		}
		else { 
			throw new Exception("id is not null"); 
		}
		
	
		
		
	}

	@Override
	@Transactional
	public Death updateDeath(Death death) throws Exception {
		if(existsInDataBase(death.getId())) {
			Game game = this.gameSerivce.findById(death.getGame().getId()); 
			Hibernate.initialize(game.getDeaths());
			game.addDeath(death);
			this.gameSerivce.updateGame(death.getGame());
			
			return this.deathRepository.save(death); 
		}
		else { 
			throw new Exception("Reason instance not existing ( " + death.toString() + " ).");
		}
	}

	@Override
	@Transactional
	public void deleteDeathById(Long id) {
		if(existsInDataBase(id)) { 
			Death deathToDelete = findById(id);
			this.deathRepository.deleteById(id);
			Game game = this.gameSerivce.findById(deathToDelete.getId()); 
			Hibernate.initialize(game.getDeaths());
			game.addDeath(deathToDelete);
		}
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
