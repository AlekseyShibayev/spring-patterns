package com.company.app.habr.infrastructure.test_entity_factory.participant.service;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Participant;
import com.company.app.habr.infrastructure.test_entity_factory.participant.model.ParticipantPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantPrototypeFinisher {

    @Transactional
    public List<Participant> create(ParticipantPrototype prototype) {
        List<Participant> result = new ArrayList<>();
        for (int i = 0; i < prototype.getAmount(); i++) {
            result.add(createOne(prototype));
        }
        return result;
    }

    private Participant createOne(ParticipantPrototype prototype) {
        return null;
    }

}