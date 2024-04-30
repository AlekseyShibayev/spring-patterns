package com.company.app.domain.repository;

import com.company.app.domain.entity.Rank;
import com.company.app.domain.enums.RankType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RankRepository extends JpaRepository<Rank, Long> {

    Rank findByRankType(RankType rankType);

}