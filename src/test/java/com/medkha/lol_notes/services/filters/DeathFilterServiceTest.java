package com.medkha.lol_notes.services.filters;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.entities.Champion;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.entities.Role;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.impl.filters.DeathFilterServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class DeathFilterServiceTest {

	private static DeathFilterServiceImpl deathFilterService;
	private static DeathService deathService;

	@BeforeAll
	public static void init(){
		deathService = mock(DeathService.class);
		deathFilterService = new DeathFilterServiceImpl(deathService);

	}
	

	@Test
	public void shouldFilterDeathsByReason_getDeathsByFilter() {
		Game game1 = new Game(Role.ADC, 1);
		game1.setId((long)1);

		Game game2 = new Game(Role.ADC, 1);
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

		when(deathService.findAllDeaths()).thenReturn(deaths);
		assertAll(
				() -> assertEquals(5,
						deathFilterService.getDeathsByFilter(
								Stream.of(game1.getPredicate()).collect(Collectors.toList())
						).count()),
				() -> assertEquals(4,
						deathFilterService.getDeathsByFilter(
								Stream.of(reason1.getPredicate()).collect(Collectors.toList())
						).count()),
				() -> assertEquals(3,
						deathFilterService.getDeathsByFilter(
								Stream.of(reason1.getPredicate(), game1.getPredicate()).collect(Collectors.toList())
						).count())
		);
	}

}
