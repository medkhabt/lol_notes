package com.medkha.lol_notes.controllers.filter;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.medkha.lol_notes.controllers.filters.DeathFilterController;
import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeathFilterController.class)
public class DeathFilterControllerTest {
	
	@MockBean 
	private DeathFilterService deathFilterService;
	
	@MockBean 
	private GameService gameService; 

	@MockBean
	private ReasonService reasonService;

	@MockBean
	private DeathService deathService;
	
	@Autowired 
	private MockMvc mockMvc;



	public Game initGame() { 
		Game game = new Game(Role.ADC, Champion.JINX);
		game.setId((long)1);
		return game;  
	}
	
	public Reason initReason() { 
		Reason reason = new Reason("reason"); 
		reason.setId((long)1);
		return reason; 
	}
	public Set<Death> initDeaths() {
		
		
		
		Death death = new Death(11, initReason(), initGame()); 
		death.setId((long)1);
		
		Death death1 =  new Death(15, initReason(), initGame()); 
		death1.setId((long) 2);
		
		
		
		return Stream.of(death, death1).collect(Collectors.toSet()); 
	}
	

	@Test 
	public void whenGameIdDoesntExistIndDb_ThenReturn403_GetDeathsByGame() throws Exception { 
		
		when(gameService.findById((long)1)).thenThrow(NoElementFoundException.class); 
		mockMvc.perform(get("/deaths/filter")
					.param("gameId", "1")
					.contentType("application/json")
					)
				.andExpect(status().isForbidden()); 
	}

	@Test
	public void  whenNoQueryParam_ThenReturn200_FindAllDeaths() throws Exception {
		when(deathService.findAllDeaths()).thenReturn(initDeaths());
		mockMvc.perform(get("/deaths/filter")
				.contentType("application/json")
		)
				.andExpect(status().isOk());

	}
}
