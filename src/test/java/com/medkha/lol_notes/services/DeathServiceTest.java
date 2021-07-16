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

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.impl.DeathServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class DeathServiceTest {

	private DeathRepository deathRepositoryMock;
	private MapperService mapperServiceMock;
	private DeathService deathService;

	private GameDTO sampleGameDTOWithId(){
		GameDTO game = new GameDTO();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setId((long) 1);
		return game;
	}

	private Game sampleGameWithId() {
		Game game = new Game();
		game.setChampionId(10);
		game.setRoleName("SOLO");
		game.setLaneName("MIDLANE");
		game.setId((long) 1);
		return game;
	}

	private ReasonDTO sampleReasonDTOWithId(){
		ReasonDTO reason = new ReasonDTO();
		reason.setId((long) 1);
		reason.setDescription("sample reason");
		return reason;
	}

	private Reason sampleReasonWithId(){
		Reason reason = new Reason("sample reason");
		reason.setId((long) 1);
		return reason;
	}

	private DeathDTO sampleDeathDTOWithId(){
		DeathDTO death = new DeathDTO();
		death.setId((long)1);
		death.setMinute(1);
		death.setGame(GameDTO.copy(sampleGameDTOWithId()));
		death.setReason(ReasonDTO.copy(sampleReasonDTOWithId()));
		return death;
	}

	private DeathDTO sampleDeathDTOWithoutId() {
		DeathDTO death = new DeathDTO();
		death.setMinute(1);
		death.setGame(GameDTO.copy(sampleGameDTOWithId()));
		death.setReason(ReasonDTO.copy(sampleReasonDTOWithId()));
		return death;
	}

	private Death sampleDeathWithId(){
		Death death = new Death(1,sampleReasonWithId(), sampleGameWithId());
		death.setId((long)1);
		return death;
	}
	private Death sampleDeathWithouId(){
		Death death = new Death(1,sampleReasonWithId(), sampleGameWithId());
		return death;
	}


	@BeforeEach
	public  void setupMock() {
		deathRepositoryMock = mock(DeathRepository.class);
		mapperServiceMock = mock(MapperService.class);
		deathService = new DeathServiceImpl(deathRepositoryMock, mapperServiceMock);
	}
	
	@Test
	public void shouldcreateDeath() {
		when(this.mapperServiceMock.convert(sampleDeathDTOWithoutId(), Death.class)).thenReturn(sampleDeathWithouId());
		when(this.deathRepositoryMock.save(mapperServiceMock.convert(sampleDeathDTOWithoutId(), Death.class))).thenReturn(sampleDeathWithId());
		when(this.mapperServiceMock.convert(sampleDeathWithId(), DeathDTO.class)).thenReturn(sampleDeathDTOWithId());
		// when
		DeathDTO result = this.deathService.createDeath(sampleDeathDTOWithoutId());

		// then
		assertEquals(sampleDeathDTOWithId(), result);
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_DeathIsNull() {
		when(this.deathRepositoryMock.save(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		assertThrows(IllegalArgumentException.class, () -> { 
			this.deathService.createDeath(null); 
		}); 
	}
	
	@Test
	public void shouldfindDeathById() {
		when(this.deathRepositoryMock.findById(sampleDeathDTOWithId().getId())).thenReturn(Optional.of(sampleDeathWithId()));
		when(this.mapperServiceMock.convert(sampleDeathWithId(), DeathDTO.class)).thenReturn(sampleDeathDTOWithId());
		assertEquals(sampleDeathDTOWithId(), this.deathService.findById(sampleDeathDTOWithId().getId()));
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_idIsNull_findById() {
		when(this.deathRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		assertThrows(IllegalArgumentException.class, () -> {
			this.deathService.findById(null); 
		}); 
	}
	
	@Test 
	public void shouldThrowNoElementFoundException_When_idIsNotInDb_findById() {
		when(this.deathRepositoryMock.findById(sampleDeathDTOWithId().getId())).thenReturn(Optional.empty());
		assertThrows(NoElementFoundException.class, () -> {
			this.deathService.findById(sampleDeathDTOWithId().getId());
		});
	}
	
	@Test 
	public void shouldUpdateDeath() {
		// given
		Death updatedDeath = new Death(3, sampleReasonWithId(), sampleGameWithId());
		updatedDeath.setId(sampleDeathWithId().getId());
		DeathDTO updatedDeathDto = DeathDTO.copy(sampleDeathDTOWithId());
		updatedDeathDto.setMinute(3);
			// DeathService findBy
		when(this.deathRepositoryMock.findById(updatedDeathDto.getId())).thenReturn(Optional.of(sampleDeathWithId()));
		when(this.mapperServiceMock.convert(sampleDeathWithId(), DeathDTO.class)).thenReturn(sampleDeathDTOWithId());
			// DeathService updateDeath
		when(this.mapperServiceMock.convert(updatedDeathDto, Death.class)).thenReturn(updatedDeath);
		when(this.deathRepositoryMock.save(updatedDeath)).thenReturn(updatedDeath);
		when(this.mapperServiceMock.convert(updatedDeath, DeathDTO.class)).thenReturn(updatedDeathDto);

		// when
		DeathDTO result = this.deathService.updateDeath(updatedDeathDto);
		assertEquals(updatedDeathDto, result);
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_DeathIsNullOdIdIsNull_updateDeath() {
		when(this.deathRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		assertAll(
				()  -> assertThrows(IllegalArgumentException.class, () -> {
							this.deathService.updateDeath(null); 
						}), 
				() -> assertThrows(IllegalArgumentException.class, () -> {
							this.deathService.updateDeath(sampleDeathDTOWithoutId());
						})
				);
	}
	
	@Test
	public void shouldThrowNoElementFoundException_When_IdDoesntExistInDb_updateDeath() {
		when(this.deathRepositoryMock.findById(sampleDeathWithId().getId())).thenReturn(Optional.empty());

		assertThrows(NoElementFoundException.class, () -> {
			this.deathService.updateDeath(sampleDeathDTOWithId());
		}); 
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_IdIsNull_deleteDeathById() {
		when(this.deathRepositoryMock.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class);
		assertThrows(IllegalArgumentException.class, () -> {
			this.deathService.deleteDeathById(null);
		}); 
		
	}
	
	@Test
	public void shouldThrowNoElementFoundException_When_idIsNotInDb_deleteDeathById() {
		when(this.deathRepositoryMock.findById(sampleDeathDTOWithId().getId())).thenReturn(Optional.empty());
		assertThrows(NoElementFoundException.class, () -> {
			this.deathService.deleteDeathById(sampleDeathDTOWithId().getId());
		});
	}
	
	
	
	
	
	
	
	
	
	
}
