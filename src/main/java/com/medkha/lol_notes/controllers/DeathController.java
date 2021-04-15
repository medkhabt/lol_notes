package com.medkha.lol_notes.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.services.DeathService;

@RestController
@RequestMapping(path="deaths",
				produces="application/json")
public class DeathController {
	@Autowired
	private DeathService deathService;
	
	public DeathController(DeathService deathService) {
		this.deathService = deathService; 
	}
	@GetMapping(produces = "application/json")
	public Set<Death> allDeaths(){
		return this.deathService.findAllDeaths(); 
	}
	
	@GetMapping(value = "/{deathId}", produces = "application/json")
	public Death getDeath(@PathVariable("deathId") Long deathId) {
		return this.deathService.findById(deathId); 
	}
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Death postDeath(@RequestBody Death death) throws Exception {
		return this.deathService.createDeath(death); 
	}
	
	@PutMapping(path = "/{deathId}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Death putDeath(@PathVariable("deathId") Long deathId, 
							@RequestBody Death death) throws Exception { 
		death.setId(deathId);
		return this.deathService.updateDeath(death); 
	}
	
	@DeleteMapping("/{deathId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDeath(@PathVariable("deathId") Long deathId) throws Exception {
		this.deathService.deleteDeathById(deathId);
	}
}
