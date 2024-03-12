package com.company.app.habr.domain.initiator;

import javax.annotation.PostConstruct;

import java.util.Arrays;
import java.util.List;

import com.company.app.habr.domain.entity.Role;
import com.company.app.habr.domain.enums.RoleType;
import com.company.app.habr.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleInitiator {

    private final RoleRepository roleRepository;

    @PostConstruct
    void init() {
        List<Role> roles = Arrays.stream(RoleType.values())
            .map(roleType -> new Role().setRoleType(roleType))
            .toList();
        roleRepository.saveAll(roles);
    }

}