package com.medkha.lol_notes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.mapper.impl.MapperServiceImpl;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.ChampionService;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.RoleAndLaneService;
import com.medkha.lol_notes.services.impl.ChampionServiceImpl;
import com.medkha.lol_notes.services.impl.DeathServiceImpl;
import com.medkha.lol_notes.services.impl.GameServiceImpl;
import com.medkha.lol_notes.services.impl.ReasonServiceImpl;

@Configuration
public class ServiceConfiguration {

    @Bean
    public GameService gameService(GameRepository gameRepository, ChampionService championService, RoleAndLaneService roleAndLaneService) {
        return new GameServiceImpl(gameRepository, championService, roleAndLaneService);
    }

    @Bean
    public ReasonService reasonService(ReasonRepository reasonRepository) {
        return new ReasonServiceImpl(reasonRepository);
    }

    @Bean
    public DeathService deathService(DeathRepository deathRepository) {
        return new DeathServiceImpl(deathRepository);
    }


    /*
    MapperService doesn't need to be implemented here, because it doesn't require an other service in its construction.
    And there is only one impl.
     */
//    @Bean
//    public MapperService mapperService() {
//        return new MapperServiceImpl();
//    }

    @Bean
    public ChampionService championService(MapperService mapperService) {
        return new ChampionServiceImpl(mapperService);
    }
}
