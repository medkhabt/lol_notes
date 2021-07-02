package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;

public interface ChampionService {
    public Set<ChampionEssentielsDto> getAllChampions();
    public ChampionEssentielsDto getChampionByName(String name);
    public ChampionEssentielsDto getChampionById(Integer id);
}
