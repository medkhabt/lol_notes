package com.medkha.lol_notes.services.impl.filters;

import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.exceptions.IncorrectReturnSizeException;
import com.medkha.lol_notes.services.filters.DeathFilterService;
import com.medkha.lol_notes.services.filters.ReasonFilterService;

@Service
public class ReasonFilterServiceImpl implements ReasonFilterService {
    private DeathFilterService deathFilterService;

    public ReasonFilterServiceImpl(DeathFilterService deathFilterService) {
        this.deathFilterService = deathFilterService;
    }
    @Override
    public ReasonDTO getDeathsByGameAndCalculateTopReasonByGame(GameDTO game) {
        Stream<DeathDTO> deathStreamFilteredByGame = getDeathsStreamByGame(game);

        Map<Long, Long> mapOfReasonRepetitions =
                convertDeathStreamToMapOfReasonIdsAndTheirNumberOfOccurence(deathStreamFilteredByGame);

        Long idTopReason = getTopIdReasonFromMapOfIdReasonsAndTheirNumberOfOccurence(mapOfReasonRepetitions);

        return new ReasonDTO(idTopReason);
    }

    private Long getTopIdReasonFromMapOfIdReasonsAndTheirNumberOfOccurence(Map<Long, Long> mapOfReasonRepetitions) {
        Map.Entry<Long, Long> maxEntry = Map.entry((long)0, (long)0);
        int topReasonsCount = 0;
        for(Map.Entry<Long,Long> entry : mapOfReasonRepetitions.entrySet()) {
            if(maxEntry.getValue() <= entry.getValue()) {
                if(maxEntry.getValue().equals(entry.getValue())) {
                    topReasonsCount++;
                }
                else {
                    topReasonsCount = 1;
                }
                maxEntry = entry;
            }
        }
        if(topReasonsCount > 1) {
            throw new IncorrectReturnSizeException("There are many reasons with the same death.",1 , topReasonsCount);
        }
        return maxEntry.getKey();
    }

    private Map<Long, Long> convertDeathStreamToMapOfReasonIdsAndTheirNumberOfOccurence(Stream<DeathDTO> deathStreamFilteredByGame) {
        return deathStreamFilteredByGame
                .map(DeathDTO::getReason)
                .collect(
                        Collectors.groupingBy(ReasonDTO::getId, Collectors.counting())
                );
    }

    private Stream<DeathDTO> getDeathsStreamByGame(GameDTO game) {
        return deathFilterService.getDeathsByFilter(Collections.singletonList(game.getPredicate()));
    }
}
