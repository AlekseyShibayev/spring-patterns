package com.company.app.infrastructure.participant.service;

import java.util.ArrayList;
import java.util.List;

import com.company.app.domain.entity.Participant;
import com.company.app.domain.entity.Rank;
import com.company.app.domain.enums.RankType;
import com.company.app.domain.repository.ParticipantRepository;
import com.company.app.domain.repository.RankRepository;
import com.company.app.infrastructure.participant.model.Callback;
import com.company.app.infrastructure.participant.model.ParticipantPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class ParticipantPrototypeFinisher {

    private final ParticipantRepository participantRepository;
    private final RankRepository rankRepository;

    @Transactional
    public List<Participant> create(ParticipantPrototype prototype) {
        List<Participant> result = new ArrayList<>();
        for (int i = 0; i < prototype.getAmount(); i++) {
            result.add(createOne(prototype));
        }
        return result;
    }

    private Participant createOne(ParticipantPrototype prototype) {
        RankType rankType = prototype.getRankType();
        Rank rank = rankRepository.findByRankType(rankType);

        Participant participant = new Participant()
            .setName("defaultName")
            .setRank(rank);
        participantRepository.save(participant);

        List<Callback> chain = prototype.getChain();
        chain.forEach(callback -> callback.modify(participant));

        return participant;
    }

}