package com.medkha.lol_notes.dto;

import java.util.Objects;

public class DeathDTO {
    private Long id;
    private int minute;
    private ReasonDTO reason ;
    private GameDTO game;

    public DeathDTO(){}
    public DeathDTO(String idParam) {
        this.id = Long.getLong(idParam);
    }

    public static DeathDTO copy(DeathDTO deathToCopy){
        DeathDTO newDeath = new DeathDTO();
        newDeath.setId(deathToCopy.getId());
        newDeath.setMinute(deathToCopy.getMinute());
        newDeath.setReason(deathToCopy.getReason());
        newDeath.setGame(deathToCopy.getGame());
        return newDeath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public ReasonDTO getReason() {
        return reason;
    }

    public void setReason(ReasonDTO reason) {
        this.reason = reason;
    }

    public GameDTO getGame() {
        return game;
    }

    public void setGame(GameDTO game) {
        this.game = game;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeathDTO deathDTO = (DeathDTO) o;
        return Objects.equals(id, deathDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
