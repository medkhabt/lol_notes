package com.medkha.lol_notes.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Role;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.services.GameService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)

public class GameControllerTest {

	@MockBean
	private GameService gameService; 
	
	@Autowired 
	private ObjectMapper objectMapper; 
	
	@Autowired 
	MockMvc mockMvc;
	
	@Test 
	public void whenValidInput_ThenReturns201_CreateGame() throws Exception { 
		Game game = new Game(Role.ADC, Champion.JINX);  
		
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(game)))
			.andExpect(status().isCreated()); 
	}
	
	@Test
	public void whenNullRoleOrChampion_thenReturns400_CreateGame() throws Exception { 
		Game gameWithoutRole = new Game(null, Champion.KAISA);  
		Game gameWithoutChampion = new Game(Role.ADC, null); 
		
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gameWithoutRole)))
			.andExpect(status().isBadRequest());
		
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gameWithoutChampion)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenNullGame_thenReturn400_CreateGame() throws Exception { 
		Game game = null ;
		
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(game)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenGameIdIsntInDb_theReturn403_UpdateReason() throws Exception{
		Game game = new Game(Role.ADC, Champion.KAISA); 
		game.setId((long)1);
		
		when(this.gameService.updateGame(game)).thenThrow(NoElementFoundException.class);
		
		mockMvc.perform(put("/games/{gameId}", game.getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(game)))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void whenRoleOrChampionIsNull_thenReturn400_UpdateReason() throws Exception { 
		Game gameWithoutRole = new Game(null, Champion.KAISA);  
		gameWithoutRole.setId((long) 1);
		Game gameWithoutChampion = new Game(Role.ADC, null); 
		gameWithoutChampion.setId((long) 2);
		
		mockMvc.perform(put("/games/{gameId}", gameWithoutRole.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gameWithoutRole)))
			.andExpect(status().isBadRequest());
		
		mockMvc.perform(put("/games/{gameId}", gameWithoutChampion.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gameWithoutChampion)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenValidInput_ThenReturns200_UpdateGame() throws Exception{ 
		Game game = new Game(Role.ADC, Champion.KAISA); 
		game.setId((long)1);
		
		when(this.gameService.updateGame(game)).thenReturn(game); 
		
		mockMvc.perform(put("/games/{gameId}", game.getId()) 
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(game)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void whenValidInput_ThenReturnsGame_FindById() throws Exception { 
		Game game = new Game(Role.ADC, Champion.KAISA); 
		game.setId((long)1);
		
		when(this.gameService.findById(game.getId())).thenReturn(game); 
		MvcResult mvcResult = mockMvc.perform(get("/games/{gameId}", (long) 1))
									.andReturn(); 
		
		
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString(); 
		
		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
						objectMapper.writeValueAsString(game)
				);
	}
	
	@Test
	public void whenGameIdIsntInDb_thenReturn403_FindById() throws Exception { 
		Long id = (long) 1; 
		
		when(this.gameService.findById(id)).thenThrow(NoElementFoundException.class);
		
		mockMvc.perform(get("/games/{gameId}", id))
					.andExpect(status().isForbidden()); 
	}
	
	
	@Test 
	public void whenValidInput_ThenReturn204_deleteGame() throws Exception { 
		Long id = (long) 1 ; 
		
		mockMvc.perform(delete("/games/{gameId}", id))
					.andExpect(status().isNoContent());
	}
	
	
	@Test 
	public void whenGameIdIsntInDb_thenReturn403_deleteGame() throws Exception { 
		Long id = (long) 1; 
		
		doThrow(NoElementFoundException.class).when(this.gameService).deleteGame(id); 
		
		mockMvc.perform(delete("/games/{gameId}", id))
					.andExpect(status().isForbidden());
		
	}
	
}
