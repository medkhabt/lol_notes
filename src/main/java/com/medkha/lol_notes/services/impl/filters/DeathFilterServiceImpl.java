package com.medkha.lol_notes.services.impl.filters;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@Service
public class DeathFilterServiceImpl implements DeathFilterService{
	private static Logger log = LoggerFactory.getLogger(DeathFilterService.class); 

	private DeathService deathService;

	public DeathFilterServiceImpl(DeathService deathService) {
		this.deathService = deathService;
	}

	@Override
	public Stream<Death> getDeathsByFilter(List<Predicate<Death>> listDeathPredicate) {
		// hmm doesn't feel quite right getting all Deaths from the db, this will cause performance issues in the future.
		// At least i should get it a stream, so i can cap the result when i find what i wanted ( for example a page of 100
		// result after the filters.
		log.info("enter getDeathsByFilter:");
		Stream<Death> deaths = deathService.findAllDeaths().stream()
										.filter(listDeathPredicate.stream().reduce(x->true, Predicate::and));
		return deaths;
	}

}
