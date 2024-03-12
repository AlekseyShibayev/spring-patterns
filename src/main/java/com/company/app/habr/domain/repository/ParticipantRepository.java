package com.company.app.habr.domain.repository;

import com.company.app.habr.domain.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}