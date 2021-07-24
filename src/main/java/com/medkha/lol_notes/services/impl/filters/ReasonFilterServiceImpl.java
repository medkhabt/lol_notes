package com.medkha.lol_notes.services.impl.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
        Long max = (long)0;
        Long keyOfMax = (long)0;
        for(Long key : mapOfReasonRepetitions.keySet()) {
            Long value = mapOfReasonRepetitions.get(key);
            if(max < value) {
                max = value;
                keyOfMax = key;
            }
        }

        return new ReasonDTO(String.valueOf(keyOfMax));
    }
}
