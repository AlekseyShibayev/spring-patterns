package com.company.app.infrastructure.participant.model;


import com.company.app.domain.entity.Participant;


@FunctionalInterface
public interface Callback {

    void modify(Participant participant);

}