package com.company.app.fluent_interface.step_1;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.domain.entity.Participant;
import com.company.app.domain.entity.Post;
import com.company.app.domain.entity.Rank;
import com.company.app.domain.enums.RankType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class ManualImperativeCreatingExampleTest extends SpringBootTestApplication {

    @Test
    void test() {
        Participant participant = createParticipantManualAndImperative("name", "post", RankType.WRITER);

        Assertions.assertEquals(RankType.WRITER, participant.getRank().getRankType());
    }

    private Participant createParticipantManualAndImperative(String participantName, String postTitle, RankType rankType) {
        Rank rank = rankRepository.findByRankType(rankType);

        Participant participant = new Participant()
            .setRank(rank)
            .setName(participantName);
        participantRepository.save(participant);

        Post post = new Post()
            .setTitle(postTitle);
        Post persist = postRepository.save(post);

        participant.getPosts().add(post);
        participantRepository.save(participant);

        persist.setParticipant(participant);
        postRepository.save(persist);

        return participant;
    }

}