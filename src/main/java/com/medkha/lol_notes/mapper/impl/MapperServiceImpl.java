package com.medkha.lol_notes.mapper.impl;

import java.util.HashSet;
import java.util.Set;

import org.reflections.Reflections;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.FilterSearchRequest;
import com.medkha.lol_notes.dto.DeathFilterOption;

@Service
public class MapperServiceImpl extends MapperBaseService{

    @Override
    public Set<DeathFilterOption> convertFilterSearchRequestToDeathFilterOptions(FilterSearchRequest filterDeathRequest) {
        Set<DeathFilterOption> deathFilterOptions = new HashSet<>();
        // get all implementatiosn of deatFilterOption
        Set<String> paramNames = this.convertInterfaceImplementationsToParamsByInterfaceAndSingleClassMapperFunction(
                DeathFilterOption.class,
                this::mapClassDtoToParamName);

        // loop for the  filterDeathRequest
        paramNames.stream().forEach(
                (param) -> {
                    try{
                        filterDeathRequest.getParams().get(param);
                        // TODO: FactoryMethod for creating DeathFitlerOptions.
                    } catch (NullPointerException err) {
                        // TODO: Log it.
                    }
                }
        );
        // get paramNames from Classes
        return null;
    }


}


