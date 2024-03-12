package com.company.app.habr.infrastructure.test_entity_factory.service;

import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.Participant;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.ParticipantRepository;
import com.company.app.habr.infrastructure.test_entity_factory.model.HabrPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class HabrPrototypeService {

    private final HabrRepository habrRepository;
    private final ParticipantRepository participantRepository;
    private final TestEntityFactoryFinisher testEntityFactoryFinisher;

    public List<Habr> create(HabrPrototype habrPrototype) {
        return testEntityFactoryFinisher.create(habrPrototype);
    }

    public void addParticipant(Habr habr, String name) {
        Participant user = new Participant()
            .setHabr(habr)
            .setName(name);

        participantRepository.save(user);
        habr.getUsers().add(user);
        habrRepository.save(habr);
    }

}