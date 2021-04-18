package com.medkha.lol_notes.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;
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
		Game game = new Game(Role.ADC, Champion.JINX); 
		
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
}
