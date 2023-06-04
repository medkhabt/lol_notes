package com.medkha.lol_notes.dto;

import java.util.function.Predicate;

public class QueueDTO implements DeathFilterOption{
    private Integer id;
    private String queueName;

    public QueueDTO(){}

    public QueueDTO(Integer id, String queueName) {
        this.id = id;
        this.queueName = queueName;
    }

    public QueueDTO(String paramId) {
        this.id = Integer.parseInt(paramId);
    }

    public String getQueueName() {
        return queueName;
    }

    public void setQueueName(String queueName) {
        this.queueName = queueName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public Predicate<DeathDTO> getPredicate() {
        return (d) -> d.getGame().getQueueId().equals(this.id);
    }
}
