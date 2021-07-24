package com.medkha.lol_notes.services.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@Service
public class ReasonServiceImpl implements ReasonService{
	
	private static final Logger log = 
			LoggerFactory.getLogger(ReasonServiceImpl.class); 
	
	private final ReasonRepository reasonRepository;
	private final DeathService deathService;
	private final DeathFilterService deathFilterService;
	private final MapperService mapperService;

	public ReasonServiceImpl(ReasonRepository reasonRepository, DeathService deathService, DeathFilterService deathFilterService, MapperService mapperService) {
		this.reasonRepository = reasonRepository;
		this.deathService = deathService;
		this.deathFilterService = deathFilterService;
		this.mapperService = mapperService;
	}

	@Override
	public ReasonDTO createReason(ReasonDTO reason){
		try {
			Reason createdReason = this.reasonRepository.save(mapperService.convert(reason, Reason.class));
			log.info("createReason: Reason with id: " + createdReason.getId() + " created successfully.");
			return mapperService.convert(createdReason, ReasonDTO.class);
		
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			log.error("createReason: Reason Object is null and cannot be proceed");
			throw new IllegalArgumentException("Reason Object is null and cannot be processed", err); 
		}
	}

	@Override
	public ReasonDTO updateReason(ReasonDTO reason){
		try {
			findById(reason.getId());
			Reason updatedReason = reasonRepository.save(mapperService.convert(reason, Reason.class));
			log.info("updateReason: Reason with id: " + updatedReason.getId() + " was updated successfully.");
			return mapperService.convert(updatedReason, ReasonDTO.class);
		} catch (InvalidDataAccessApiUsageException | NullPointerException err) {
			log.error("updateReason: Reason Object is null and cannot be proceed");
			throw new IllegalArgumentException("Resaon Object is null and cannot be processed", err);
		}
	}

	@Override
	public void deleteReason(Long id) {
		ReasonDTO reasonFound = findById(id);
		deleteAssociatedDeaths(reasonFound);
		try {
			this.reasonRepository.deleteById(id);
			log.info("deleteReason: Reason with id: " + id + " was deleted successfully.");
		} catch (IllegalArgumentException err) {
			log.error("deleteReason: Reason id is null, so can't proceed.");
			throw new IllegalArgumentException("Reason id is null, so can't proceed.", err); 
		}
	}

	private void deleteAssociatedDeaths(ReasonDTO reason) {
		log.info("deleteAssociatedDeaths: start deleting associated deaths of reason with id: {}.", reason.getId());
		this.deathFilterService.getDeathsByFilter(Collections.singletonList(reason.getPredicate())).forEach(
				(d) -> {
					this.deathService.deleteDeathById(d.getId());
					log.info("deleteAssociatedDeaths: Delete death with id: {} successfully.", d.getId());
				}
		);
	}

	@Override
	public Set<ReasonDTO> findAllReasons() {
		Set<Reason> findallReasonsSet = new HashSet<>(); 
		this.reasonRepository.findAll().forEach(findallReasonsSet::add);
		log.info("findAllReasons: " + findallReasonsSet.size() + " reasons were found.");
		return mapperService.convertSet(findallReasonsSet, ReasonDTO.class);
	}

	@Override
	public ReasonDTO findById(Long id) {
		try {
			Reason foundReason = reasonRepository.findById(id).orElseThrow();
			log.info("findById: Reason with id: " + id + " was found successfully.");
			return mapperService.convert(foundReason, ReasonDTO.class);
		} catch (InvalidDataAccessApiUsageException | NullPointerException err ) {
			log.error("findById: Reason Object has null id, and cannot be processed");
			throw new IllegalArgumentException("Reason Object has null id, and cannot be processed", err); 
		} catch (NoSuchElementException err) {
			log.error("No Element of type Reason with id " + id + " was found in the database.");
			throw new NoElementFoundException("No Element of type Reason with id " + id + " was found in the database.", err); 
		}
	}

	
}
