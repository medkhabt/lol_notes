package com.medkha.lol_notes.dto.interfaces;

import java.util.function.Predicate;

import com.medkha.lol_notes.dto.DeathDTO;
import com.medkha.lol_notes.entities.Death;

public interface DeathFilterOption {
    Predicate<DeathDTO> getPredicate();
}
