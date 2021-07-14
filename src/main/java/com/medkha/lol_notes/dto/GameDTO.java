package com.medkha.lol_notes.dto;

import java.util.Date;
import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.interfaces.DeathFilterEntity;

public class GameDTO implements DeathFilterEntity {
    private static Logger log = LoggerFactory.getLogger(Game.class);

    private Long id;
    private Date createdOn;
    private String roleName;
    private String laneName;
    private Integer championId;

    public GameDTO() {}

    public static GameDTO copy(GameDTO gameToCopy) {
        GameDTO game = new GameDTO();
        game.setId(gameToCopy.getId());
        game.setLaneName(gameToCopy.getLaneName());
        game.setRoleName(gameToCopy.getRoleName());
        game.setChampionId(gameToCopy.getChampionId());
        game.setCreatedOn(gameToCopy.getCreatedOn());
        return game;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    public Integer getChampionId() {
        return championId;
    }

    public void setChampionId(Integer championId) {
        this.championId = championId;
    }

    @Override
    public Predicate<Death> getPredicate() {
        return (Death death) -> {
            log.info("getDeathFilterByGamePredicate: Filter by Game with id: {}", this.getId());
            Boolean result = death.getGame().getId().equals(this.getId());
            log.info("Death with id: {} has Game with id: {} equals Filter Game with id:{} ? {} ",
                    death.getId(), death.getGame().getId(), this.getId(), result);
            return result;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GameDTO gameDTO = (GameDTO) o;
        return Objects.equals(id, gameDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
