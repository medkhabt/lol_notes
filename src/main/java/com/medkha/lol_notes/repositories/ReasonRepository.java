package com.medkha.lol_notes.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.medkha.lol_notes.entities.Reason;

@Repository
public interface ReasonRepository extends CrudRepository<Reason, Long>{
	
}
