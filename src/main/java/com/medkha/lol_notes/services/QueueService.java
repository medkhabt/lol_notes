package com.medkha.lol_notes.services;

import java.util.Optional;
import java.util.Set;

import com.medkha.lol_notes.dto.QueueDTO;

public interface QueueService {
    Set<QueueDTO> getAllQueues();
    Optional<QueueDTO> getQueueById(Integer id);
}
