package com.medkha.lol_notes.controllers.filter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.medkha.lol_notes.controllers.filters.DeathFilterController;
import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.services.filters.DeathFilterService;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DeathFilterController.class)
public class DeathFilterControllerTest {
	
	@MockBean 
	private DeathFilterService deathFilterService;
	
	@Autowired 
	private MockMvc mockMvc;

	// TODO: Need to Test the DeathFilterController

//	private Set<DeathDTO> listOfDeaths(){
//		DeathDTO death1 = new DeathDTO();
//		death1.setId((long)1);
//		death1.setMinute(1);
//		death1.setGame(GameDTO.copy(listGamesWithId().get(0)));
//		death1.setReason(ReasonDTO.copy(listReasonsWithId().get(0)));
//
//		DeathDTO death2 = new DeathDTO();
//		death2.setId((long)2);
//		death2.setMinute(2);
//		death2.setGame(GameDTO.copy(listGamesWithId().get(0)));
//		death2.setReason(ReasonDTO.copy(listReasonsWithId().get(1)));
//
//		DeathDTO death3 = new DeathDTO();
//		death3.setId((long)3);
//		death3.setMinute(3);
//		death3.setGame(GameDTO.copy(listGamesWithId().get(1)));
//		death3.setReason(ReasonDTO.copy(listReasonsWithId().get(1)));
//
//		DeathDTO death4 = new DeathDTO();
//		death4.setId((long)4);
//		death4.setMinute(4);
//		death4.setGame(GameDTO.copy(listGamesWithId().get(1)));
//		death4.setReason(ReasonDTO.copy(listReasonsWithId().get(0)));
//
//		Set<DeathDTO> listOfDeaths = Stream.of(death1, death2, death3, death4).collect(Collectors.toSet());
//		return new HashSet<>(listOfDeaths);
//	}
//
//	private List<GameDTO> listGamesWithId(){
//		GameDTO game1 = new GameDTO();
//		game1.setChampionId(10);
//		game1.setRoleName("SOLO");
//		game1.setLaneName("MIDLANE");
//		game1.setId((long) 1);
//
//		GameDTO game2 = new GameDTO();
//		game2.setChampionId(11);
//		game2.setRoleName("SOLO");
//		game2.setLaneName("TOPLANE");
//		game2.setId((long) 2);
//
//		List<GameDTO> listGamesWithId = Stream.of(game1, game2).collect(Collectors.toList());
//		return new ArrayList<>(listGamesWithId);
//	}
//
//	private List<ReasonDTO> listReasonsWithId(){
//		ReasonDTO reason1 = new ReasonDTO();
//		reason1.setId((long) 1);
//		reason1.setDescription("sample reason 1");
//
//		ReasonDTO reason2 = new ReasonDTO();
//		reason2.setId((long) 2);
//		reason2.setDescription("sample reason 2");
//
//		List<ReasonDTO> listReasonsWithId = Stream.of(reason1, reason2).collect(Collectors.toList());
//		return new ArrayList<>(listReasonsWithId);
//	}
}
