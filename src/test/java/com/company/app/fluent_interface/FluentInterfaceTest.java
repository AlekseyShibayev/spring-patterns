package com.company.app.fluent_interface;

import com.company.app.configuration.SpringBootTestApplication;
import com.company.app.domain.entity.Participant;
import com.company.app.domain.entity.Post;
import com.company.app.domain.entity.Rank;
import com.company.app.domain.enums.RankType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


class FluentInterfaceTest extends SpringBootTestApplication {

    @Test
    void step_1() {
        Participant participant = createParticipantManualAndImperative("name", "post", RankType.WRITER);
        Assertions.assertEquals(RankType.WRITER, participant.getRank().getRankType());
    }

    private Participant createParticipantManualAndImperative(String name, String title, RankType rankType) {
        Rank rank = rankRepository.findByRankType(rankType);

        Participant participant = new Participant()
            .setRank(rank)
            .setName(name);
        participantRepository.save(participant);

        Post post = new Post()
            .setTitle(title);
        Post persist = postRepository.save(post);

        participant.getPosts().add(post);
        participantRepository.save(participant);

        persist.setParticipant(participant);
        postRepository.save(persist);

        return participant;
    }

    /**
     *   public Habr createMinimumPosibleHabr(Status status) {
     *         return habrRepository.save(new Habr()
     *             .setStatus(status));
     *     }
     *
     *     public void addParticipant(Habr habr, String name) {
     *         Participant user = new Participant()
     *             .setName(name)
     *             .setHabr(habr);
     *         participantRepository.save(user);
     *         habr.getUsers().add(user);
     *         habrRepository.save(habr);
     *     }
     *
     */

}
