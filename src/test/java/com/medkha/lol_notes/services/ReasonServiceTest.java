package com.medkha.lol_notes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.repositories.ReasonRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class ReasonServiceTest {

	@Autowired 
	private ReasonService reasonService;
	
	@MockBean
	private ReasonRepository reasonRepositoryMock; 

	@Test
	public void shouldReturnIllegalArgumentException_When_ReasonIsNull_createReason() { 
		when(reasonRepositoryMock.save(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		
		assertThrows(IllegalArgumentException.class, ()-> {
			this.reasonService.createReason(null); 
		});
	}
	
	@Test
	public void shouldCreateReason() {
		Reason reasonToCreate = new Reason("Outnumbered"); 
		Reason resultReasonSaved = new Reason(reasonToCreate.getDescription());
		resultReasonSaved.setId((long) 1);
		
		when(reasonRepositoryMock.save(reasonToCreate)).thenReturn(resultReasonSaved); 
		assertEquals(resultReasonSaved, this.reasonService.createReason(reasonToCreate)); 
	}
}
