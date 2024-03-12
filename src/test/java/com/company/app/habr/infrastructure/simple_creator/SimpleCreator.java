package com.company.app.habr.infrastructure.simple_creator;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.Participant;
import com.company.app.habr.domain.entity.Post;
import com.company.app.habr.domain.entity.Role;
import com.company.app.habr.domain.enums.RoleType;
import com.company.app.habr.domain.enums.StatusType;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.ParticipantRepository;
import com.company.app.habr.domain.repository.PostRepository;
import com.company.app.habr.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class SimpleCreator {

    private final HabrRepository habrRepository;
    private final ParticipantRepository participantRepository;
    private final RoleRepository roleRepository;
    private final PostRepository postRepository;

    public Habr createMinimumPossibleHabr(StatusType status) {
        return habrRepository.save(new Habr().setStatusType(status));
    }

    public Participant createMinimumPossibleParticipant(String name) {
        return participantRepository.save(new Participant().setName(name));
    }

    public Role createMinimumPossibleRole(RoleType roleType) {
        return roleRepository.save(new Role().setRoleType(roleType));
    }

    public Post createMinimumPossiblePost(String title) {
        return postRepository.save(new Post().setTitle(title));
    }

}