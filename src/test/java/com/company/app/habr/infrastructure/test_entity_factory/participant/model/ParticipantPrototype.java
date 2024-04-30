package com.company.app.habr.infrastructure.test_entity_factory.participant.model;

import java.util.ArrayList;
import java.util.List;

import com.company.app.domain.entity.Participant;
import com.company.app.habr.infrastructure.test_entity_factory.participant.service.ParticipantPrototypeService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class ParticipantPrototype {

    private String name;
    private ParticipantPrototypeService participantPrototypeService;
    private List<Callback> chain = new ArrayList<>();
    private int amount;

    /**
     * terminal operations
     */
    public Participant createOne() {
        this.amount = 1;
        return participantPrototypeService.create(this).get(0);
    }

    public List<Participant> createMany(int amount) {
        this.amount = amount;
        return participantPrototypeService.create(this);
    }

    /**
     * intermediate operations
     */
    public ParticipantPrototype with(Callback callback) {
        chain.add(callback);
        return this;
    }

}