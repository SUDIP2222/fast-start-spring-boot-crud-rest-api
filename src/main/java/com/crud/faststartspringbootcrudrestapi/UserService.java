package com.crud.faststartspringbootcrudrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveAll(List<User> users) {
        userRepository.saveAll(users);
    }

    public List<User> findAllUser() {
        return userRepository.findAll();
    }

    public void save(User user) {
        if(userRepository.existsByEmail(user.getEmail())){
                throw new IllegalStateException("User with email " + user.getEmail() + " already exist");
        }
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new IllegalStateException("User not exist");
        }
        userRepository.deleteById(id);
    }

    public void updateUserById(User user) {
        User userOld = userRepository.findById(user.getId()).orElseThrow();
        User userUpdated = User.builder()
                .id(userOld.getId())
                .country(user.getCountry() == null ? userOld.getCountry() : user.getCountry())
                .email(user.getEmail() == null ? userOld.getEmail() : user.getEmail())
                .firstName(user.getFirstName() == null ? userOld.getFirstName() : user.getFirstName())
                .lastName(user.getLastName() == null ? userOld.getLastName() : user.getLastName())
                .build();

        userRepository.save(userUpdated);
    }
}
