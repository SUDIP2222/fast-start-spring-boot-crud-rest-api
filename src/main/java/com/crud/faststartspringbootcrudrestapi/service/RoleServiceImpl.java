package com.crud.faststartspringbootcrudrestapi.service;

import com.crud.faststartspringbootcrudrestapi.domain.Role;
import com.crud.faststartspringbootcrudrestapi.domain.User;
import com.crud.faststartspringbootcrudrestapi.repository.RoleRepository;
import com.crud.faststartspringbootcrudrestapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class RoleServiceImpl implements RoleService{

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public void save(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void saveAll(List<Role> roles) {
        roleRepository.saveAll(roles);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }

    @Override
    public void addRoleToUser(String userEmail, String roleName) {
        User user = userRepository.findByEmail(userEmail);
        Role role = roleRepository.findByName(roleName);
        user.getRoles().add(role);
//        userRepository.save(user);
    }
}
