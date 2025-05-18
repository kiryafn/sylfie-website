package com.sylfie.repository;

import com.sylfie.model.entity.Role;
import com.sylfie.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    List<Role> findAll();

    Role findByName(String name);
}
