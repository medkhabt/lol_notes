package com.medkha.lol_notes.services;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
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

	private ReasonRepository reasonRepositoryMock;
	private ReasonService reasonService;

	@BeforeEach
	public void setupMock() {
		reasonRepositoryMock = mock(ReasonRepository.class);
		reasonService = new ReasonServiceImpl(reasonRepositoryMock);

	}

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
		when(reasonRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		
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
		
		when(reasonRepositoryMock.findById(reasonNoInDb.getId())).thenReturn(Optional.empty());
		
		assertThrows(NoElementFoundException.class, ()-> {
			this.reasonService.updateReason(reasonNoInDb);
		});
		
	}
	
	@Test 
	public void shouldUpdateReason() {
		Reason existingReason = new Reason("existing Resason") ; 
		existingReason.setId((long)1); 
		
		Reason updatedReason = new Reason("updated Reason"); 
		updatedReason.setId(existingReason.getId());
		
		when(reasonRepositoryMock.findById(updatedReason.getId())).thenReturn(Optional.of(existingReason)); 
		when(reasonRepositoryMock.save(updatedReason)).thenReturn(updatedReason); 
		assertEquals(updatedReason, this.reasonService.updateReason(updatedReason)); 
	} 	
	
	@Test
	public void shouldThrowNoElementFoundException_When_ReasonIdDoesntExistInDb_deleteReason() {
		Reason reasonToDelete = new Reason("reason to delete"); 
		reasonToDelete.setId((long)1);
		
		when(reasonRepositoryMock.findById(reasonToDelete.getId())).thenReturn(Optional.empty());
		
		assertThrows(NoElementFoundException.class, () -> {
			this.reasonService.deleteReason(reasonToDelete.getId());
		}); 
	}
	
	@Test
	public void shouldThrowIllegalArgumentUsage_When_ReasonIdIsNull_deleteReason() {
		
		
		when(reasonRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		assertThrows(IllegalArgumentException.class, () -> {
			this.reasonService.deleteReason(null);
		}); 
	}
}
