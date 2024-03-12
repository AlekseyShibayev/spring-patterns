package com.company.app.habr.infrastructure.test_entity_factory.habr.service;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.StatusType;
import com.company.app.habr.domain.repository.HabrRepository;
import com.company.app.habr.infrastructure.simple_creator.SimpleCreator;
import com.company.app.habr.infrastructure.test_entity_factory.habr.model.Callback;
import com.company.app.habr.infrastructure.test_entity_factory.habr.model.HabrPrototype;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Service
@RequiredArgsConstructor
public class HabrPrototypeFinisher {

    private final SimpleCreator simpleCreator;
    private final HabrRepository habrRepository;

    @Transactional
    public List<Habr> create(HabrPrototype prototype) {
        List<Habr> result = new ArrayList<>();
        for (int i = 0; i < prototype.getAmount(); i++) {
            result.add(createOne(prototype));
        }
        return result;
    }

    private Habr createOne(HabrPrototype prototype) {
        StatusType status = prototype.getStatus();
        Habr minimumPosibleHabr = simpleCreator.createMinimumPossibleHabr(status);

        List<Callback> chain = prototype.getChain();
        chain.forEach(callback -> callback.modify(minimumPosibleHabr));

        return habrRepository.save(minimumPosibleHabr);
    }

}