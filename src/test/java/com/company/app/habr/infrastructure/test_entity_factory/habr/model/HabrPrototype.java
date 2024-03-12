package com.company.app.habr.infrastructure.test_entity_factory.habr.model;

import java.util.ArrayList;
import java.util.List;

import com.company.app.habr.domain.entity.Habr;
import com.company.app.habr.domain.enums.StatusType;
import com.company.app.habr.infrastructure.test_entity_factory.habr.service.HabrPrototypeService;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class HabrPrototype {

    private StatusType status;
    private HabrPrototypeService habrPrototypeService;
    private List<Callback> chain = new ArrayList<>();
    private int amount;

    /**
     * terminal operations
     */
    public Habr createOne() {
        this.amount = 1;
        return habrPrototypeService.create(this).get(0);
    }

    public List<Habr> createMany(int amount) {
        this.amount = amount;
        return habrPrototypeService.create(this);
    }

    /**
     * intermediate operations
     */
    public HabrPrototype with(Callback enrichCallback) {
        chain.add(enrichCallback);
        return this;
    }

    public HabrPrototype addParticipant(String name) {
        chain.add(habr -> habrPrototypeService.addParticipant(habr, name));
        return this;
    }

}