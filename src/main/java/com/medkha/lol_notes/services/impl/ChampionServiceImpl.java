package com.medkha.lol_notes.services.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.ChampionEssentielsDto;
import com.medkha.lol_notes.entities.ChampionV2;
import com.medkha.lol_notes.exceptions.NoElementFoundException;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.services.ChampionService;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
@Service
public class ChampionServiceImpl implements ChampionService {
    private static Logger log = LoggerFactory.getLogger(ChampionServiceImpl.class);

    @Autowired
    private MapperService mapper;


    @Override
    public Set<ChampionEssentielsDto> getAllChampions() {
        return Champions.withRegion(Region.EUROPE_WEST).get().stream()
                .map(
                    champion -> mapper.convert(champion, ChampionEssentielsDto.class)
                ).collect(Collectors.toSet());
    }

    @Override
    public ChampionEssentielsDto getChampionByName(String name) {
        Champion champion = Champion.named(name).withRegion(Region.EUROPE_WEST).get();
        ChampionEssentielsDto result =  mapper.convert(champion, ChampionEssentielsDto.class);
        if(result.getId() == 0) {
            throw new NoElementFoundException("No Champion Found with name: " + name);
        }
        return result;
    }
}
