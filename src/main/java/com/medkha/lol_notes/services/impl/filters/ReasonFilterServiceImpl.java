package com.medkha.lol_notes.services.impl.filters;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.services.filters.DeathFilterService;
import com.medkha.lol_notes.services.filters.ReasonFilterService;

@Service
public class ReasonFilterServiceImpl implements ReasonFilterService {
    private DeathFilterService deathFilterService;

    public ReasonFilterServiceImpl(DeathFilterService deathFilterService) {
        this.deathFilterService = deathFilterService;
    }
    @Override
    public ReasonDTO getTopReasonByGame(GameDTO game) {
        // get deaths by game
        Map<Long, Long> mapOfReasonRepetitions = deathFilterService.getDeathsByFilter(Collections.singletonList(game.getPredicate()))
                .map(DeathDTO::getReason)
                .collect(
                        Collectors.groupingBy(ReasonDTO::getId, Collectors.counting())
                );
        Map.Entry<Long, Long> maxEntry = Map.entry((long)0, (long)0);
        for(Map.Entry<Long,Long> entry : mapOfReasonRepetitions.entrySet()) {
            if(maxEntry.getValue() < entry.getValue()) {
                maxEntry = entry;
            }
        }

        return new ReasonDTO(String.valueOf(maxEntry.getKey()));
    }
}
