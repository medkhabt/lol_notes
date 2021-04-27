package com.medkha.lol_notes.controllers.filters;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@RestController
@RequestMapping("/deaths")
public class DeathFilterController {

	@Autowired private DeathFilterService deathFilterService;
	
	@GetMapping(value = "/filter")
	public Set<Death>getDeathsByEtapeController( @RequestParam Long gameId){ 
		return this.deathFilterService.getDeathsByGame(gameId); 
	}
}
