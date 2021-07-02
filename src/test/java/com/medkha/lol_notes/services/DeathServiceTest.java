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
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.impl.DeathServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class DeathServiceTest {

	@MockBean 
	private DeathRepository deathRepository;
	
	@InjectMocks
	private DeathServiceImpl deathService; 
	
	@Test
	public void shouldcreateDeath() { 
		Reason reason = new Reason("ganked"); 
		Game game = new Game(Role.ADC, 1);
		
		Death expectedResultdeath = new Death(10, reason, game); 
		
		when(this.deathRepository.save(expectedResultdeath)).thenReturn(expectedResultdeath); 
		
		assertEquals(expectedResultdeath, this.deathService.createDeath(expectedResultdeath)); 
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_DeathIsNull() { 
		
		when(this.deathRepository.save(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		
		assertThrows(IllegalArgumentException.class, () -> { 
			this.deathService.createDeath(null); 
		}); 
	}
	
	@Test
	public void shouldfindDeathById() { 
	
		Reason reason = new Reason("ganked"); 
		Game game = new Game(Role.ADC, 1);
		
		Death death = new Death(10, reason, game);
		
		when(this.deathRepository.findById(death.getId())).thenReturn(Optional.of(death)); 
		
		assertEquals(death, this.deathService.findById(death.getId())); 
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_idIsNull_findById() { 
		
		when(this.deathRepository.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		assertThrows(IllegalArgumentException.class, () -> {
			this.deathService.findById(null); 
		}); 
	}
	
	@Test 
	public void shouldThrowNoElementFoundException_When_idIsNotInDb_findById() { 
		
		Long id = (long)1; 
		
		when(this.deathRepository.findById(id)).thenReturn(Optional.empty()); 
		assertThrows(NoElementFoundException.class, () -> {
			this.deathService.findById(id); 
		});
	}
	
	@Test 
	public void shouldUpdateDeath() { 
		Reason reason = new Reason("ganked"); 
		Game game = new Game(Role.ADC, 1);
		
		Death death = new Death(10, reason, game);
		
		Death updatedDeath = new Death(13, reason, game); 
		
		when(this.deathRepository.findById(updatedDeath.getId())).thenReturn(Optional.of(death)); 
		when(this.deathRepository.save(updatedDeath)).thenReturn(updatedDeath); 
		
		assertEquals(updatedDeath, this.deathService.updateDeath(updatedDeath)); 
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_DeathIsNullOdIdIsNull_updateDeath() { 
		Reason reason = new Reason("ganked"); 
		Game game = new Game(Role.ADC, 1);
		
		Death deathWithNullId = new Death(10, reason, game);
		deathWithNullId.setId(null);
		
		when(this.deathRepository.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		
		assertAll(
				()  -> assertThrows(IllegalArgumentException.class, () -> {
							this.deathService.updateDeath(null); 
						}), 
				() -> assertThrows(IllegalArgumentException.class, () -> {
							this.deathService.updateDeath(deathWithNullId);
						})
				); 
		
	}
	
	@Test
	public void shouldThrowNoElementFoundException_When_IdDoesntExistInDb_updateDeath() { 
		Reason reason = new Reason("ganked"); 
		Game game = new Game(Role.ADC, 1);
		
		Death death = new Death(10, reason, game);
		
		when(this.deathRepository.findById(death.getId())).thenReturn(Optional.empty()); 
		
		
		assertThrows(NoElementFoundException.class, () -> {
			this.deathService.updateDeath(death); 
		}); 
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_When_IdIsNull_deleteDeathById() { 
		
		when(this.deathRepository.findById(null)).thenThrow(InvalidDataAccessApiUsageException.class); 
		assertThrows(IllegalArgumentException.class, () -> {
			this.deathService.deleteDeathById(null);
		}); 
		
	}
	
	@Test
	public void shouldThrowNoElementFoundException_When_idIsNotInDb_deleteDeathById() {
		Long id = (long)1 ; 
		
		when(this.deathRepository.findById(id)).thenReturn(Optional.empty()); 
		assertThrows(NoElementFoundException.class, () -> {
			this.deathService.deleteDeathById(id); 
		});
	}
	
	
	
	
	
	
	
	
	
	
}
