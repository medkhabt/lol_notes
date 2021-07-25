package com.medkha.lol_notes.controllers.filters;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.dto.GameDTO;
import com.medkha.lol_notes.dto.ReasonDTO;
import com.medkha.lol_notes.services.ReasonService;
import com.medkha.lol_notes.services.filters.ReasonFilterService;

@RestController
@RequestMapping("/reasons")
public class ReasonFilterController {
    private static Logger log = LoggerFactory.getLogger(ReasonFilterController.class);
    private final ReasonFilterService reasonFilterService;
    private final ReasonService reasonService;

    public ReasonFilterController(
            ReasonFilterService reasonFilterService,
            ReasonService reasonService){
        this.reasonFilterService = reasonFilterService;
        this.reasonService = reasonService;
    }

    @GetMapping(value = "/game/{gameId}/top-game-calculate")
    @ResponseStatus(HttpStatus.OK)
    public ReasonDTO getTopReasonByGameController(
            @PathVariable("gameId") Long gameId){
        ReasonDTO reason = reasonFilterService.getDeathsByGameAndCalculateTopReasonByGame(new GameDTO(gameId));
        log.info("getTopReasonByGameController: get top reason which is with id {} successfully", reason.getId());
        return reasonService.findById(reason.getId());
    }
}
