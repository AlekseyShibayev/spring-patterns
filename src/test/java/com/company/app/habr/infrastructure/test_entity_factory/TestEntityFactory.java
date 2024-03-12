package com.company.app.habr.infrastructure.test_entity_factory;

import com.company.app.habr.domain.enums.Status;
import com.company.app.habr.infrastructure.test_entity_factory.model.HabrPrototype;
import com.company.app.habr.infrastructure.test_entity_factory.service.HabrPrototypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class TestEntityFactory {

    private final HabrPrototypeService habrPrototypeService;

    public HabrPrototype habrBy(Status status) {
        return new HabrPrototype()
            .setStatus(status)
            .setHabrPrototypeService(habrPrototypeService);
    }

}