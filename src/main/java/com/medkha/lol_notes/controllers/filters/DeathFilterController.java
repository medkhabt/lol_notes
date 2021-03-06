package com.medkha.lol_notes.controllers.filters;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@RestController
@RequestMapping("/deaths")
public class DeathFilterController {
	private static Logger log = LoggerFactory.getLogger(DeathFilterController.class);
	@Autowired private DeathFilterService deathFilterService;
	@Autowired private GameService gameService;
	@Autowired private ReasonService reasonService;
	@Autowired private DeathService deathService;

	@GetMapping(value = "/filter")
	@ResponseStatus(HttpStatus.OK)
	public Set<Death> getDeathsByFiltersController(
			@RequestParam Optional<Long> gameId,
			@RequestParam Optional<Long> reasonId){

		List<Predicate<Death>> deathFilterPredicates = new ArrayList<>();

		if(gameId.isPresent()) {
			Game game = gameService.findById(gameId.get());
			deathFilterPredicates.add(game.getPredicate());
		}
		if(reasonId.isPresent()) {
			Reason reason = reasonService.findById(reasonId.get());
			deathFilterPredicates.add(reason.getPredicate());
		}
		return deathFilterService.getDeathsByFilter(deathFilterPredicates).collect(Collectors.toSet());

	}
}
