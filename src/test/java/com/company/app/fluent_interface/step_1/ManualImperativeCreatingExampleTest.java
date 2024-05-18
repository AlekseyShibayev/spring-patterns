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
        Rank rank = rankRepository.findByRankType(rankType); // вытащить Rank из бд

        Participant participant = new Participant() // создать новый Participant
            .setRank(rank)
            .setName(participantName);
        participantRepository.save(participant); // заперсистить

        Post post = new Post() // создать новый Post
            .setTitle(postTitle);
        postRepository.save(post); // заперсистить

        // переженить Participant и Post
        participant.getPosts().add(post);
        participantRepository.save(participant);

        post.setParticipant(participant);
        postRepository.save(post);

        return participant;
    }

}