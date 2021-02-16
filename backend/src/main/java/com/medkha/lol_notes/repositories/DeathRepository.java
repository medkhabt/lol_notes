package com.medkha.lol_notes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medkha.lol_notes.entities.Death;

@Repository
public interface DeathRepository extends CrudRepository<Death, Long>{

}
