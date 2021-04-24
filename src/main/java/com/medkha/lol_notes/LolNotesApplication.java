package com.medkha.lol_notes;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.ReasonService;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


@SpringBootApplication
@EnableSwagger2
public class LolNotesApplication {

	public static void main(String[] args) {
		SpringApplication.run(LolNotesApplication.class, args);
	}

//	@Bean
//	  public CommandLineRunner dataLoader(ReasonService reasonService, GameService gameService, DeathService deathService) {
//	    return new CommandLineRunner() {
//	      @Override
//	      public void run(String... args) throws Exception {
//	        reasonService.createReason(new Reason("OutNumbered")); 
//	        reasonService.createReason(new Reason("Jgl gank")); 
//	        reasonService.createReason(new Reason("Over Staying")); 
//	        reasonService.createReason(new Reason("Match-up")); 
//	        reasonService.createReason(new Reason("Positionning")); 
//	        
//	        
//	        gameService.createGame(new Game(Role.ADC, Champion.KAISA)); 
//	        
//	        
//	        deathService.createDeath(new Death(10, reasonService.findById((long) 103), gameService.findById((long)105))); 
//	        
//	        
//	      }
//	    };
//	}
}
