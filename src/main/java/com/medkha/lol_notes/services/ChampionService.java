package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.entities.ChampionV2;

public interface ChampionService {
    public Set<ChampionV2> getAllChampions();
    public ChampionV2 getChampionByName(String name);
}
