package com.medkha.lol_notes.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.impl.ReasonServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class ReasonServiceTest {

	
	
	@MockBean
	private ReasonRepository reasonRepositoryMock; 
	
	@InjectMocks 
	private ReasonServiceImpl reasonService;

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
	
	@Test
	public void shouldReturnIllegalArgumentException_When_ReasonIsNull_updateReason() { 
		Reason nullIdReason = new Reason("null id"); 
		
		when(reasonRepositoryMock.save(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		when(reasonRepositoryMock.findById(null)).thenThrow(NullPointerException.class);
		
		assertAll(
				() -> assertThrows(IllegalArgumentException.class, ()-> {				
					this.reasonService.updateReason(null); 
				}), 
				
				() -> assertThrows(IllegalArgumentException.class, ()-> {
					this.reasonService.updateReason(nullIdReason); 
				})
		); 
	}
	
	@Test
	public void shouldReturnNoElementFoundException_When_ReasonDoesntExistInDb_updateReason() { 
		Reason reasonNoInDb = new Reason("Reason not in db"); 
		reasonNoInDb.setId((long)1);
		
		when(reasonRepositoryMock.save(reasonNoInDb)).thenReturn(reasonNoInDb); 
		when(reasonRepositoryMock.findById(reasonNoInDb.getId())).thenReturn(Optional.empty()); 
		
		assertThrows(NoElementFoundException.class, ()-> {
			this.reasonService.updateReason(reasonNoInDb);
		});
		
		
	}
}
