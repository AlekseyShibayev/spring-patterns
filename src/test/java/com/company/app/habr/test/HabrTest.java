package com.company.app.habr.test;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.entity.HabrUser;
import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.HabrUserRepository;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.TestEntityFactoryWithBeansBag;
import com.company.app.habr.infrastructure.test_entity_factory_with_beans_bag.service.TestEntityFactoryBeansBag;
import com.company.app.habr.infrastructure.test_entity_factory_with_prototype.TestEntityFactoryWithPrototype;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


class HabrTest extends SpringBootTestApplication {

    private static final String NAME = "name";
    private static final String NAME2 = "name2";

    @Autowired
    private SimpleCreator simpleCreator;
    @Autowired
    private TestEntityFactoryWithBeansBag testEntityFactoryWithBeansBag;
    @Autowired
    private TestEntityFactoryWithPrototype testEntityFactoryWithPrototype;
    @Autowired
    private HabrRepository habrRepository;
    @Autowired
    private HabrUserRepository habrUserRepository;

    @AfterEach
    void doAfterEach_() {
        super.doAfterEach();
        habrUserRepository.deleteAllInBatch();
        habrRepository.deleteAllInBatch();
    }

    @Test
    void step_1_simpleCreator_test() {
        Habr habr = transactionTemplate.execute(status -> {
            Habr save = simpleCreator.createMinimumPosibleHabr(Status.ON);
            simpleCreator.addHabrUser(save, NAME);
            return save;
        });

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertEquals(NAME, habr.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

    @Test
    void step_2_testEntityFactory_test_with_lambda() {
        Habr habr = testEntityFactoryWithBeansBag.habrBy(Status.ON)
            .with(habrBuilderContext -> {
                Habr minimumPosibleHabr = habrBuilderContext.getHabr();
                TestEntityFactoryBeansBag beansBag = habrBuilderContext.getBeansBag();
                HabrUserRepository habrUserRepository = beansBag.getHabrUserRepository();
                HabrRepository habrRepository = beansBag.getHabrRepository();

                HabrUser user = new HabrUser().setHabr(minimumPosibleHabr).setName(NAME);
                habrUserRepository.save(user);
                minimumPosibleHabr.getHabrUsers().add(user);
                habrRepository.save(minimumPosibleHabr);
            })
            .createOne();

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertEquals(NAME, habr.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

    @Test
    void step_4_testEntityFactory_test_with_impl_as_method() {
        Habr habr = testEntityFactoryWithBeansBag.habrBy(Status.ON)
            .addHabrUser(NAME)
            .createOne();

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertEquals(NAME, habr.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

    @Test
    void step_5_testPrototypeFactoryFacade_test() {
        Habr habr = testEntityFactoryWithPrototype.habrBy(Status.ON)
            .addHabrUser(NAME)
            .createOne();

        Assertions.assertEquals(habr.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(1, habr.getHabrUsers().size());
        Assertions.assertEquals(NAME, habr.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(habr.getHabrUsers().get(0).getId());
    }

    @Test
    void step_6_testPrototypeFactoryFacade_test() {
        Habr on = testEntityFactoryWithPrototype.habrBy(Status.ON)
            .addHabrUser(NAME)
            .addHabrUser(NAME2)
            .createOne();
        Habr off = testEntityFactoryWithPrototype.habrBy(Status.OFF)
            .addHabrUser(NAME2)
            .createOne();

        Assertions.assertEquals(on.getStatus().name(), Status.ON.name());
        Assertions.assertEquals(2, on.getHabrUsers().size());
        Assertions.assertNotNull(on.getHabrUsers().get(0).getId());

        Assertions.assertEquals(off.getStatus().name(), Status.OFF.name());
        Assertions.assertEquals(1, off.getHabrUsers().size());
        Assertions.assertEquals(NAME2, off.getHabrUsers().get(0).getName());
        Assertions.assertNotNull(off.getHabrUsers().get(0).getId());
    }

}