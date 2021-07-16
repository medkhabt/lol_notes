package com.medkha.lol_notes.dto;

import java.util.function.Predicate;

import com.medkha.lol_notes.dto.interfaces.DeathFilterOption;

public class LaneDTO implements DeathFilterOption {
    private String laneName;

    public LaneDTO(String laneName) {
        this.laneName = laneName;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    @Override
    public Predicate<DeathDTO> getPredicate() {
        return null;
    }
}
