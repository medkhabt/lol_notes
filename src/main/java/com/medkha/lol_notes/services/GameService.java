package com.medkha.lol_notes.services;

import java.util.Optional;
import java.util.Set;

import com.medkha.lol_notes.dto.GameDTO;

public interface GameService {
	
	public GameDTO createGame(GameDTO game);
	public GameDTO updateGame(GameDTO game);
	public void deleteGame(Long id);
	public Set<GameDTO> findAllGames();
	public GameDTO findById(Long id);
	public Optional<GameDTO> findByGameId(String gameId);
}
