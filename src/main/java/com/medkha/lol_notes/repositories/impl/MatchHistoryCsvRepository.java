package com.medkha.lol_notes.repositories.impl;

import com.medkha.lol_notes.entities.Game;
import com.medkha.lol_notes.repositories.MatchHistoryRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MatchHistoryCsvRepository implements MatchHistoryRepository {
    @Override
    public void exportMatchHistory(List<Game> games) {
        throw new UnsupportedOperationException();
    }
    private String convertToCsvFormat(List<Game> games) {
        for(Game game: games){

        }
        throw new UnsupportedOperationException();
    }
}
