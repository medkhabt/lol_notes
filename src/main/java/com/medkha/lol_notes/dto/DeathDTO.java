package com.medkha.lol_notes.dto;

import java.util.Objects;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.medkha.lol_notes.entities.Constants;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;

public class DeathDTO {
    private Long id;
    private int minute;
    private Reason reason ;
    private Game game;

    public DeathDTO(){}

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

    public Reason getReason() {
        return reason;
    }

    public void setReason(Reason reason) {
        this.reason = reason;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
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
