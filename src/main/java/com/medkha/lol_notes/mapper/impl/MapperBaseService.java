package com.medkha.lol_notes.mapper.impl;

import java.util.Set;
import java.util.function.Function;
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
    public Set<String> convertInterfaceImplementationsToParamsByInterfaceAndSingleClassMapperFunction(
                            Class interfaceClass,
                            Function<Class, String> singleClassMapperFunction) {
        if(interfaceClass.isInterface()) {
            Reflections reflections = new Reflections("com.medkha.lol_notes");
            Set<Class> classes = reflections.getSubTypesOf(interfaceClass);
            return classes.stream().map(singleClassMapperFunction).collect(Collectors.toSet());
        }
        else {
            throw new IllegalArgumentException(interfaceClass.getSimpleName() + " is not an interface so can't proceed.");
        }

    }

    @Override
    public abstract Set<DeathFilterOption> convertFilterSearchRequestToDeathFilterOptions(FilterSearchRequest filterDeathRequest);
}


