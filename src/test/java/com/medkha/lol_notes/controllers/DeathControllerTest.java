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
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;
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
	
	public Death initDeath() {
		Reason reason = new Reason("reason"); 
		reason.setId((long)1);
		Game game = new Game(Role.ADC, Champion.JINX);
		game.setId((long)1);
		
		Death death = new Death(11, reason, game); 
		death.setId((long)1);
		return death; 
	}
	@Test 
	public void whenValidInput_ThenReturns201_CreateDeath() throws Exception {
		 
		Death death = initDeath(); 
		
		mockMvc.perform(post("/deaths")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(death)))
					.andExpect(status().isCreated()); 
	}
	
	@Test
	public void whenNullDeath_thenReturns400_CreateDeath() throws Exception { 
		Death death = null;
		
		mockMvc.perform(post("/deaths")
						.contentType("application/json")
						.content(objectMapper.writeValueAsString(death)))
				.andExpect(status().isBadRequest());
		
	}
	
	@Test
	public void whenDeathIdIsntInDb_theReturn403_UpdateDeath() throws Exception{
		Death death = initDeath(); 
		
		
		when(this.deathService.updateDeath(death)).thenThrow(NoElementFoundException.class);
		
		mockMvc.perform(put("/deaths/{deathId}", death.getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(death)))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void whenValidInput_ThenReturns200_UpdateDeath() throws Exception{ 
		Death death = initDeath(); 
		
		when(this.deathService.updateDeath(death)).thenReturn(death); 
		
		mockMvc.perform(put("/deaths/{deathId}", death.getId()) 
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(death)))
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
		Death death = initDeath(); 
		
		when(this.deathService.findById(death.getId())).thenReturn(death);
		
		MvcResult mvcResult = mockMvc.perform(get("/deaths/{deathId}", death.getId())
					.contentType("application/json"))
				.andExpect(status().isOk())
				.andReturn(); 
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		
		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
						objectMapper.writeValueAsString(death)
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
		Death death = initDeath(); 
		
		mockMvc.perform(delete("/deaths/{deathId}", death.getId()))
				.andExpect(status().isNoContent()); 
	}

}
