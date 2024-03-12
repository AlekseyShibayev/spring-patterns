package com.company.app.habr.infrastructure.test_entity_factory.participant.service;

import java.util.List;

import com.company.app.habr.domain.entity.Participant;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.ParticipantRepository;
import com.company.app.habr.infrastructure.test_entity_factory.participant.model.ParticipantPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantPrototypeService {

    private final HabrRepository habrRepository;
    private final ParticipantRepository participantRepository;
    private final ParticipantPrototypeFinisher participantPrototypeFinisher;

    public List<Participant> create(ParticipantPrototype prototype) {
        return participantPrototypeFinisher.create(prototype);
    }

}