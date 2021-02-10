package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
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
	public Death updateDeath(Death death) throws Exception {
		if(existsInDataBase(death.getId())) {
			death.getGame().addDeath(death);
			this.gameSerivce.updateGame(death.getGame());
			
			return this.deathRepository.save(death); 
		}
		else { 
			throw new Exception("Reason instance not existing ( " + death.toString() + " ).");
		}
	}

	@Override
	public void deleteDeathById(Long id) {
		if(existsInDataBase(id)) { 
			this.deathRepository.deleteById(id);
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
