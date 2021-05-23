package com.medkha.lol_notes.services.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.impl.filters.DeathFilterServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class DeathFilterServiceTest {

	@InjectMocks
	private DeathFilterServiceImpl deathFilterService; 
	
	@MockBean
	private DeathRepository deathRepository ;
	
	@MockBean 
	private GameService gameService; 
	
	@Test
	public void shouldFilterDeathsByGame() { 
		Game game1 = new Game(Role.ADC, Champion.JINX); 
		game1.setId((long)1); 
		
		Game game2 = new Game(Role.ADC, Champion.KAISA); 
		game2.setId((long)2);
		
		Reason reason = new Reason("out numbered"); 
		reason.setId((long) 1);
		
		Death death1 = new Death(10, reason, game1); 
		death1.setId((long) 1);
		
		Death death2 = new Death(20, reason, game1); 
		death2.setId((long) 2);
		
		Death death3 = new Death(30, reason, game1); 
		death3.setId((long) 3);
		
		Death death4 = new Death(11, reason, game2); 
		death4.setId((long) 4);
		
		Death death5 = new Death(25, reason, game2); 
		death5.setId((long) 5);
		
		Death death6 = new Death(29, reason, game2); 
		
		Set<Death> deaths = Stream.of(
				
				death1, 
				death2, 
				death3,
				death4,
				death5,
				death6
				
				
		).collect(Collectors.toSet());
		
		
		when(this.deathRepository.findByGame(game1)).thenReturn(
				deaths.stream().filter(death -> death.getGame().equals(game1)).collect(Collectors.toSet())
				); 
		
		assertEquals(3, this.deathFilterService.getDeathsByGame(game1).size()); 
		
		
	}
	
	@Test 
	public void shouldThrowIllegalArgumentException_when_Game_isNull() { 
		
		Game nullGame = null ;
		
		
		assertThrows(IllegalArgumentException.class, () -> {
			deathFilterService.getDeathsByGame(nullGame); 
		}); 
		
	}
	
	@Test
	public void shouldThrowNoElementFoundException_when_NoDeathIsFoundForGame() { 
		
		Game game = new Game(Role.ADC, Champion.JINX); 
		
		when(deathRepository.findByGame(game)).thenReturn(Collections.emptySet()); 
		
		assertThrows(NoElementFoundException.class, () -> { 
			deathFilterService.getDeathsByGame(game); 
		}); 
		
	}
}
