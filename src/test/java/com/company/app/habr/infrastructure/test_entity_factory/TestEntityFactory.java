package com.company.app.habr.infrastructure.test_entity_factory;

import com.company.app.habr.infrastructure.test_entity_factory.participant.model.ParticipantPrototype;
import com.company.app.habr.infrastructure.test_entity_factory.participant.service.ParticipantPrototypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactory {

    private final ParticipantPrototypeService participantPrototypeService;

    public ParticipantPrototype participantBy(String name) {
        return new ParticipantPrototype()
            .setName(name)
            .setParticipantPrototypeService(participantPrototypeService);
    }

}