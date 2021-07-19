package com.medkha.lol_notes.dto.factories;

import com.medkha.lol_notes.dto.DeathFilterOption;

public interface DeathFilterOptionFactory {
    public DeathFilterOption createDeathFilterOptionByParamAndItsValue(String param, String value);
}
