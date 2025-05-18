package com.sylfie.service;

import com.sylfie.model.entity.Role;
import com.sylfie.repository.RoleRepository;
import jakarta.persistence.EntityNotFoundException;
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

    public Role getById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Role not found with id: " + id));
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
    public void delete(Long id) {
        Role role = getById(id);
        roleRepository.delete(role);
    }

    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

}