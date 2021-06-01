package com.medkha.lol_notes.services.filters;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
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
import com.medkha.lol_notes.services.ReasonService;
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

	@MockBean
	private ReasonService reasonService;


	

//	@Test
//	public void shouldRetValidRet_When_DeathReasonEqualOrNotFilterReason_getDeathFilterByReasonPredicate(){
//		Reason filterReasonMock = mock(Reason.class);
//		when(filterReasonMock.getId()).thenReturn((long) 1);
//
//		Reason deathReasonMock = mock(Reason.class);
//		when(deathReasonMock.getId()).thenReturn((long) 2);
//
//		Death deathToFilterWrongReasonMock = mock(Death.class);
//		when(deathToFilterWrongReasonMock.getId()).thenReturn((long) 1);
//		when(deathToFilterWrongReasonMock.getReason()).thenReturn(deathReasonMock);
//
//		Death deathToFilterRightReasonMock = mock(Death.class);
//		when(deathToFilterRightReasonMock.getId()).thenReturn((long) 1);
//		when(deathToFilterRightReasonMock.getReason()).thenReturn(filterReasonMock);
//		assertAll(
//				() -> assertFalse(deathFilterService.getDeathFilterByReasonPredicate(filterReasonMock).test(deathToFilterWrongReasonMock)),
//				() -> assertTrue(deathFilterService.getDeathFilterByReasonPredicate(filterReasonMock).test(deathToFilterRightReasonMock))
//		);
//
//
//	}

//	@Test
//	public void shouldThrowIllegalArgument_When_ArgumentIsNull() {
//		Reason filterReason = null;
//		deathFilterService.getDeathFilterByReasonPredicate(filterReason);
//	}

	@Test
	public void shouldFilterDeathsByReason_getDeathsByFilter() {
		Game game1 = new Game(Role.ADC, Champion.JINX);
		game1.setId((long)1);

		Game game2 = new Game(Role.ADC, Champion.KAISA);
		game2.setId((long)2);

		Reason reason1 = new Reason("out numbered");
		reason1.setId((long) 1);

		Reason reason2 = new Reason("out staying");
		reason2.setId((long) 2 );

		Death death1 = new Death(10, reason1, game1);
		death1.setId((long) 1);

		Death death2 = new Death(20, reason1, game1);
		death2.setId((long) 2);

		Death death3 = new Death(30, reason1, game1);
		death3.setId((long) 3);

		Death death4 = new Death(11, reason1, game2);
		death4.setId((long) 4);

		Death death5 = new Death(25, reason2, game1);
		death5.setId((long) 5);

		Death death6 = new Death(29, reason2, game1);

		Set<Death> deaths = Stream.of(
				death1,
				death2,
				death3,
				death4,
				death5,
				death6
		).collect(Collectors.toSet());


		assertEquals(4,
				this.deathFilterService.getDeathsByFilter(deaths.stream(), reason1).count());
	}


	@Test
	public void shouldFilterDeathsByGame_getDeathsByFilter() {
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

		assertEquals(3, deathFilterService.getDeathsByFilter(deaths.stream(), game1).count());
	}

}
