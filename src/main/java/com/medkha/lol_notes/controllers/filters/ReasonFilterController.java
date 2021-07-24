package com.medkha.lol_notes.controllers.filters;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.dto.DeathFilterOption;
import com.medkha.lol_notes.dto.FilterSearchRequest;
import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.mapper.MapperService;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.filters.DeathFilterService;
import com.medkha.lol_notes.services.filters.ReasonFilterService;

@RestController
@RequestMapping("/reasons")
public class ReasonFilterController {
    private static Logger log = LoggerFactory.getLogger(DeathFilterController.class);
    private final ReasonFilterService reasonFilterService;
    private final ReasonService reasonService;
    private final MapperService mapperService;

    public ReasonFilterController(
            ReasonFilterService reasonFilterService,
            ReasonService reasonService,
            MapperService mapperService){
        this.reasonFilterService = reasonFilterService;
        this.mapperService = mapperService;
        this.reasonService = reasonService;
    }

    @GetMapping(value = "/game/{gameId}/top-game-calculate")
    @ResponseStatus(HttpStatus.OK)
    public ReasonDTO getTopReasonByGameController(
            @PathVariable("gameId") Long gameId){
        ReasonDTO reason = reasonFilterService.getTopReasonByGame(new GameDTO(gameId));
        return reasonService.findById(reason.getId());
    }
}
