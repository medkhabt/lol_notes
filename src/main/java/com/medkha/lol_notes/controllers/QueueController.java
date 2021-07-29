package com.medkha.lol_notes.controllers;

import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.medkha.lol_notes.dto.QueueDTO;
import com.medkha.lol_notes.services.QueueService;

@RestController
@RequestMapping(value = "queues")
public class QueueController {
    private static Logger log = LoggerFactory.getLogger(QueueController.class);
    private QueueService queueService;

    public QueueController(QueueService queueService) {
        this.queueService = queueService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public Set<QueueDTO> getAllQueuesController(){
        Set<QueueDTO> queueDTOSet = new HashSet<>(this.queueService.getAllQueues());
        log.info("GetAllQueuesController: {} queues were found successfully.", queueDTOSet.size());
        return queueDTOSet;
    }

    @GetMapping(value = "/remove-duplicate")
    @ResponseStatus(HttpStatus.OK)
    public Set<QueueDTO> getAllQueuesWithoutDeprecationController() {
        Set<QueueDTO> queueDTOWithoutDeprecationSet = new HashSet<>(this.queueService.getAllQueuesWithoutDeprecate());
        log.info("GetAllQueuesWithoutDeprecationController: {} queues without deprecations werer found successfully.",
                queueDTOWithoutDeprecationSet.size());
        return queueDTOWithoutDeprecationSet;
    }
}
