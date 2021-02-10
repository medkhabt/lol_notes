package com.medkha.lol_notes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DeathServiceTest {

	@Autowired
	DeathService deathService;
	
	@Autowired 
	GameService gameService; 
	
	@Autowired
	ReasonService reasonService; 
	
	private Game game; 
	private Reason reason; 
	@BeforeEach
	public void init() throws Exception {
		this.game = new Game(Role.ADC, Champion.KAISA);
		this.gameService.createGame(game); 
		
		this.reason = new Reason("Outnumbered"); 
		this.reasonService.createReason(reason); 	
		
	}
	
	@AfterEach
	public void reset() {
		this.gameService.deleteGame(this.game.getId());
		this.reasonService.deleteReason(this.reason.getId());
	}
	
	@Test
	public void shoudRetNotNullId_When_CreateDeath() throws Exception { 
		
		// given 
		Death death = new Death(10, this.reason, this.game); 
		
		// when 
		Death result = this.deathService.createDeath(death); 
		log.info(result.toString());
		
		// then
		assertNotNull(result.getId()); 
	}
	
	
	@Test
	public void shouldRetNewReasonId_When_UpdateDeathsReason() throws Exception {
		
		// given 
		Death death = new Death(10, this.reason, this.game); 
		death = this.deathService.createDeath(death); 
		
		Reason newReason = new Reason("the new Reason");
		newReason = this.reasonService.createReason(newReason); 
		
		// when 
		death.setReasonOfDeath(newReason);
		Death result = this.deathService.updateDeath(death);
		
//		log.info("******************************");
		
		// then
		assertEquals(newReason.getId(), this.deathService.findById(result.getId()).getReasonOfDeath().getId());
		
	}
}
