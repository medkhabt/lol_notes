package com.medkha.lol_notes.controllers.filters;


import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.DeathFilterOption;
import com.medkha.lol_notes.dto.FilterSearchRequest;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@RestController
@RequestMapping("/deaths")
public class DeathFilterController {
	private static Logger log = LoggerFactory.getLogger(DeathFilterController.class);
	private final DeathFilterService deathFilterService;
	private final MapperService mapperService;

	public DeathFilterController(DeathFilterService deathFilterService, MapperService mapperService){
		this.deathFilterService = deathFilterService;
		this.mapperService = mapperService;

	}

	@GetMapping(value = "/filter")
	@ResponseStatus(HttpStatus.OK)
	public Set<DeathDTO> getDeathsByFiltersController(
			@RequestParam Map<String,String> requestParams){
		FilterSearchRequest filterDeathRequest = new FilterSearchRequest();
		filterDeathRequest.setParams(requestParams);
		log.info("FilterDeathRequest: " + filterDeathRequest.getParams());
		Set<DeathFilterOption> deathFilterOptions =
				this.mapperService.convertFilterSearchRequestToDeathFilterOptions(filterDeathRequest);
		log.info("deathFitlterOptions: " + deathFilterOptions );
		List<Predicate<DeathDTO>> deathFilterPredicates =
				deathFilterOptions.stream().map(DeathFilterOption::getPredicate).collect(Collectors.toList());
		return deathFilterService.getDeathsByFilter(deathFilterPredicates).collect(Collectors.toSet());
	}

	@GetMapping(value = "ratio/filter")
	@ResponseStatus(HttpStatus.OK)
	public Double getRatioDeathsByFilterController(
			@RequestParam Map<String,String> requestParams){
		FilterSearchRequest filterDeathRequest = new FilterSearchRequest();
		filterDeathRequest.setParams(requestParams);
		log.info("FilterDeathRequest: " + filterDeathRequest.getParams());
		Set<DeathFilterOption> deathFilterOptions =
				this.mapperService.convertFilterSearchRequestToDeathFilterOptions(filterDeathRequest);
		log.info("deathFitlterOptions: " + deathFilterOptions );
		List<Predicate<DeathDTO>> deathFilterPredicates =
				deathFilterOptions.stream().map(DeathFilterOption::getPredicate).collect(Collectors.toList());
		return deathFilterService.getRatioDeathsByFilter(deathFilterPredicates);
	}
}
