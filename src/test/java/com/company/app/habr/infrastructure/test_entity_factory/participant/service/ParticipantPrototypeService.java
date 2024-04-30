package com.company.app.habr.infrastructure.test_entity_factory.participant.service;

import java.util.List;

import com.company.app.domain.entity.Participant;
import com.company.app.domain.repository.ParticipantRepository;
import com.company.app.domain.repository.PostRepository;
import com.company.app.domain.repository.RankRepository;
import com.company.app.habr.infrastructure.test_entity_factory.participant.model.ParticipantPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantPrototypeService {

    private final ParticipantPrototypeFinisher participantPrototypeFinisher;
    private final ParticipantRepository participantRepository;
    private final RankRepository rankRepository;
    private final PostRepository postRepository;

    public List<Participant> create(ParticipantPrototype prototype) {
        return participantPrototypeFinisher.create(prototype);
    }

}