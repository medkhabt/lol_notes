package com.medkha.lol_notes.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

	@GetMapping(value="/")
	public String Home() { 
		return "heroku working"; 
	}
}
