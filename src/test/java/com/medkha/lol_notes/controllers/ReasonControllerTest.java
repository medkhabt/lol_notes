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
import com.medkha.lol_notes.dto.ReasonDTO;
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
		mockMvc.perform(post("/reasons")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(sampleReasonDTOWithoutId())))
			.andExpect(status().isCreated());
	}

	@Test
	public void whenNullReasonDescription_thenReturns400_CreateReason() throws Exception {
		when(this.reasonService.createReason(sampleReasonDTOWithoutDescriptionAndId())).thenThrow(IllegalArgumentException.class);
		mockMvc.perform(post("/reasons")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(sampleReasonDTOWithoutDescriptionAndId())))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void whenNullReason_thenReturn400_CreateReason() throws Exception {
		mockMvc.perform(post("/reasons")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(null)))
			.andExpect(status().isBadRequest());
	}


	@Test
	public void whenReasonIdIsntInDb_theReturn403_UpdateReason() throws Exception{
		when(this.reasonService.updateReason(sampleReasonDTOWithId())).thenThrow(NoElementFoundException.class);
		mockMvc.perform(put("/reasons/{reasonId}", sampleReasonDTOWithId().getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(sampleReasonDTOWithId())))
				.andExpect(status().isForbidden());
	}

	@Test
	public void whenReasonDescriptionIsNull_thenReturn400_UpdateReason() throws Exception {
		ReasonDTO reasonWithoutDescription = ReasonDTO.copy(sampleReasonDTOWithoutDescriptionAndId());
		reasonWithoutDescription.setId((long)1);
		when(this.reasonService.updateReason(reasonWithoutDescription)).thenThrow(IllegalArgumentException.class);
		mockMvc.perform(put("/reasons/{reasonId}", reasonWithoutDescription.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(reasonWithoutDescription)))
			.andExpect(status().isBadRequest());

		ReasonDTO reasonWithBlankDescription = ReasonDTO.copy(sampleReasonDTOWithId());
		when(this.reasonService.updateReason(reasonWithBlankDescription)).thenThrow(IllegalArgumentException.class);
		reasonWithBlankDescription.setDescription("");
		mockMvc.perform(put("/reasons/{reasonId}", reasonWithBlankDescription.getId())
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(reasonWithBlankDescription)))
			.andExpect(status().isBadRequest());
	}

	@Test
	public void whenValidInput_ThenReturns200_UpdateReason() throws Exception{
		when(this.reasonService.updateReason(sampleReasonDTOWithId())).thenReturn(sampleReasonDTOWithId());
		mockMvc.perform(put("/reasons/{reasonId}", sampleReasonDTOWithId().getId())
					.contentType("application/json")
					.content(objectMapper.writeValueAsString(sampleReasonDTOWithId())))
				.andExpect(status().isOk());
	}

	@Test
	public void whenValidInput_ThenReturnsReason_FindById() throws Exception {
		when(this.reasonService.findById(sampleReasonDTOWithId().getId())).thenReturn(sampleReasonDTOWithId());
		MvcResult mvcResult = mockMvc.perform(get("/reasons/{reasonId}", sampleReasonDTOWithId().getId()))
									.andReturn();
		String actualResponseBody = mvcResult.getResponse().getContentAsString();
		assertThat(actualResponseBody).isEqualToIgnoringWhitespace(
						objectMapper.writeValueAsString(sampleReasonDTOWithId())
				);
	}

	@Test
	public void whenReasonIdIsntInDb_thenReturn403_FindById() throws Exception {
		Long id = (long) 1;
		when(this.reasonService.findById(id)).thenThrow(NoElementFoundException.class);
		mockMvc.perform(get("/reasons/{reasonId}", id))
					.andExpect(status().isForbidden());
	}

	@Test
	public void whenValidInput_ThenReturn204_deleteReason() throws Exception {
		Long id = (long) 1 ;
		mockMvc.perform(delete("/reasons/{reasonsId}", id))
					.andExpect(status().isNoContent());
	}

	@Test
	public void whenReasonIdIsntInDb_thenReturn403_deleteReason() throws Exception {
		Long id = (long) 1;
		doThrow(NoElementFoundException.class).when(this.reasonService).deleteReason(id);
		mockMvc.perform(delete("/reasons/{reasonsId}", id))
					.andExpect(status().isForbidden());

	}
	private ReasonDTO sampleReasonDTOWithoutDescriptionAndId(){
		ReasonDTO reasonWithoutDescription = ReasonDTO.copy(sampleReasonDTOWithoutId());
		reasonWithoutDescription.setDescription(null);
		return reasonWithoutDescription;
	}
	private ReasonDTO sampleReasonDTOWithoutId(){
		ReasonDTO reason = new ReasonDTO();
		reason.setDescription("sample reason");
		return reason;
	}
	private ReasonDTO sampleReasonDTOWithId(){
		ReasonDTO reason = new ReasonDTO();
		reason.setId((long) 1);
		reason.setDescription("sample reason");
		return reason;
	}
	
}
