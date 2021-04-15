package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.ReasonService;

@Service
public class ReasonServiceImpl implements ReasonService{
	
	@Autowired
	private ReasonRepository reasonRepository; 

	@Override
	public Reason createReason(Reason reason){
		try {
			return this.reasonRepository.save(reason); 
		
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			throw new IllegalArgumentException("Reason Object is null and cannot be processed", err); 
		}
	
	}

	@Override
	public Reason updateReason(Reason reason) throws Exception {
		if(existsInDataBase(reason.getId())) {
			return this.reasonRepository.save(reason); 
		}
		else { 
			throw new Exception("Reason instance not existing ( " + reason.toString() + " ).");
		}
	}

	@Override
	public void deleteReason(Long id) {
		if(existsInDataBase(id)) { 
			this.reasonRepository.deleteById(id);
		}
	}

	@Override
	public Set<Reason> findAllReasons() {
		Set<Reason> findallReasonsSet = new HashSet<>(); 
		this.reasonRepository.findAll().forEach(findallReasonsSet::add);
		return findallReasonsSet;
	}

	@Override
	public Reason findById(Long id) {
		return this.reasonRepository.findById(id).orElse(null);
	}

	
	public Boolean existsInDataBase(Long id) { 
		return id != null && findById(id) != null; 
	}
}
