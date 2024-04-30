package com.company.app.infrastructure;

import com.company.app.domain.enums.RankType;
import com.company.app.infrastructure.participant.model.ParticipantPrototype;
import com.company.app.infrastructure.participant.service.ParticipantPrototypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactory {

    private final ParticipantPrototypeService participantPrototypeService;

    public ParticipantPrototype participantBy(RankType rankType) {
        return new ParticipantPrototype()
            .setRankType(rankType)
            .setParticipantPrototypeService(participantPrototypeService);
    }

}