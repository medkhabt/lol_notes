package com.medkha.lol_notes.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;

@Repository
public interface DeathRepository extends CrudRepository<Death, Long>{
	public Set<Death> findByGame(Game game); 
}
