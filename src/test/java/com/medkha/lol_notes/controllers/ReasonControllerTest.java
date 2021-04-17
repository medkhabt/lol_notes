package com.medkha.lol_notes.controllers;

import static org.mockito.Mockito.when;
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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.services.ReasonService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ReasonController.class)

public class ReasonControllerTest {
	
	@MockBean
	ReasonService reasonService;
	
	@Autowired 
	private ObjectMapper objectMapper; 
	
	@Autowired 
	MockMvc mockMvc;
	
	@Test 
	public void whenValidInput_ThenReturns201_CreateReason() throws Exception { 
		Reason reason = new Reason("reason"); 
		
		mockMvc.perform(post("/reasons")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(reason)))
			.andExpect(status().isCreated()); 
	}
	
	@Test
	public void whenNullReasonDescription_thenReturns400_CreateReason() throws Exception { 
		Reason reason = new Reason(null); 
		
		mockMvc.perform(post("/reasons")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(reason)))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void whenNullReason_thenReturn400_CreateReason() throws Exception { 
		Reason reason = null ;
		
		mockMvc.perform(post("/reasons")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(reason)))
			.andExpect(status().isBadRequest());
	}
	
	
	@Test
	public void whenReasonIdIsntInDb_theReturn403_UpdateReason() throws Exception{
		Reason reason = new Reason("reason not in db"); 
		reason.setId((long)1);
		
		when(this.reasonService.updateReason(reason)).thenThrow(NoElementFoundException.class);
		
		mockMvc.perform(put("/reasons/{reasonId}", reason.getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(reason)))
				.andExpect(status().isForbidden());
	}
	
	@Test
	public void whenReasonDescriptionIsNull_thenReturn400_UpdateReason() throws Exception { 
	
		Reason reasonNull = new Reason(null); 
		reasonNull.setId((long)1);
		
		Reason reasonBlank = new Reason(""); 
		reasonBlank.setId((long) 2 );
		
		
		mockMvc.perform(put("/reasons/{reasonId}", reasonNull.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(reasonNull)))
			.andExpect(status().isBadRequest());
		
		mockMvc.perform(put("/reasons/{reasonId}", reasonBlank.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(reasonBlank)))
			.andExpect(status().isBadRequest());
	}
	
	@Test
	public void whenValidInput_ThenReturns200_UpdateReason() throws Exception{ 
		Reason reason = new Reason("updated Reason"); 
		reason.setId((long)1);
		
		when(this.reasonService.updateReason(reason)).thenReturn(reason); 
		
		mockMvc.perform(put("/reasons/{reasonId}", reason.getId()) 
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(reason)))
				.andExpect(status().isOk());
	}
	
	
}
