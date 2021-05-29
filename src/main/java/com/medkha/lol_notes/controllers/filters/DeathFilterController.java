package com.medkha.lol_notes.controllers.filters;


import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	@Autowired private DeathFilterService deathFilterService;
	@Autowired private GameService gameService;
	@Autowired private ReasonService reasonService;
	@Autowired private DeathService deathService;

	// I don't know if Putting ResponseEntity as a return is worth it or not, but i just wanted to try it in this case.
	@GetMapping(value = "/filter")
//	@ResponseStatus(HttpStatus.OK)
	public ResponseEntity<?> getDeathsByFiltersController(
			HttpServletResponse response,
			@RequestParam Optional<Long> gameId,
			@RequestParam Optional<Long> reasonId){
		if(gameId.isPresent() && reasonId.isPresent()) {
			// TODO FILTER FOR GAME AND REASON.
			return new ResponseEntity<>(HttpStatus.METHOD_NOT_ALLOWED);
		}
		else if(gameId.isPresent()) {
			Game game = gameService.findById(gameId.get());
			return new ResponseEntity<>(this.deathFilterService.getDeathsByGame(game), HttpStatus.OK);
		}
		else if(reasonId.isPresent()) {
			Reason reason = reasonService.findById(reasonId.get());
			return new ResponseEntity<>(this.deathFilterService.getDeathsByReason(reason), HttpStatus.OK);
		}
		else {
			return new ResponseEntity<>(deathService.findAllDeaths(), HttpStatus.OK);
		}


	}
}
