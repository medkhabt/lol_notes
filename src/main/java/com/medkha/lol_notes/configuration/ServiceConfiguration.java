package com.medkha.lol_notes.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.medkha.lol_notes.repositories.DeathRepository;
import com.medkha.lol_notes.repositories.GameRepository;
import com.medkha.lol_notes.repositories.ReasonRepository;
import com.medkha.lol_notes.services.DeathService;
import com.medkha.lol_notes.services.GameService;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.impl.DeathServiceImpl;
import com.medkha.lol_notes.services.impl.GameServiceImpl;
import com.medkha.lol_notes.services.impl.ReasonServiceImpl;

@Configuration
public class ServiceConfiguration {

    @Bean
    public GameService gameService(GameRepository gameRepository) {
        return new GameServiceImpl(gameRepository);
    }

    @Bean
    public ReasonService reasonService(ReasonRepository reasonRepository) {
        return new ReasonServiceImpl(reasonRepository);
    }

    @Bean
    public DeathService deathService(DeathRepository deathRepository) {
        return new DeathServiceImpl(deathRepository);
    }
}
