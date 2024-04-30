package com.company.app.domain.initializer;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.company.app.domain.entity.Rank;
import com.company.app.domain.enums.RankType;
import com.company.app.domain.repository.RankRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;


@Slf4j
@Repository
@RequiredArgsConstructor
public class RankInitializer {

    private final RankRepository rankRepository;

    @PostConstruct
    void init() {
        List<Rank> ranks = Arrays.stream(RankType.values())
            .map(rankType -> new Rank().setRankType(rankType))
            .toList();
        rankRepository.saveAll(ranks);
    }

}