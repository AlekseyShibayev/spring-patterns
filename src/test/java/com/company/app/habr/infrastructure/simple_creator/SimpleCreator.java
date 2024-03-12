package com.company.app.habr.infrastructure.simple_creator;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.Participant;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleCreator {

    private final HabrRepository habrRepository;
    private final ParticipantRepository participantRepository;

    public Habr createMinimumPosibleHabr(Status status) {
        return habrRepository.save(new Habr()
            .setStatus(status));
    }

    public void addParticipant(Habr habr, String name) {
        Participant user = new Participant()
            .setName(name)
            .setHabr(habr);
        participantRepository.save(user);
        habr.getUsers().add(user);
        habrRepository.save(habr);
    }

}