package com.medkha.lol_notes.dto;

import java.util.function.Predicate;

import com.medkha.lol_notes.dto.interfaces.DeathFilterOption;

public class ChampionEssentielsDto implements DeathFilterOption {
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
        return null;
    }
}
