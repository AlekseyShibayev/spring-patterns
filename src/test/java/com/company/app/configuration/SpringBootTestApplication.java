package com.company.app.configuration;

import javax.annotation.PostConstruct;

import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.domain.repository.PostRepository;
import com.company.app.habr.domain.repository.ParticipantRepository;
import com.company.app.habr.domain.repository.RoleRepository;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import com.company.app.habr.infrastructure.test_entity_factory.TestEntityFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.context.TestPropertySource;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;


@Slf4j
@ExtendWith(OutputCaptureExtension.class)
@TestPropertySource("/test.properties")
@SpringBootTest(
    classes = {DbTestConfiguration.class}
    , webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Testcontainers(disabledWithoutDocker = false)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class SpringBootTestApplication {

    @Autowired
    protected SimpleCreator simpleCreator;
    @Autowired
    protected TransactionTemplate transactionTemplate;
    @Autowired
    protected TestEntityFactory testEntityFactory;
    /**
     * @Repository
     */
    @Autowired
    protected HabrRepository habrRepository;
    @Autowired
    protected ParticipantRepository participantRepository;
    @Autowired
    protected RoleRepository roleRepository;
    @Autowired
    protected PostRepository postRepository;
    /**
     * @MockBean
     */
    /**
     * @SpyBean
     */
    @PostConstruct
    void init() {
        log.debug("**********     run spring boot test context     **********");
    }

    @BeforeEach
    protected void doBeforeEach() {
    }

    @AfterEach
    protected void doAfterEach() {
        participantRepository.deleteAllInBatch();
        habrRepository.deleteAllInBatch();
    }

}