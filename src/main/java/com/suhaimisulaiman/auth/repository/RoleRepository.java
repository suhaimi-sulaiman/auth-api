package com.suhaimisulaiman.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.suhaimisulaiman.auth.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    public Role findByName(String name);
}
