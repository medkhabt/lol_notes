package com.medkha.lol_notes.services.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

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
	
		Set<Death> deaths = Stream.of(
				
				new Death(10, reason, game1), 
				new Death(20, reason, game1), 
				new Death(30, reason, game1),
				new Death(11, reason, game2),
				new Death(25, reason, game2),
				new Death(29, reason, game2)
				
		).collect(Collectors.toSet());
		
		when(this.gameService.findById(game1.getId())).thenReturn(game1); 
		
		when(this.deathRepository.findByGame(game1)).thenReturn(
				deaths.stream().filter(death -> death.getGame().getId().equals(game1.getId())).collect(Collectors.toSet())
				); 
		
		assertEquals(3, this.deathFilterService.getDeathsByGame(game1.getId()).size()); 
		
		
	}
}
