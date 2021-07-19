package com.medkha.lol_notes.dto;


import java.util.HashMap;
import java.util.Map;

public class FilterSearchRequest {
    private Map<String, String> params = new HashMap<>();

    public void setParams(Map<String, String> params) {
        this.params = params;
    }
    public Map<String, String> getParams() {
        return new HashMap<>(params);
    }
}