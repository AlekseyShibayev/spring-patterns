package com.company.app.habr.test;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class HabrTest extends SpringBootTestApplication {

    private static final String NAME = "name";
    private static final String NAME2 = "name2";

    @Autowired
    private SimpleCreator simpleCreator;

    @Test
    void step_1_simpleCreator_test() {
        Habr habr = transactionTemplate.execute(status -> {
            Habr save = simpleCreator.createMinimumPosibleHabr(Status.ON);
            simpleCreator.addParticipant(save, NAME);
            return save;
        });

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getUsers().size());
        Assertions.assertEquals(NAME, habr.getUsers().get(0).getName());
        Assertions.assertNotNull(habr.getUsers().get(0).getId());
    }

    @Test
    void step_5_testPrototypeFactoryFacade_test() {
        Habr habr = testEntityFactory.habrBy(Status.ON)
            .addParticipant(NAME)
            .createOne();

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getUsers().size());
        Assertions.assertEquals(NAME, habr.getUsers().get(0).getName());
        Assertions.assertNotNull(habr.getUsers().get(0).getId());
    }

    @Test
    void step_6_testPrototypeFactoryFacade_test() {
        Habr on = testEntityFactory.habrBy(Status.ON)
            .addParticipant(NAME)
            .addParticipant(NAME2)
            .createOne();
        Habr off = testEntityFactory.habrBy(Status.OFF)
            .addParticipant(NAME2)
            .createOne();

        Assertions.assertEquals(on.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(2, on.getUsers().size());
        Assertions.assertNotNull(on.getUsers().get(0).getId());

        Assertions.assertEquals(off.getStatus().name(), Status.OFF.name());
        Assertions.assertEquals(1, off.getUsers().size());
        Assertions.assertEquals(NAME2, off.getUsers().get(0).getName());
        Assertions.assertNotNull(off.getUsers().get(0).getId());
    }

}