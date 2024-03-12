package com.company.app.habr.infrastructure.test_entity_factory;

import com.company.app.habr.domain.enums.StatusType;
import com.company.app.habr.infrastructure.test_entity_factory.habr.model.HabrPrototype;
import com.company.app.habr.infrastructure.test_entity_factory.habr.service.HabrPrototypeService;
import com.company.app.habr.infrastructure.test_entity_factory.participant.model.ParticipantPrototype;
import com.company.app.habr.infrastructure.test_entity_factory.participant.service.ParticipantPrototypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactory {

    private final HabrPrototypeService habrPrototypeService;
    private final ParticipantPrototypeService participantPrototypeService;

    public HabrPrototype habrBy(StatusType status) {
        return new HabrPrototype()
            .setStatus(status)
            .setHabrPrototypeService(habrPrototypeService);
    }

    public ParticipantPrototype participantBy(String name) {
        return new ParticipantPrototype()
            .setName(name)
            .setParticipantPrototypeService(participantPrototypeService);
    }

}