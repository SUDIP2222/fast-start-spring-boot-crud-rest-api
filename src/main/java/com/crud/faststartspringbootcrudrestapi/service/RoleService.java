package com.crud.faststartspringbootcrudrestapi.service;

import com.crud.faststartspringbootcrudrestapi.domain.Role;

import java.util.List;

public interface RoleService {

    Role findByName(String name);
    void save(Role role);
    void saveAll(List<Role> roles);
    List<Role> findAll();
    void addRoleToUser(String userEmail, String roleName);
}
