package com.company.app.habr.infrastructure.test_entity_factory.participant.model;

import com.company.app.habr.domain.entity.Participant;


@FunctionalInterface
public interface Callback {

    void modify(Participant participant);

}