package com.medkha.lol_notes.dto;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestParam;

public class FilterDeathRequest {

    private Long gameId;
    private Long reasonId;
    private Integer championId;
    private String roleString;
    private String laneString;

    public Long getGameId() {
        return gameId;
    }

    public void setGameId(Long gameId) {
        this.gameId = gameId;
    }

    public Long getReasonId() {
        return reasonId;
    }

    public void setReasonId(Long reasonId) {
        this.reasonId = reasonId;
    }

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    public String getLaneString() {
        return laneString;
    }

    public void setLaneString(String laneString) {
        this.laneString = laneString;
    }
}


//    @RequestParam
//    Optional<Long> gameId,
//    @RequestParam Optional<Long> reasonId,
//    @RequestParam Optional<Integer> championId,
//    @RequestParam(name = "role") Optional<String> roleString,
//    @RequestParam(name = "lane") Optional<String> laneString