package com.medkha.lol_notes.mapper;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;
import com.medkha.lol_notes.mapper.impl.MapperServiceImpl;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.services.impl.DeathServiceImpl;
import com.merakianalytics.orianna.types.core.staticdata.Champion;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
public class MapperServiceTest {
    @Autowired
    private MapperService mapper;


    @Configuration
    static class ContextConfiguration {
        @Bean
        @Primary
        public MapperService mapperService() {
            return new MapperServiceImpl();
        }
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

}
