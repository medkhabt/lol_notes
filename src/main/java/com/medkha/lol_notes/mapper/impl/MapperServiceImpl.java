package com.medkha.lol_notes.mapper.impl;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.mapper.MapperService;

@Service
public class MapperServiceImpl implements MapperService{

    private ModelMapper modelMapper = new ModelMapper();


    @Override
    public <D> D convert(Object source, Class<D> destination) {
        return modelMapper.map(source, destination);
    }

    @Override
    public <D> Set<D> convertSet(Set source, Class<D> destination) {
        return (Set<D>) source.stream().map(s -> modelMapper.map(s, destination)).collect(Collectors.toSet());
    }
}


