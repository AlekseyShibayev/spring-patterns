package com.company.app.habr.test;

import javax.persistence.EntityNotFoundException;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.Participant;
import com.company.app.habr.domain.entity.Post;
import com.company.app.habr.domain.entity.Role;
import com.company.app.habr.domain.enums.RoleType;
import com.company.app.habr.domain.enums.StatusType;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class HabrTest extends SpringBootTestApplication {

    private static final String NAME = "Ivan";
    private static final String NAME2 = "Igor";

    @Test
    void step_1_simpleCreator_test() {
        Habr habr = transactionTemplate.execute(status -> {
            Habr minimumPossibleHabr = simpleCreator.createMinimumPossibleHabr(StatusType.ON);
            Post minimumPossiblePost = simpleCreator.createMinimumPossiblePost("title");
            Role reader = roleRepository.findByRoleType(RoleType.READER).orElseThrow(EntityNotFoundException::new);
            Participant participant = simpleCreator.createMinimumPossibleParticipant(NAME);

            participant.setHabr(minimumPossibleHabr);
            participant.getPosts().add(minimumPossiblePost);
            participant.setRole(reader);
            participantRepository.save(participant);

            minimumPossibleHabr.getParticipants().add(participant);
            return habrRepository.save(minimumPossibleHabr);
        });

        Assertions.assertEquals(habr.getStatusType().name(), StatusType.ON.name());
        Assertions.assertEquals(1, habr.getParticipants().size());

        Participant participant = habr.getParticipants().get(0);
        Assertions.assertEquals(NAME, participant.getName());
        Assertions.assertEquals(RoleType.READER, participant.getRole().getRoleType());
        Assertions.assertNotNull(habr.getParticipants().get(0).getId());
    }

    @Test
    void step_5_testPrototypeFactoryFacade_test() {
        Habr habr = testEntityFactory.habrBy(StatusType.ON)
            .addParticipant(NAME)
            .createOne();

        Assertions.assertEquals(habr.getStatusType().name(), StatusType.ON.name());
        Assertions.assertEquals(1, habr.getParticipants().size());
        Assertions.assertEquals(NAME, habr.getParticipants().get(0).getName());
        Assertions.assertNotNull(habr.getParticipants().get(0).getId());
    }

    @Test
    void step_6_testPrototypeFactoryFacade_test() {
        Habr on = testEntityFactory.habrBy(StatusType.ON)
            .addParticipant(NAME)
            .addParticipant(NAME2)
            .createOne();
        Habr off = testEntityFactory.habrBy(StatusType.OFF)
            .addParticipant(NAME2)
            .createOne();

        Assertions.assertEquals(on.getStatusType().name(), StatusType.ON.name());
        Assertions.assertEquals(2, on.getParticipants().size());
        Assertions.assertNotNull(on.getParticipants().get(0).getId());

        Assertions.assertEquals(off.getStatusType().name(), StatusType.OFF.name());
        Assertions.assertEquals(1, off.getParticipants().size());
        Assertions.assertEquals(NAME2, off.getParticipants().get(0).getName());
        Assertions.assertNotNull(off.getParticipants().get(0).getId());
    }

}