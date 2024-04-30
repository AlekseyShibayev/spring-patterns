package com.company.app.infrastructure.participant.service;

import java.util.List;

import com.company.app.domain.entity.Participant;
import com.company.app.domain.entity.Post;
import com.company.app.domain.entity.Rank;
import com.company.app.domain.repository.ParticipantRepository;
import com.company.app.domain.repository.PostRepository;
import com.company.app.domain.repository.RankRepository;
import com.company.app.infrastructure.participant.model.ParticipantPrototype;
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

    public void setParticipantName(Participant participant, String participantName) {
        participant.setName(participantName);
        participantRepository.save(participant);
    }

    public void addPost(Participant participant, String postTitle) {
        Post post = new Post()
            .setTitle(postTitle)
            .setParticipant(participant);
        postRepository.save(post);

        participant.getPosts().add(post);
        participantRepository.save(participant);
    }

}