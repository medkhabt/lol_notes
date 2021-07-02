package com.medkha.lol_notes.entities;

public class ChampionV2 {
    private Long id;
    private String name;

    public ChampionV2(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {

        return id;
    }
}
