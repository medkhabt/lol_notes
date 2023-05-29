package com.medkha.lol_notes.configuration;

import com.medkha.lol_notes.services.*;
import com.medkha.lol_notes.services.impl.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.filters.DeathFilterService;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableAsync
public class ServiceConfiguration {

    @Bean
    public GameService gameService(
            GameRepository gameRepository,
            ChampionService championService,
            RoleAndLaneService roleAndLaneService,
            QueueService queueService,
            DeathService deathService,
            DeathFilterService deathFilterService,
            MapperService mapperService) {
        return new GameServiceImpl(gameRepository, championService, roleAndLaneService, queueService,  deathService, deathFilterService, mapperService);
    }

    @Bean
    public ReasonService reasonService(ReasonRepository reasonRepository, DeathService deathService, DeathFilterService deathFilterService, MapperService mapperService) {
        return new ReasonServiceImpl(reasonRepository, deathService, deathFilterService, mapperService);
    }

    @Bean
    public DeathService deathService(DeathRepository deathRepository, MapperService mapperService) {
        return new DeathServiceImpl(deathRepository, mapperService);
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

    @Bean
    public RiotLookUpService riotLookUpService(RestTemplate restTemplate, @Lazy LiveGameService liveGameService) {
        return new RiotLookUpServiceImpl(restTemplate, liveGameService);
    }
    @Bean
    public LiveGameService liveGameService(
            RiotLookUpService riotLookUpService,
            GameService gameService,
            ChampionService championService,
            QueueService queueService
    ) {
        return new LiveGameServiceImpl( riotLookUpService,
                 gameService,
                 championService,
                 queueService);
    }
}
