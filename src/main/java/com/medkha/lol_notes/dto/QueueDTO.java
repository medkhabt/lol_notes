package com.medkha.lol_notes.dto;

public class QueueDTO {
    Integer id;
    String queueName;

    public QueueDTO(){}

    public QueueDTO(Integer id, String queueName) {
        this.id = id;
        this.queueName = queueName;
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
}
