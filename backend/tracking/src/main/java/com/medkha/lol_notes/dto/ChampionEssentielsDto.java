package com.medkha.lol_notes.dto;

import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ChampionEssentielsDto implements DeathFilterOption {
    private static Logger log = LoggerFactory.getLogger(ChampionEssentielsDto.class);
    private int id;
    private String name;
    private String imageUrl;

    public ChampionEssentielsDto() {
    }

    public ChampionEssentielsDto(String paramId) {
        this.id = Integer.parseInt(paramId);
    }

    public ChampionEssentielsDto(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public static ChampionEssentielsDto proxy(int id) {
        ChampionEssentielsDto champion = new ChampionEssentielsDto();
        champion.setId(id);
        return champion;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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
}
