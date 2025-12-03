package com.sylfie.repository;

import com.sylfie.model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();

    Optional<Role> findByName(String name);
}
