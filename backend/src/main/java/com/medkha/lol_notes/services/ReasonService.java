package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.entities.Reason;

public interface ReasonService {
	
	public Reason createReason(Reason reason) throws Exception;
	public Reason updateReason(Reason reason) throws Exception; 
	public void deleteReason(Long id); 
	public Set<Reason> findAllReasons();
	public Reason findById(Long id); 
	public Boolean existsInDataBase(Long id);
}
