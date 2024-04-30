package com.company.app.infrastructure.participant.model;

import java.util.ArrayList;
import java.util.List;

import com.company.app.domain.entity.Participant;
import com.company.app.domain.enums.RankType;
import com.company.app.infrastructure.participant.service.ParticipantPrototypeService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class ParticipantPrototype {

    private RankType rankType;
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

    public ParticipantPrototype withName(String participantName) {
        chain.add(participant -> participantPrototypeService.setParticipantName(participant, participantName));
        return this;
    }

    public ParticipantPrototype withPost(String customTitle) {
        chain.add(participant -> participantPrototypeService.addPost(participant, customTitle));
        return this;
    }

}