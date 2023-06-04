package com.medkha.lol_notes.controllers;

import java.util.Set;

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

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.services.DeathService;

@RestController
@RequestMapping("deaths")
public class DeathController {

	private DeathService deathService ;

	public DeathController(DeathService deathService){
	    this.deathService = deathService;
	}

	@GetMapping(produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public Set<DeathDTO> getAllDeaths(){
		return this.deathService.findAllDeaths();
	}

	@GetMapping(value = "/count" , produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public Integer getAllDeathsCount(){
		return this.deathService.countAllDeaths();
	}

	@GetMapping(value="/{deathId}", produces="application/json")
	@ResponseStatus(HttpStatus.OK)
	public DeathDTO getDeathById(
			@PathVariable("deathId") Long death_id) {
		return this.deathService.findById(death_id);
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public DeathDTO postDeath(@RequestBody DeathDTO death) {
		return this.deathService.createDeath(death);
	}

	@PutMapping(value = "/{deathId}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public DeathDTO putDeath(@PathVariable("deathId") Long death_id,
							@RequestBody DeathDTO death) {
		death.setId(death_id);
		return this.deathService.updateDeath(death);
	}

	@DeleteMapping(value="/{deathId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteDeath(@PathVariable("deathId") Long death_id) {
		this.deathService.deleteDeathById(death_id);
	}
}