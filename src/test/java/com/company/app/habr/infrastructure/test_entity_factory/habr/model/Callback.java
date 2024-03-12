package com.company.app.habr.infrastructure.test_entity_factory.habr.model;

import com.company.app.habr.domain.entity.Habr;


@FunctionalInterface
public interface Callback {

    void modify(Habr habr);

}