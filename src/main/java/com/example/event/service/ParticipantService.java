package com.example.event.service;

import com.example.event.model.Participant;
import java.util.List;

public interface ParticipantService {
    List<Participant> findAll();
    Participant findById(Long id);
    Participant create(Participant p);
    Participant update(Long id, Participant p);
    void delete(Long id);
}
