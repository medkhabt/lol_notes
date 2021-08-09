package com.medkha.lol_notes.dto;


import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReasonDTO implements DeathFilterOption {
    private static Logger log = LoggerFactory.getLogger(ReasonDTO.class);
    private Long id;
    private String title;
    private String description;

    public ReasonDTO(){}

    public ReasonDTO(String paramId) {
        this.id = Long.parseLong(paramId);
    }

    public ReasonDTO(Long id) {
        this.id = id;
    }

    public static ReasonDTO copy(ReasonDTO reasonToCopy){
        ReasonDTO newReason = new ReasonDTO();
        newReason.setId(reasonToCopy.getId());
        newReason.setDescription(reasonToCopy.getDescription());
        return newReason;
    }

    public static ReasonDTO proxy(Long id) {
        ReasonDTO reason = new ReasonDTO();
        reason.setId(id);
        return reason;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
