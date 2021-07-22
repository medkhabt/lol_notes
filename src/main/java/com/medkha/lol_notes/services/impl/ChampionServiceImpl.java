package com.medkha.lol_notes.services.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
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

    private final MapperService mapper;

    public ChampionServiceImpl(MapperService mapper){
        this.mapper = mapper;
    }

    @PostConstruct
    public void init() {
        log.info("init: Start initialization of ChampionService...");
        getAllChampions();
        log.info("init: Got all champions successfully");
    }
    @Override
    @Cacheable("allChampions")
    public Set<ChampionEssentielsDto> getAllChampions() {
        return Champions.withRegion(Region.EUROPE_WEST).get().stream()
                .map(
                    champion -> mapper.convert(champion, ChampionEssentielsDto.class)
                ).collect(Collectors.toSet());
    }

    @Override
    public ChampionEssentielsDto getChampionByName(String name) {
        ChampionEssentielsDto championFoundByName = getAllChampions().stream()
                .filter(champion -> champion.getName().equals(name))
                .findAny()
                .orElseThrow(() -> {
                    log.error("getChampionByName: No Champion with name {} is found.", name);
                    throw new NoElementFoundException("No Champion with name " + name + " is found.");
                });

        log.info("getChampionByName: Found {} successfully", championFoundByName.toString());
        return championFoundByName;
    }

    @Override
    public ChampionEssentielsDto getChampionById(Integer id) {
        if(id == null) {
            log.error("getChampionById: id is null can't proceed.");
            throw new IllegalArgumentException("id is null can't proceed.");
        }
        ChampionEssentielsDto championFoundById = getAllChampions().stream()
                .filter(champion -> champion.getId() == id)
                .findAny()
                .orElseThrow(() -> {
                    log.error("getChampionById: No Champion with id {} is found.", id);
                    throw new NoElementFoundException("No Champion with id " + id + " is found.");
                });

        log.info("getChampionById: Found {} successfully", championFoundById.toString());
        return championFoundById;
    }
}
