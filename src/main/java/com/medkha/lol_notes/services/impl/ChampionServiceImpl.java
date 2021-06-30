package com.medkha.lol_notes.services.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.entities.ChampionV2;
import com.medkha.lol_notes.services.ChampionService;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
@Service
public class ChampionServiceImpl implements ChampionService {
    private static Logger log = LoggerFactory.getLogger(ChampionServiceImpl.class);
    @Override
    public Set<ChampionV2> getAllChampions() {
        return Champions.withRegion(Region.EUROPE_WEST).get().stream()
                .map(champion -> new ChampionV2((long) champion.getId(), champion.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public ChampionV2 getChampionByName(String name) {
        Champion champion = Champion.named(name).withRegion(Region.EUROPE_WEST).get();
        return  new ChampionV2((long) champion.getId(), champion.getName());
    }
}
