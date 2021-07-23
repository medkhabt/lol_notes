package com.medkha.lol_notes.repositories;

import java.util.Set;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medkha.lol_notes.entities.Death;
import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.entities.Reason;

@Repository
public interface DeathRepository extends CrudRepository<Death, Long>{
	public Set<Death> findByGame(Game game); 
	public Set<Death> findByReason(Reason reason);
	@Query("SELECT count(d) FROM Death d")
	public Integer countAllDeaths();
}
