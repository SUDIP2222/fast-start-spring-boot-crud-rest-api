package com.crud.faststartspringbootcrudrestapi;

import com.crud.faststartspringbootcrudrestapi.domain.Role;
import com.crud.faststartspringbootcrudrestapi.domain.User;
import com.crud.faststartspringbootcrudrestapi.service.RoleService;
import com.crud.faststartspringbootcrudrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserInit implements CommandLineRunner{

    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        Role user = Role.builder().name("USER").build();
        Role admin = Role.builder().name("ADMIN").build();
        Role moderator = Role.builder().name("MODERATOR").build();
        Role seo = Role.builder().name("SEO").build();

        roleService.saveAll(List.of(user, admin, moderator, seo));

        User user1 = User.builder()
                .country("USA")
                .email("user1@email.com")
                .firstName("userName1")
                .lastName("userlast1")
                .password(passwordEncoder.encode("password1"))
                .roles(List.of(roleService.findByName("ADMIN")))
                .build();

        User user2 = User.builder()
                .country("France")
                .email("user2@email.com")
                .firstName("userName2")
                .lastName("userlast2")
                .password(passwordEncoder.encode("password2"))
                .roles(List.of(roleService.findByName("ADMIN"), roleService.findByName("USER")))
                .build();

        User user3 = User.builder()
                .country("Germany")
                .email("user3@email.com")
                .firstName("Crown")
                .lastName("Dorman")
                .password(passwordEncoder.encode("password3"))
                .roles(List.of(roleService.findByName("MODERATOR")))
                .build();

        User user4 = User.builder()
                .country("Italy")
                .email("user4@email.com")
                .firstName("Boriman")
                .lastName("Calatane")
                .password(passwordEncoder.encode("password4"))
                .roles(List.of(roleService.findByName("SEO")))
                .build();

        userService.saveAll(List.of(user1, user2, user3, user4));
    }
}
