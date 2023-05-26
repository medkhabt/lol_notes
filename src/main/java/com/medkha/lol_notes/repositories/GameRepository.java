package com.medkha.lol_notes.repositories;

import com.medkha.lol_notes.dto.GameDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medkha.lol_notes.entities.Game;

import java.util.List;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {
    public List<Game> findGamesByGameId(String gameId);
}
