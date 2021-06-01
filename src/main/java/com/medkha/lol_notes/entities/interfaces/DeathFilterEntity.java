package com.medkha.lol_notes.entities.interfaces;

import java.util.function.Predicate;

import com.medkha.lol_notes.entities.Death;

public interface DeathFilterEntity {

    Predicate<Death> getPredicate();
}
