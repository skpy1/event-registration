package com.example.event.repository;

import com.example.event.model.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByEmailIgnoreCase(String email);
}
