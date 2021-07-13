package com.medkha.lol_notes.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
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
import com.medkha.lol_notes.dto.GameDTO;
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
		Game game = new Game(10, "solo", "midlane");
		game.setId((long)1);
		
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(game)))
			.andExpect(status().isCreated()); 
	}
	
	@Test
	public void whenNullRoleOrChampion_thenReturns400_CreateGame() throws Exception {
		Game gameWithoutRoleOrLane = new Game(10, null, null);
		gameWithoutRoleOrLane.setId((long)1);
		Game gameWithoutChampion = new Game(null, "solo", "midlane");
		gameWithoutChampion.setId((long)2);
		
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gameWithoutRoleOrLane)))
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
		GameDTO gameDTO = mock(GameDTO.class);
		when(gameDTO.getId()).thenReturn((long)1);
		when(gameDTO.getChampionId()).thenReturn(1);

		when(this.gameService.updateGame(gameDTO)).thenThrow(NoElementFoundException.class);
		
		mockMvc.perform(put("/games/{gameId}", gameDTO.getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(gameDTO)))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void whenRoleOrChampionIsNull_thenReturn400_UpdateReason() throws Exception {
		Game gameWithoutRoleOrLane = new Game(10, null, null);
		gameWithoutRoleOrLane.setId((long)1);
		Game gameWithoutChampion = new Game(null, "solo", "midlane");
		gameWithoutChampion.setId((long)2);
		
		mockMvc.perform(put("/games/{gameId}", gameWithoutRoleOrLane.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gameWithoutRoleOrLane)))
			.andExpect(status().isBadRequest());
		
		mockMvc.perform(put("/games/{gameId}", gameWithoutChampion.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(gameWithoutChampion)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenValidInput_ThenReturns200_UpdateGame() throws Exception{
		GameDTO gameDTO = mock(GameDTO.class);
		gameDTO.setId((long)1);
		gameDTO.setChampionId(10);
		gameDTO.setLaneName("midlane");
		gameDTO.setRoleName("solo");

		when(this.gameService.updateGame(gameDTO)).thenReturn(gameDTO);
		
		mockMvc.perform(put("/games/{gameId}", gameDTO.getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(gameDTO)))
				.andExpect(status().isOk());
	}
	
	@Test
	public void whenValidInput_ThenReturnsGame_FindById() throws Exception {
		GameDTO gameDTO = mock(GameDTO.class);
		gameDTO.setId((long)1);
		gameDTO.setChampionId(10);
		gameDTO.setLaneName("midlane");
		gameDTO.setRoleName("solo");
		
		when(this.gameService.findById(gameDTO.getId())).thenReturn(gameDTO);
		MvcResult mvcResult = mockMvc.perform(get("/games/{gameId}", (long) 1))
									.andReturn(); 
		
		
		
		String actualResponseBody = mvcResult.getResponse().getContentAsString(); 
		
		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
						objectMapper.writeValueAsString(gameDTO)
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
