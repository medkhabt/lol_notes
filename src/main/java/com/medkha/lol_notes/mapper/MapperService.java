package com.medkha.lol_notes.mapper;

import java.util.Collection;
import java.util.Set;

public interface MapperService {
    public <D> D convert(Object source, Class<D> destination);
    public <D> Set<D> convertSet(Set source, Class<D> destination);
}
