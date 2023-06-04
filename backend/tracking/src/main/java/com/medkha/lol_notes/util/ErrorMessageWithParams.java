package com.medkha.lol_notes.util;

import java.util.HashMap;
import java.util.Map;

public class ErrorMessageWithParams extends ErrorMessage{
    private Map<String, String> errorParams = new HashMap<>();
    public ErrorMessageWithParams(String status, String message) {
        super(status, message);
    }
    public void addParam(String keyParam, String valueParam) {
        errorParams.put(keyParam, valueParam);
    }
    public Map<String, String> getErrorParams() {
        return new HashMap<>(errorParams);
    }
    public void setErrorParams(Map<String, String> errorParams) {
        this.errorParams = errorParams;
    }
}
