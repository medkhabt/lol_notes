package com.medkha.lol_notes.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;
import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;
import com.medkha.lol_notes.mapper.impl.MapperServiceImpl;
import com.merakianalytics.orianna.types.core.staticdata.Champion;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class MapperServiceTest {

    private final MapperService mapper;

    public MapperServiceTest() {
        this.mapper = new MapperServiceImpl();
    }

    @Test void checkChampionEssentielMapping() {
        Champion champion = mock(Champion.class);
        when(champion.getId()).thenReturn(1);
        when(champion.getName()).thenReturn("test");

        ChampionEssentielsDto championEssentielsDto = mapper.convert(champion, ChampionEssentielsDto.class);

        assertAll(
                () -> assertEquals(champion.getId(), championEssentielsDto.getId()),
                () -> assertEquals(champion.getName(), championEssentielsDto.getName())
        );
    }

    @Test
    void checkMappingOfDeathDAO() {
        assertAll(
                () -> assertEquals(sampleDeathWithId(), this.mapper.convert(sampleDeathDTOWithId(), Death.class)),
                () -> assertEquals(sampleDeathDTOWithId(), this.mapper.convert(sampleDeathDTOWithId(), DeathDTO.class))
        );
    }

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
    private Death sampleDeathWithId(){
        Death death = new Death(1,sampleReasonWithId(), sampleGameWithId());
        death.setId((long)1);
        return death;
    }

}
