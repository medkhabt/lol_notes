package com.medkha.lol_notes.services;

import java.util.Set;

import com.medkha.lol_notes.dto.ReasonDTO;

public interface ReasonService {
	
	public ReasonDTO createReason(ReasonDTO reason);
	public ReasonDTO updateReason(ReasonDTO reason);
	public void deleteReason(Long id); 
	public Set<ReasonDTO> findAllReasons();
	public ReasonDTO findById(Long id);
}
