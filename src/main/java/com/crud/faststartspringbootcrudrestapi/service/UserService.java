package com.crud.faststartspringbootcrudrestapi.service;

import com.crud.faststartspringbootcrudrestapi.domain.User;

import java.util.List;

public interface UserService {

    public void saveAll(List<User> users);

    public List<User> findAllUser();

    public User getUserById(Long id);

    public void save(User user);

    public void deleteUserById(Long id);

    User findByEmail(String email);
}
