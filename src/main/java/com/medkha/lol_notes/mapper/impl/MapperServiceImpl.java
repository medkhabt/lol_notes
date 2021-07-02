package com.medkha.lol_notes.mapper.impl;

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
}


