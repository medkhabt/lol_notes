package com.medkha.lol_notes.services.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.exceptions.IncorrectReturnSizeException;
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
                .thenReturn(listOfDeaths().filter(d -> d.getGame().getId().equals((long)1)));
        // when
        ReasonDTO result = this.reasonFilterService.getDeathsByGameAndCalculateTopReasonByGame(gameToFilterBy);

        // then
        assertEquals(correctResult, result);
    }

    @Test
    void shouldThrowExceptionWhenThereAreMoreThanOneTopGame() {
        GameDTO gameToFilterBy = new GameDTO((long)2);
        when(deathFilterServiceMock.getDeathsByFilter(anyList()))
                .thenReturn(listOfDeaths().filter(d -> d.getGame().getId().equals((long)2)));
        assertThrows(
                IncorrectReturnSizeException.class,
                () -> this.reasonFilterService.getDeathsByGameAndCalculateTopReasonByGame(gameToFilterBy)
        );

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

        DeathDTO death5 = new DeathDTO();
        death5.setId((long)5);
        death5.setMinute(5);
        death5.setGame(GameDTO.copy(listGamesWithId().get(1)));
        death5.setReason(ReasonDTO.copy(listReasonsWithId().get(0)));

        DeathDTO death6 = new DeathDTO();
        death6.setId((long)6);
        death6.setMinute(6);
        death6.setGame(GameDTO.copy(listGamesWithId().get(1)));
        death6.setReason(ReasonDTO.copy(listReasonsWithId().get(1)));


        return Stream.of(death1, death2, death3, death4, death5, death6);
    }

    private List<GameDTO> listGamesWithId(){
        GameDTO game1 = new GameDTO();
        game1.setChampionId(10);
        game1.setRoleName("SOLO");
        game1.setLaneName("MIDDLE");
        game1.setId((long) 1);

        GameDTO game2 = new GameDTO();
        game2.setChampionId(13);
        game2.setRoleName("SOLO");
        game2.setLaneName("MIDDLE");
        game2.setId((long) 2);

        List<GameDTO> listGamesWithId = Stream.of(game1, game2).collect(Collectors.toList());
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

}
