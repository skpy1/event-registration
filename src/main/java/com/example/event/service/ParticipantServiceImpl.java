package com.example.event.service;

import com.example.event.exception.EmailAlreadyExistsException;
import com.example.event.model.Participant;
import com.example.event.repository.ParticipantRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    private final ParticipantRepository repo;

    public ParticipantServiceImpl(ParticipantRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<Participant> findAll() {
        return repo.findAll();
    }

    @Override
    public Participant findById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Участник не найден: id=" + id));
    }

    @Override
    public Participant create(Participant p) {
        if (repo.existsByEmailIgnoreCase(p.getEmail())) {
            throw new EmailAlreadyExistsException(p.getEmail());
        }
        try {
            return repo.save(p);
        } catch (DataIntegrityViolationException e) {
            throw new EmailAlreadyExistsException(p.getEmail());
        }
    }

    @Override
    public Participant update(Long id, Participant p) {
        Participant existing = findById(id);
        if (!existing.getEmail().equalsIgnoreCase(p.getEmail())
                && repo.existsByEmailIgnoreCase(p.getEmail())) {
            throw new EmailAlreadyExistsException(p.getEmail());
        }
        existing.setName(p.getName());
        existing.setEmail(p.getEmail());
        return repo.save(existing);
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
