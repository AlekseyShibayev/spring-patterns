package com.company.app.fluent_interface.step_2;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.domain.entity.Participant;
import com.company.app.domain.enums.RankType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class FluentInterfaceExampleTest extends SpringBootTestApplication {

    @Test
    void create_one_minimum_possible_participant_test() {
        Participant participant = testEntityFactory.participantBy(RankType.WRITER)
            .createOne();

        Assertions.assertEquals(RankType.WRITER, participant.getRank().getRankType());
    }

    @Test
    void create_one_minimum_possible_participant_with_custom_name_test() {
        Participant participant = testEntityFactory.participantBy(RankType.WRITER)
            .withName("customName")
            .createOne();

        Assertions.assertEquals(RankType.WRITER, participant.getRank().getRankType());
        Assertions.assertEquals("customName", participant.getName());
    }

    @Test
    void create_one_minimum_possible_participant_with_one_post_test() {
        Participant participant = testEntityFactory.participantBy(RankType.WRITER)
            .withPost("customTitle")
            .createOne();

        Assertions.assertEquals(RankType.WRITER, participant.getRank().getRankType());
        Assertions.assertEquals(1, participant.getPosts().size());
        Assertions.assertEquals("customTitle", participant.getPosts().get(0).getTitle());
    }

    @Test
    void create_one_minimum_possible_participant_with_custom_name_and_many_posts_test() {
        Participant participant = testEntityFactory.participantBy(RankType.WRITER)
            .withName("customName")
            .withPost("post_1")
            .withPost("post_2")
            .withPost("post_3")
            .createOne();

        Assertions.assertEquals(RankType.WRITER, participant.getRank().getRankType());
        Assertions.assertEquals("customName", participant.getName());
        Assertions.assertEquals(3, participant.getPosts().size());
    }

}