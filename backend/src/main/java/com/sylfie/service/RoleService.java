package com.sylfie.service;

import com.sylfie.exception.RoleNotFoundException;
import com.sylfie.model.Role;
import com.sylfie.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Transactional
    public Role create(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public Role update(Role role) {
        return roleRepository.save(role);
    }

    @Transactional
    public void delete(String name) {
        Role role = getByName(name);
        roleRepository.delete(role);
    }

    public Role getByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new RoleNotFoundException("Role with name" + name + "does not exists"));
    }

}