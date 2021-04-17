package com.medkha.lol_notes.controllers;

import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

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

import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.services.ReasonService;

@RestController
@RequestMapping(path="reasons",
				produces="application/json")
public class ReasonController {
	
	@Autowired
	private ReasonService reasonService;
	
	public ReasonController(ReasonService reasonService) {
		this.reasonService = reasonService; 
	}
	
	@GetMapping(produces = "application/json")
	public Set<Reason> allReasons(){
		return this.reasonService.findAllReasons(); 
	}
	
	@GetMapping(value = "/{reasonId}", produces = "application/json")
	public Reason getReason(@PathVariable("reasonId") Long reasonId) {
		return this.reasonService.findById(reasonId); 
	}
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Reason postReason(@Valid @RequestBody Reason reason) throws Exception {
		return this.reasonService.createReason(reason); 
	}
	
	@PutMapping(path = "/{reasonId}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Reason putReason(@PathVariable("reasonId") Long reasonId, 
							@Valid @RequestBody Reason reason) throws Exception { 
		reason.setId(reasonId);
		return this.reasonService.updateReason(reason); 
	}
	
	@DeleteMapping(value = "/{reasonId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReason(@PathVariable("reasonId") Long reasonId) {
		this.reasonService.deleteReason(reasonId);
	}
	
}
