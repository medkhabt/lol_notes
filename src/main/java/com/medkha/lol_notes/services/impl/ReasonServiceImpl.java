package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
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
	public Reason updateReason(Reason reason){
		try {
			findById(reason.getId()); 
			return this.reasonRepository.save(reason); 
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			throw new IllegalArgumentException("Resaon Object is null and cannot be processed", err);
		}
		
	}

	@Override
	public void deleteReason(Long id) {
		findById(id); 
		this.reasonRepository.deleteById(id);
	}

	@Override
	public Set<Reason> findAllReasons() {
		Set<Reason> findallReasonsSet = new HashSet<>(); 
		this.reasonRepository.findAll().forEach(findallReasonsSet::add);
		return findallReasonsSet;
	}

	@Override
	public Reason findById(Long id) {
		try {
			return this.reasonRepository.findById(id).orElseThrow();			
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			throw new IllegalArgumentException("Reason Object has null id, and cannot be processed", err); 
		} catch (NoSuchElementException err) { 
			throw new NoElementFoundException("No Element of type Reason with id " + id + " was found in the database.", err); 
		}
	}

	
}
