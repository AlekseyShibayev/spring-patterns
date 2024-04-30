package com.company.app.domain.repository;

import com.company.app.domain.entity.Participant;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ParticipantRepository extends JpaRepository<Participant, Long> {
}