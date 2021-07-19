package com.medkha.lol_notes.dto.factories;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.DeathFilterOption;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.factories.impl.DefaultDeathFilterFactory;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.mapper.impl.MapperServiceImpl;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class DeathFilterFactoryTest {

    private final MapperService mapperService ;
    private final DeathFilterOptionFactory deathFilterOptionFactory;

    // TODO: Use dependency injection here.
    public DeathFilterFactoryTest(){
        mapperService = new MapperServiceImpl();
        deathFilterOptionFactory = new DefaultDeathFilterFactory(mapperService);
    }

    @Test
    public void createDeathOptionFactoryByParamAndValue() {
        // given
        DeathDTO deathInGame10 = new DeathDTO();
        deathInGame10.setGame(new GameDTO("10"));
        DeathFilterOption game = deathFilterOptionFactory.createDeathFilterOptionByParamAndItsValue("game", "10");
        assertAll(
                () -> assertEquals("gamedto", game.getClass().getSimpleName().toLowerCase()),
                () -> assertEquals(10, ((GameDTO) game).getId()),
                () -> assertTrue(game.getPredicate().test(deathInGame10))
        );
    }
}
