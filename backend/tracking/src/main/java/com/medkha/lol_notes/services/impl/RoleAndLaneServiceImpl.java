package com.medkha.lol_notes.services.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.medkha.lol_notes.services.RoleAndLaneService;
import com.merakianalytics.orianna.types.common.Lane;
import com.merakianalytics.orianna.types.common.Role;

@Service
public class RoleAndLaneServiceImpl implements RoleAndLaneService {
    private static Logger log = LoggerFactory.getLogger(RoleAndLaneServiceImpl.class);

    @PostConstruct
    public void init() {
        log.info("init: Start initialization of RoleAndLaneService...");
        getAllRoles();
        log.info("init: Got all roles successfully");
        getAllLanes();
        log.info("init: Got all lanes successfully");
    }

    @Override
    @Cacheable("roles")
    public Set<String> getAllRoles() {
        Set<String> allRoles = Arrays.stream(Role.values()).map(Role::toString).map(String::toUpperCase).collect(Collectors.toSet());
        log.info("getAllRoles: roles: [{}].",  allRoles);
        return allRoles;
    }

    @Override
    @Cacheable("lanes")
    public Set<String> getAllLanes() {
        Set<String> allLanes = Arrays.stream(Lane.values())
                                        .map(Lane::toString)
                                        .map(String::toUpperCase)
                                .collect(Collectors.toSet());
        log.info("getAllLanes: lanes: [{}]", allLanes);
        return allLanes;
    }

    @Override
    public Boolean isLane(String laneName) {
        return getAllLanes().contains(laneName.toUpperCase());
    }

    @Override
    public Boolean isRole(String roleName) {
        return getAllRoles().contains(roleName.toUpperCase());
    }


}
