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
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;
import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.LaneDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.dto.RoleDTO;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@RestController
@RequestMapping("/deaths")
public class DeathFilterController {
	private static Logger log = LoggerFactory.getLogger(DeathFilterController.class);
	private final DeathFilterService deathFilterService;
	private final GameService gameService;
	private final ReasonService reasonService;

	public DeathFilterController(DeathFilterService deathFilterService, GameService gameService, ReasonService reasonService){
		this.deathFilterService = deathFilterService;
		this.gameService = gameService;
		this.reasonService = reasonService;
	}

	@GetMapping(value = "/filter")
	@ResponseStatus(HttpStatus.OK)
	public Set<DeathDTO> getDeathsByFiltersController(
			@RequestParam Optional<Long> gameId,
			@RequestParam Optional<Long> reasonId,
			@RequestParam Optional<Integer> championId,
			@RequestParam(name = "role") Optional<String> roleString,
			@RequestParam(name = "lane") Optional<String> laneString){

		List<Predicate<DeathDTO>> deathFilterPredicates = new ArrayList<>();
		if(gameId.isPresent()) {
			GameDTO game = GameDTO.proxy(gameId.get());
			deathFilterPredicates.add(game.getPredicate());
		}
		if(reasonId.isPresent()) {
			ReasonDTO reason = ReasonDTO.proxy(reasonId.get());
			deathFilterPredicates.add(reason.getPredicate());
		}
		if(championId.isPresent()) {
			ChampionEssentielsDto champion = ChampionEssentielsDto.proxy(championId.get());
			deathFilterPredicates.add(champion.getPredicate());
		}
		if(roleString.isPresent()) {
			RoleDTO role = new RoleDTO(roleString.get());
			deathFilterPredicates.add(role.getPredicate());
		}
		if(laneString.isPresent()) {
			LaneDTO lane = new LaneDTO(laneString.get());
			deathFilterPredicates.add(lane.getPredicate());
		}
		return deathFilterService.getDeathsByFilter(deathFilterPredicates).collect(Collectors.toSet());
	}
}
