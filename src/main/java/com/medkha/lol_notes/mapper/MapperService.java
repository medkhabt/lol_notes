package com.medkha.lol_notes.mapper;

public interface MapperService {
    public <D> D convert(Object source, Class<D> destination);
}
