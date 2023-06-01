package com.medkha.lol_notes.repositories;

import com.medkha.lol_notes.dto.GameFinishedDTO;

import java.util.Collection;

public interface MatchHistoryRepository {
    // Is it the right thing to interact with a DTO object in a repo?
    void exportMatchHistory(Collection<GameFinishedDTO> games);
}
