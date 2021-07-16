package com.medkha.lol_notes.dto;

import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medkha.lol_notes.dto.interfaces.DeathFilterOption;

public class ChampionEssentielsDto implements DeathFilterOption {
    private static Logger log = LoggerFactory.getLogger(ChampionEssentielsDto.class);
    private int id;
    private String name;

    public ChampionEssentielsDto() {

    }
    public ChampionEssentielsDto(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "ChampionEssentielsDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public Predicate<DeathDTO> getPredicate() {
        return (DeathDTO death) -> {
            log.info("getDeathFilterByChampionPredicate: Filter by Champion with id: {}", this.getId());
            Boolean result = death.getGame().getChampionId().equals(this.getId());
            log.info("Death with id: {} has Champion with id: {} equals Filter Champion with id:{} ? {} ",
                death.getId(), death.getGame().getId(), this.getId(), result);
            return result;
        };
    }
//      return (DeathDTO death) -> {
//        log.info("getDeathFilterByGamePredicate: Filter by Game with id: {}", this.getId());
//        Boolean result = death.getGame().getId().equals(this.getId());
//        log.info("Death with id: {} has Game with id: {} equals Filter Game with id:{} ? {} ",
//                death.getId(), death.getGame().getId(), this.getId(), result);
//        return result;
//    };
}
