package com.medkha.lol_notes.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.DeathId;
import com.medkha.lol_notes.services.DeathService;

@RestController
@RequestMapping("deaths")
public class DeathController {
	@Autowired 
	private DeathService deathService ;
	
	@GetMapping(produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public Set<Death> getAllDeaths(){
		return this.deathService.findAllDeaths(); 
	}
	
	@GetMapping(value="/{gameId}/{reasonId}", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public Death getDeathById(
			@PathVariable("gameId") Long game_id,
			@PathVariable("reasonId") Long reason_id) { 
		return this.deathService.findById(new DeathId(game_id, reason_id)); 
	}
	
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Death postDeath(@RequestBody Death death) { 
		return this.deathService.createDeath(death); 
	}
	
	@PutMapping(value = "/{gameId}/{reasonId}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Death putDeath(@PathVariable("gameId") Long game_id,
							@PathVariable("reasonId") Long reason_id,
							@RequestBody Death death) {
		death.setId(new DeathId(game_id, reason_id));
		return this.deathService.updateDeath(death); 
	}
}
