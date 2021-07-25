package com.medkha.lol_notes.services.filters;

import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;

public interface ReasonFilterService {
    public ReasonDTO getDeathsByGameAndCalculateTopReasonByGame(GameDTO game);
}
