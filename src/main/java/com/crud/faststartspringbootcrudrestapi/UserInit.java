package com.crud.faststartspringbootcrudrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserInit implements CommandLineRunner{

    private final UserService userService;

    @Autowired
    public UserInit(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String... args) throws Exception {
        User user1 = User.builder()
                .country("USA")
                .email("user1@email.com")
                .firstName("userName1")
                .lastName("userlast1")
                .build();

        User user2 = User.builder()
                .country("France")
                .email("user2@email.com")
                .firstName("userName2")
                .lastName("userlast2")
                .build();

        userService.saveAll(List.of(user1, user2));
    }
}
