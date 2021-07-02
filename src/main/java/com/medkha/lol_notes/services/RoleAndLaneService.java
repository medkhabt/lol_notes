package com.medkha.lol_notes.services;

import java.util.Set;


public interface RoleAndLaneService {
    public Set<String> getAllRoles();
    public Set<String> getAllLanes();

    public Boolean isLane(String laneName);
    public Boolean isRole(String roleName);
}
