package com.medkha.lol_notes.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;
import java.util.Optional;

public class EventInGameDTO {
    @JsonProperty("EventID")
    public String eventId;
    @JsonProperty("EventName")
    public String eventName;
    @JsonProperty("EventTime")
    public String eventTime;
    @JsonProperty("KillerName")
    public Optional<String> killerName;
    @JsonProperty("VictimName")
    public Optional<String> victimName;
    @JsonProperty("Assisters")
    public String[] assisters;
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventInGameDTO that = (EventInGameDTO) o;
        return eventName.equals(that.eventName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName);
    }
}
