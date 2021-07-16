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
import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.services.DeathService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeathController.class)
public class DeathControllerTest {
	
	@MockBean
	private DeathService deathService;
	@Autowired 
	private ObjectMapper objectMapper;
	@Autowired 
	private MockMvc mockMvc;

	@Test
	public void whenValidInput_ThenReturns201_CreateDeath() throws Exception {
		mockMvc.perform(post("/deaths")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(sampleDeathDTOWithId())))
					.andExpect(status().isCreated());
	}

	@Test
	public void whenNullDeath_thenReturns400_CreateDeath() throws Exception {
		mockMvc.perform(post("/deaths")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(null)))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void whenDeathIdIsntInDb_theReturn403_UpdateDeath() throws Exception{
		when(this.deathService.updateDeath(sampleDeathDTOWithId())).thenThrow(NoElementFoundException.class);

		mockMvc.perform(put("/deaths/{deathId}", sampleDeathDTOWithId().getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(sampleDeathDTOWithId())))
				.andExpect(status().isForbidden());
	}

	@Test
	public void whenValidInput_ThenReturns200_UpdateDeath() throws Exception{
		when(this.deathService.updateDeath(sampleDeathDTOWithId())).thenReturn(sampleDeathDTOWithId());

		mockMvc.perform(put("/deaths/{deathId}", sampleDeathDTOWithId().getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(sampleDeathDTOWithId())))
				.andExpect(status().isOk());
	}

	@Test
	public void whenDeathIdIsntInDb_thenReturn403_getDeathById() throws Exception {
		Long id = (long)1;
		when(this.deathService.findById(id)).thenThrow(NoElementFoundException.class);
		mockMvc.perform(get("/deaths/{deathId}",id)
					.contentType("application/json"))
		   		.andExpect(status().isForbidden());
	}

	@Test
	public void whenValidInput_ThenReturns200_getDeathById() throws Exception {
		when(this.deathService.findById(sampleDeathDTOWithId().getId())).thenReturn(sampleDeathDTOWithId());

		MvcResult mvcResult = mockMvc.perform(get("/deaths/{deathId}", sampleDeathDTOWithId().getId())
					.contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn();
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
						objectMapper.writeValueAsString(sampleDeathDTOWithId())
				);
	}

	@Test
	public void whenDeathIdIsntInDb_thenReturn403_deleteById() throws Exception {
		Long id = (long) 1;
		doThrow(NoElementFoundException.class).when(this.deathService).deleteDeathById(id);
		mockMvc.perform(delete("/deaths/{deathId}", id))
		.andExpect(status().isForbidden());
	}

	@Test
	public void whenValidInput_ThenReturn204_deleteById() throws Exception {
		mockMvc.perform(delete("/deaths/{deathId}", sampleDeathDTOWithId().getId()))
				.andExpect(status().isNoContent());
	}

	private DeathDTO sampleDeathDTOWithId(){
		DeathDTO death = new DeathDTO();
		death.setId((long)1);
		death.setMinute(1);
		death.setGame(GameDTO.copy(sampleGameDTOWithId()));
		death.setReason(ReasonDTO.copy(sampleReasonDTOWithId()));
		return death;
	}

	private ReasonDTO sampleReasonDTOWithId(){
		ReasonDTO reason = new ReasonDTO();
		reason.setId((long) 1);
		reason.setDescription("sample reason");
		return reason;
	}

	private GameDTO sampleGameDTOWithId(){
		GameDTO game = new GameDTO();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setId((long) 1);
		return game;
	}
}
