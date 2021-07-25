package com.medkha.lol_notes.services.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;
import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.LaneDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.dto.RoleDTO;
import com.medkha.lol_notes.services.impl.filters.ReasonFilterServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
class ReasonFilterServiceTest {

    private ReasonFilterService reasonFilterService;
    private DeathFilterService deathFilterServiceMock;

    ReasonFilterServiceTest() {
        this.deathFilterServiceMock = mock(DeathFilterService.class);
        this.reasonFilterService = new ReasonFilterServiceImpl(deathFilterServiceMock);
    }
    @Test
    void shouldReturnTopReasonWhenGameHasValidDeathsWithDifferentReasons() {
        // given
        GameDTO gameToFilterBy = new GameDTO((long)1);
        ReasonDTO correctResult = new ReasonDTO((long)2);
        when(deathFilterServiceMock.getDeathsByFilter(anyList()))
                .thenReturn(listOfDeaths());
        // when
        ReasonDTO result = this.reasonFilterService.getDeathsByGameAndCalculateTopReasonByGame(gameToFilterBy);

        // then
        assertEquals(correctResult, result);
    }

    private Stream<DeathDTO> listOfDeaths(){
        DeathDTO death1 = new DeathDTO();
        death1.setId((long)1);
        death1.setMinute(1);
        death1.setGame(GameDTO.copy(listGamesWithId().get(0)));
        death1.setReason(ReasonDTO.copy(listReasonsWithId().get(1)));

        DeathDTO death2 = new DeathDTO();
        death2.setId((long)2);
        death2.setMinute(2);
        death2.setGame(GameDTO.copy(listGamesWithId().get(0)));
        death2.setReason(ReasonDTO.copy(listReasonsWithId().get(1)));

        DeathDTO death3 = new DeathDTO();
        death3.setId((long)3);
        death3.setMinute(3);
        death3.setGame(GameDTO.copy(listGamesWithId().get(0)));
        death3.setReason(ReasonDTO.copy(listReasonsWithId().get(1)));

        DeathDTO death4 = new DeathDTO();
        death4.setId((long)4);
        death4.setMinute(4);
        death4.setGame(GameDTO.copy(listGamesWithId().get(0)));
        death4.setReason(ReasonDTO.copy(listReasonsWithId().get(0)));


        return Stream.of(death1, death2, death3, death4);
    }

    private List<GameDTO> listGamesWithId(){
        GameDTO game1 = new GameDTO();
        game1.setChampionId(10);
        game1.setRoleName("SOLO");
        game1.setLaneName("MIDDLE");
        game1.setId((long) 1);

        List<GameDTO> listGamesWithId = Stream.of(game1).collect(Collectors.toList());
        return new ArrayList<>(listGamesWithId);
    }

    private List<ReasonDTO> listReasonsWithId(){
        ReasonDTO reason1 = new ReasonDTO();
        reason1.setId((long) 1);
        reason1.setDescription("sample reason 1");

        ReasonDTO reason2 = new ReasonDTO();
        reason2.setId((long) 2);
        reason2.setDescription("sample reason 2");

        List<ReasonDTO> listReasonsWithId = Stream.of(reason1, reason2).collect(Collectors.toList());
        return new ArrayList<>(listReasonsWithId);
    }

    private Map<Integer, ChampionEssentielsDto> mapOfChampionEssentielsDto() {
        Map<Integer, ChampionEssentielsDto> result = new HashMap<>();

        ChampionEssentielsDto champion1 = new ChampionEssentielsDto();
        champion1.setId(10);
        champion1.setName("Champion 1");
        result.put(champion1.getId(), champion1);

        ChampionEssentielsDto champion2 = new ChampionEssentielsDto();
        champion2.setId(11);
        champion2.setName("Champion 2");
        result.put(champion2.getId(), champion2);

        return new HashMap<>(result);
    }

    private Map<String, RoleDTO> mapOfRolesDto() {
        Map<String, RoleDTO> result = new HashMap<>();
        RoleDTO role1 = new RoleDTO("SOLO");
        result.put("SOLO", role1);
        RoleDTO role2 = new RoleDTO("DUO");
        result.put("DUO", role2);
        return new HashMap<>(result);
    }

    private Map<String, LaneDTO> mapOfLanesDto() {
        Map<String, LaneDTO> result = new HashMap<>();
        LaneDTO lane1 = new LaneDTO("MIDDLE");
        result.put("MIDDLE", lane1);
        LaneDTO lane2 = new LaneDTO("BOTTOM");
        result.put("BOTTOM", lane2);
        return new HashMap<>(result);
    }
}
