package com.company.app.habr.infrastructure.test_entity_factory_with_prototype.model;

import java.util.function.Consumer;

import com.company.app.habr.domain.entity.Habr;


@FunctionalInterface
public interface EnrichCallback extends Consumer<Habr> {
}