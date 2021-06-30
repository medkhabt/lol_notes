package com.medkha.lol_notes.controllers;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.entities.ChampionV2;
import com.medkha.lol_notes.services.ChampionService;

@RestController
@RequestMapping(value = "champions")
public class ChampionController {

    @Autowired
    private ChampionService championService;

    @GetMapping(produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public Set<ChampionV2> getAllChampions(){
        return this.championService.getAllChampions();
    }

    @GetMapping(value="/name/{name}", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public ChampionV2 getChampionsByNameController(@PathVariable("name") String name){
        return this.championService.getChampionByName(name);
    }
}
