package com.medkha.lol_notes.controllers;

import java.util.Set;

import javax.validation.Valid;

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

import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.services.ReasonService;

@RestController
@RequestMapping(path="reasons",
				produces="application/json")
public class ReasonController {

	private ReasonService reasonService;
	
	public ReasonController(ReasonService reasonService) {
		this.reasonService = reasonService; 
	}
	@GetMapping(produces = "application/json")
	public Set<ReasonDTO> allReasons(){
		return this.reasonService.findAllReasons();
	}

	@GetMapping(value = "/{reasonId}", produces = "application/json")
	public ReasonDTO getReason(@PathVariable("reasonId") Long reasonId) {
		return this.reasonService.findById(reasonId);
	}
	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public ReasonDTO postReason(@Valid @RequestBody ReasonDTO reason) {
		return this.reasonService.createReason(reason);
	}

	@PutMapping(path = "/{reasonId}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public ReasonDTO putReason(@PathVariable("reasonId") Long reasonId,
							@Valid @RequestBody ReasonDTO reason) {
		reason.setId(reasonId);
		return this.reasonService.updateReason(reason);
	}

	@DeleteMapping(value = "/{reasonId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteReason(@PathVariable("reasonId") Long reasonId) {
		this.reasonService.deleteReason(reasonId);
	}

}
