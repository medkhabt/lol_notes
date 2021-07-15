package com.medkha.lol_notes.dto;


import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.medkha.lol_notes.dto.interfaces.DeathFilterOption;
import com.medkha.lol_notes.entities.Reason;

public class ReasonDTO implements DeathFilterOption {
    private static Logger log = LoggerFactory.getLogger(Reason.class);
    private Long id;
    private String description;

    public ReasonDTO(){}

    public static ReasonDTO copy(ReasonDTO reasonToCopy){
        ReasonDTO newReason = new ReasonDTO();
        newReason.setId(reasonToCopy.getId());
        newReason.setDescription(reasonToCopy.getDescription());
        return newReason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReasonDTO reasonDTO = (ReasonDTO) o;
        return Objects.equals(id, reasonDTO.id) && Objects.equals(description, reasonDTO.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }

    @Override
    public String toString() {
        return "ReasonDTO{" +
                "id=" + id +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public Predicate<DeathDTO> getPredicate() {
        return (DeathDTO death) -> {
            log.info("getDeathFilterByReasonPredicate: Filter by Reason with id: {}", this.getId());
            Boolean result = death.getReason().getId().equals(this.getId());
            log.info("Death with id: {} has Reason with id: {} equals Filter Reason with id:{} ? {} ",
                    death.getId(), death.getReason().getId(), this.getId(), result);
            return result;
        };
    }
}
