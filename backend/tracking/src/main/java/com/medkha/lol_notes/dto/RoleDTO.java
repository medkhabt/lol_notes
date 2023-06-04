package com.medkha.lol_notes.dto;

import java.util.Objects;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RoleDTO implements DeathFilterOption {
    private static Logger log = LoggerFactory.getLogger(RoleDTO.class);
    private String roleName;

    public RoleDTO(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public Predicate<DeathDTO> getPredicate() {
        return (DeathDTO death) -> {
            log.info("getDeathFilterByRolePredicate: Filter by Role with a name: {}", this.getRoleName());
            Boolean result = death.getGame().getRoleName().equalsIgnoreCase(this.getRoleName());
            log.info("Death with id: {} has Role with name: {} equals Filter Role with id:{} ? {} ",
                    death.getId(), death.getGame().getRoleName(), this.getRoleName(), result);
            return result;
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleDTO roleDTO = (RoleDTO) o;
        return this.roleName.equalsIgnoreCase(roleDTO.roleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleName);
    }
}
