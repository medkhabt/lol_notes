package com.medkha.lol_notes.repositories;

import com.medkha.lol_notes.entities.Game;

import java.util.List;

public interface MatchHistoryRepository {
    void exportMatchHistory(List<Game> games);
}
