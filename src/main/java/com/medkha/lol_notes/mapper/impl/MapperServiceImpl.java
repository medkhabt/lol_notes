package com.medkha.lol_notes.mapper.impl;

import java.util.Set;

import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.FilterSearchRequest;
import com.medkha.lol_notes.dto.DeathFilterOption;

@Service
public class MapperServiceImpl extends MapperBaseService{

    @Override
    public Set<DeathFilterOption> convertFilterSearchRequestToDeathFilterOptions(FilterSearchRequest filterDeathRequest) {
        // get all implementatiosn of deatFilterOption
        Reflections reflections = new Reflections("com.medkha.lol_notes.dto");
        Set<Class<? extends DeathFilterOption>> classes = reflections.getSubTypesOf(DeathFilterOption.class);

        // loop for the  filterDeathRequest
        // get paramNames from Classes
        classes.stream().map(this::mapClassDtoToParamName);
        return null;
    }


}


