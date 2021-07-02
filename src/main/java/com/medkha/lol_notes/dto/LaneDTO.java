package com.medkha.lol_notes.dto;

public class LaneDTO {
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
}
