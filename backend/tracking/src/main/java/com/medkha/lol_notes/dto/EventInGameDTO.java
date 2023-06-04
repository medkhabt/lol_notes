package com.medkha.lol_notes.dto;

import java.util.Objects;

public class EventInGameDTO {
    public String EventId;
    public String EventName;
    public String EventTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventInGameDTO that = (EventInGameDTO) o;
        return EventName.equals(that.EventName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(EventName);
    }
}
