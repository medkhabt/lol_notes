package com.medkha.lol_notes.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.medkha.lol_notes.repositories.MatchHistoryRepository;
import com.medkha.lol_notes.services.*;
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
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@WebMvcTest(GameController.class)

public class GameControllerTest {

	@Autowired 
	private ObjectMapper objectMapper;
	@Autowired 
	MockMvc mockMvc;
	@MockBean
	private GameService gameService;
	@MockBean
	private ChampionService championService;
	@MockBean
	private QueueService queueService;
	@MockBean
	private RestTemplate restTemplate;
	@MockBean
	RiotLookUpService riotLookUpService;
	@MockBean
	private  LiveGameService liveGameService;
	@MockBean
	private MatchHistoryRepository matchHistoryRepository;



	@Test 
	public void whenValidInput_ThenReturns201_CreateGame() throws Exception {
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(sampleGameDTOWithId())))
			.andExpect(status().isCreated()); 
	}
	
	@Test
	public void whenNullRoleOrChampion_thenReturns400_CreateGame() throws Exception {
		when(this.gameService.createGame(sampleGameDTOWithoutRoleAndLane())).thenThrow(IllegalArgumentException.class);
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(sampleGameDTOWithoutRoleAndLane())))
			.andExpect(status().isBadRequest());

		when(this.gameService.createGame(sampleGameDTOWithoutChampion())).thenThrow(IllegalArgumentException.class);
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(sampleGameDTOWithoutChampion())))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenNullGame_thenReturn400_CreateGame() throws Exception { 
		mockMvc.perform(post("/games")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(null)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenGameIdIsntInDb_theReturn403_UpdateReason() throws Exception{
		when(this.gameService.updateGame(sampleGameDTOWithId())).thenThrow(NoElementFoundException.class);
		mockMvc.perform(put("/games/{gameId}", sampleGameDTOWithId().getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(sampleGameDTOWithId())))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void whenRoleOrChampionIsNull_thenReturn400_UpdateReason() throws Exception {
		when(this.gameService.updateGame(sampleGameDTOWithoutRoleAndLane())).thenThrow(IllegalArgumentException.class);
		mockMvc.perform(put("/games/{gameId}", sampleGameDTOWithoutRoleAndLane().getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(sampleGameDTOWithoutRoleAndLane())))
			.andExpect(status().isBadRequest());

		when(this.gameService.updateGame(sampleGameDTOWithoutChampion())).thenThrow(IllegalArgumentException.class);
		mockMvc.perform(put("/games/{gameId}", sampleGameDTOWithoutChampion().getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(sampleGameDTOWithoutChampion())))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenValidInput_ThenReturns200_UpdateGame() throws Exception{
		when(this.gameService.updateGame(sampleGameDTOWithId())).thenReturn(sampleGameDTOWithId());
		
		mockMvc.perform(put("/games/{gameId}", sampleGameDTOWithId().getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(sampleGameDTOWithId())))
				.andExpect(status().isOk());
	}
	
	@Test
	public void whenValidInput_ThenReturnsGame_FindById() throws Exception {
		when(this.gameService.findById(sampleGameDTOWithId().getId())).thenReturn(sampleGameDTOWithId());
		MvcResult mvcResult = mockMvc.perform(get("/games/{gameId}", (long) 1))
									.andReturn();
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
						objectMapper.writeValueAsString(sampleGameDTOWithId())
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
	private GameDTO sampleGameDTOWithoutChampion(){
		GameDTO gameWithoutChampion = GameDTO.copy(sampleGameDTOWithId());
		gameWithoutChampion.setId((long) 2);
		gameWithoutChampion.setChampionId(null);
		return gameWithoutChampion;
	}
	private GameDTO sampleGameDTOWithoutRoleAndLane(){
		GameDTO gameWithoutRoleOrLane = GameDTO.copy(sampleGameDTOWithId());
		gameWithoutRoleOrLane.setId((long) 3);
		gameWithoutRoleOrLane.setRoleName(null);
		gameWithoutRoleOrLane.setLaneName(null);
		return gameWithoutRoleOrLane;
	}
	private GameDTO sampleGameDTOWithId() {
		GameDTO game = new GameDTO();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setId((long) 1);
		return game;
	}
}
