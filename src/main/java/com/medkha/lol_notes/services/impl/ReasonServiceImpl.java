package com.medkha.lol_notes.services.impl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.ReasonService;

@Service
public class ReasonServiceImpl implements ReasonService{
	
	private static final Logger log = 
			LoggerFactory.getLogger(ReasonServiceImpl.class); 
	
	private final ReasonRepository reasonRepository;

	public ReasonServiceImpl(ReasonRepository reasonRepository) {
		this.reasonRepository = reasonRepository;
	}

	@Override
	public Reason createReason(Reason reason){
		try {
			Reason createdReason = this.reasonRepository.save(reason);
			log.info("createReason: Reason with id: " + createdReason.getId() + " created successfully.");
			return  createdReason; 
		
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			log.error("createReason: Reason Object is null and cannot be proceed");
			throw new IllegalArgumentException("Reason Object is null and cannot be processed", err); 
		}
	
	}

	@Override
	public Reason updateReason(Reason reason){
		try {
			
			findById(reason.getId()); 
			
			Reason updatedReason = reasonRepository.save(reason); 
			log.info("updateReason: Reason with id: " + updatedReason.getId() + " was updated successfully.");
			return updatedReason; 
			
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			
			log.error("updateReason: Reason Object is null and cannot be proceed");
			throw new IllegalArgumentException("Resaon Object is null and cannot be processed", err);
		}
		
	}

	@Override
	public void deleteReason(Long id) {
		findById(id); 
		
		try {
			
			this.reasonRepository.deleteById(id);
			log.info("deleteReason: Reason with id: " + id + " was deleted successfully.");
			
		} catch (IllegalArgumentException err) {
			log.error("deleteReason: Reason id is null, so can't proceed.");
			throw new IllegalArgumentException("Reason id is null, so can't proceed.", err); 
		}
	}

	@Override
	public Set<Reason> findAllReasons() {
		Set<Reason> findallReasonsSet = new HashSet<>(); 
		this.reasonRepository.findAll().forEach(findallReasonsSet::add);
		
		log.info("findAllReasons: " + findallReasonsSet.size() + " reasons were found.");
		
		return findallReasonsSet;
	}

	@Override
	public Reason findById(Long id) {
		try {
			
			Reason foundReason = reasonRepository.findById(id).orElseThrow();
			log.info("findById: Reason with id: " + id + " was found successfully.");
			return foundReason; 	
			
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			
			log.error("findById: Reason Object has null id, and cannot be processed");
			throw new IllegalArgumentException("Reason Object has null id, and cannot be processed", err); 
		} catch (NoSuchElementException err) { 
			
			log.error("No Element of type Reason with id " + id + " was found in the database.");
			throw new NoElementFoundException("No Element of type Reason with id " + id + " was found in the database.", err); 
		}
	}

	
}
