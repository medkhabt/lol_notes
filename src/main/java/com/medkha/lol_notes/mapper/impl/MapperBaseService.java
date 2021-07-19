package com.medkha.lol_notes.mapper.impl;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.reflections.Reflections;

import com.medkha.lol_notes.dto.FilterSearchRequest;
import com.medkha.lol_notes.dto.DeathFilterOption;
import com.medkha.lol_notes.mapper.MapperService;

public abstract class MapperBaseService implements MapperService {
    protected final ModelMapper modelMapper = new ModelMapper();

    @Override
    public <D> D convert(Object source, Class<D> destination) {
        return modelMapper.map(source, destination);
    }

    @Override
    public <D> Set<D> convertSet(Set source, Class<D> destination) {
        return (Set<D>) source.stream().map(s -> modelMapper.map(s, destination)).collect(Collectors.toSet());
    }

    @Override
    public String mapClassDtoToParamName(Class classDto) {
        if(classDto.getSimpleName().endsWith("DTO")) {
            return classDto.getSimpleName().split("DTO")[0].toLowerCase();
        }
        else {
            return "";
        }
    }

    @Override
    public Set<String> convertInterfaceImplementationsToParamsByInterface(Class interfaceClass) {
        Reflections reflections = new Reflections("com.medkha.lol_notes");
        Set<Class<? extends DeathFilterOption>> classes = reflections.getSubTypesOf(interfaceClass);
        return classes.stream().map(this::mapClassDtoToParamName).collect(Collectors.toSet());
    }

    @Override
    public abstract Set<DeathFilterOption> convertFilterSearchRequestToDeathFilterOptions(FilterSearchRequest filterDeathRequest);
}


