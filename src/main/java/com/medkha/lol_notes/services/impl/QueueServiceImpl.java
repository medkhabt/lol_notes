package com.medkha.lol_notes.services.impl;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.dto.QueueDTO;
import com.medkha.lol_notes.services.QueueService;
import com.merakianalytics.orianna.types.common.Queue;

@Service
public class QueueServiceImpl implements QueueService {
    private static Logger log = LoggerFactory.getLogger(QueueServiceImpl.class);
    @PostConstruct
    public void init() {
        getAllQueues();
    }
    @Override
    @Cacheable("allQueues")
    public Set<QueueDTO> getAllQueues() {
        log.info("init: Start initialization of queueService...");
        Set<QueueDTO> queueDTOSet = Arrays.stream(Queue.values()).map(
                queue ->  new QueueDTO(queue.getId(), queue.toString())
        ).collect(Collectors.toSet());
        log.info("getAllQueues: Got all queues successfully");
        return queueDTOSet;
    }

    @Override
    public Set<QueueDTO> getAllQueuesWithoutDeprecate() {
        return getAllQueues().stream().filter(
                queueDTO -> !queueDTO.getQueueName().toLowerCase().contains("deprecated")
        ).collect(Collectors.toSet());
    }

    @Override
    public Optional<QueueDTO> getQueueById(Integer id) {
        return getAllQueues().stream().filter(queue -> queue.getId().equals(id)).findAny();
    }
}
