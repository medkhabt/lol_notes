package com.medkha.lol_notes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medkha.lol_notes.entities.Game;

@Repository
public interface GameRepository extends CrudRepository<Game, Long> {

}
