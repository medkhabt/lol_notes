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
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.filters.DeathFilterService;
import com.medkha.lol_notes.services.impl.ReasonServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class ReasonServiceTest {

	private ReasonRepository reasonRepositoryMock;
	private DeathService deathServiceMock;
	private DeathFilterService deathFilterServiceMock;
	private MapperService mapperServiceMock;
	private ReasonService reasonService;

	private ReasonDTO sampleReasonDTOWithId(){
		ReasonDTO reason = new ReasonDTO();
		reason.setId((long) 1);
		reason.setDescription("sample reason");
		return reason;
	}

	private ReasonDTO sampleReasonDTOWithoutId() {
		ReasonDTO reason = new ReasonDTO();
		reason.setDescription("sample reason");
		return reason;
	}

	private Reason sampleReasonWithId(){
		Reason reason = new Reason("sample reason");
		reason.setId((long) 1);
		return reason;
	}

	private Reason sampleReasonWithoutId(){
		return new Reason("sample reason");
	}

	@BeforeEach
	public void setupMock() {
		reasonRepositoryMock = mock(ReasonRepository.class);
		mapperServiceMock = mock(MapperService.class);
		deathServiceMock = mock(DeathService.class);
		deathFilterServiceMock = mock(DeathFilterService.class);
		reasonService = new ReasonServiceImpl(reasonRepositoryMock, deathServiceMock, deathFilterServiceMock, mapperServiceMock);
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
		// given
		when(mapperServiceMock.convert(sampleReasonDTOWithoutId(), Reason.class)).thenReturn(sampleReasonWithoutId());
		when(mapperServiceMock.convert(sampleReasonWithId(), ReasonDTO.class)).thenReturn(sampleReasonDTOWithId());
		when(reasonRepositoryMock.save(mapperServiceMock.convert(sampleReasonDTOWithoutId(), Reason.class))).thenReturn(sampleReasonWithId());

		// when
		ReasonDTO result = this.reasonService.createReason(sampleReasonDTOWithoutId());

		// then
		assertEquals(sampleReasonDTOWithId(), result);
	}
	
	@Test
	public void shouldReturnIllegalArgumentException_When_ReasonIsNull_updateReason() {
		when(reasonRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		assertAll(
				() -> assertThrows(IllegalArgumentException.class, ()-> {				
					this.reasonService.updateReason(null); 
				}), 
				
				() -> assertThrows(IllegalArgumentException.class, ()-> {
					this.reasonService.updateReason(sampleReasonDTOWithoutId());
				})
		); 
	}
	
	@Test
	public void shouldReturnNoElementFoundException_When_ReasonDoesntExistInDb_updateReason() {
		when(reasonRepositoryMock.findById(sampleReasonDTOWithId().getId())).thenReturn(Optional.empty());
		assertThrows(NoElementFoundException.class, ()-> {
			this.reasonService.updateReason(sampleReasonDTOWithId());
		});
	}
	
	@Test 
	public void shouldUpdateReason() {
		// given
		ReasonDTO updatedReasonDto = ReasonDTO.copy(sampleReasonDTOWithId());
		updatedReasonDto.setDescription("updated Reason");
		Reason updatedReason = new Reason("updated Reason"); 
		updatedReason.setId(sampleReasonDTOWithId().getId());
		// reasonService.findById mocks
		when(reasonRepositoryMock.findById(updatedReasonDto.getId())).thenReturn(Optional.of(sampleReasonWithId()));
		when(mapperServiceMock.convert(sampleReasonWithId(), ReasonDTO.class)).thenReturn(sampleReasonDTOWithId());
		// reasonService.updateReason mocks
		when(mapperServiceMock.convert(updatedReason, ReasonDTO.class)).thenReturn(updatedReasonDto);
		when(mapperServiceMock.convert(updatedReasonDto, Reason.class)).thenReturn(updatedReason);
		when(reasonRepositoryMock.save(mapperServiceMock.convert(updatedReasonDto, Reason.class))).thenReturn(updatedReason);

		// when
		ReasonDTO result = this.reasonService.updateReason(updatedReasonDto);

		// then
		assertEquals(updatedReasonDto, result);
	} 	
	
	@Test
	public void shouldThrowNoElementFoundException_When_ReasonIdDoesntExistInDb_deleteReason() {
		when(reasonRepositoryMock.findById(sampleReasonDTOWithId().getId())).thenReturn(Optional.empty());
		assertThrows(NoElementFoundException.class, () -> {
			this.reasonService.deleteReason(sampleReasonDTOWithId().getId());
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
