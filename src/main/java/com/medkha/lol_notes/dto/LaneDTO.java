package com.medkha.lol_notes.dto;

import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LaneDTO implements DeathFilterOption {
    private static Logger log = LoggerFactory.getLogger(LaneDTO.class);
    private String laneName;

    public LaneDTO(String laneName) {
        this.laneName = laneName;
    }

    public String getLaneName() {
        return laneName;
    }

    public void setLaneName(String laneName) {
        this.laneName = laneName;
    }

    @Override
    public Predicate<DeathDTO> getPredicate() {
        return (DeathDTO death) -> {
            log.info("getDeathFilterByLanePredicate: Filter by Lane with name: {}", this.getLaneName());
            Boolean result = death.getGame().getLaneName().equalsIgnoreCase(this.getLaneName());
            log.info("Death with id: {} has Lane with name: {} equals Filter Lane with id:{} ? {} ",
                    death.getId(), death.getGame().getLaneName(), this.getLaneName(), result);
            return result;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LaneDTO laneDTO = (LaneDTO) o;
        return this.laneName.equalsIgnoreCase(laneDTO.laneName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(laneName);
    }
}
