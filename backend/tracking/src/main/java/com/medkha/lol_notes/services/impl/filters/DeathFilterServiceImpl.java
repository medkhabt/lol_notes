package com.medkha.lol_notes.services.impl.filters;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@Service
public class DeathFilterServiceImpl implements DeathFilterService{
	private static Logger log = LoggerFactory.getLogger(DeathFilterService.class); 

	private final DeathService deathService;

	public DeathFilterServiceImpl(DeathService deathService) {
		this.deathService = deathService;
	}
	@Override
	public Stream<DeathDTO> getDeathsByFilter(List<Predicate<DeathDTO>> listDeathPredicate) {
//		// hmm doesn't feel quite right getting all Deaths from the db, this will cause performance issues in the future.
//		// At least i should get it a stream, so i can cap the result when i find what i wanted ( for example a page of 100
//		// result after the filters.
		log.info("enter getDeathsByFilter:");
		Stream<DeathDTO> deaths = deathService.findAllDeaths().stream()
										.filter(listDeathPredicate.stream().reduce(x->true, Predicate::and));
		return deaths;
	}

	@Override
	public Double getRatioDeathsByFilter(List<Predicate<DeathDTO>> collect) {
		int deathCount = deathService.countAllDeaths();
		if(deathCount == 0) {
			log.info("getRatioDeathsByFilter: No deaths found in the database, the ratio is 0");
			return (double) 0;
		}
		long deathAfterFilterCount = getDeathsByFilter(collect).count();
		Double result = ( (double)deathAfterFilterCount/ (double)deathCount ) ;
		log.info("getRatioDeathsByFilter: {} ratio found successfully for the specified filters");
		return result;
	}

}