package com.crud.faststartspringbootcrudrestapi.controller;

import com.crud.faststartspringbootcrudrestapi.ResponseMessage;
import com.crud.faststartspringbootcrudrestapi.service.UserService;
import com.crud.faststartspringbootcrudrestapi.domain.User;
import com.crud.faststartspringbootcrudrestapi.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/user")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping
    public List<User> getAll() {
        return userService.findAllUser();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {

        User user = userService.getUserById(id);
        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .country(user.getCountry())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
        return ResponseEntity.ok(userDto);
    }

    @PostMapping
    public ResponseEntity<?> saveUser(@RequestBody UserDto userDto) {
        User user = User
                .builder()
                .lastName(userDto.getLastName())
                .firstName(userDto.getFirstName())
                .email(userDto.getEmail())
                .country(userDto.getCountry())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .build();

        userService.save(user);
        return ResponseEntity.ok(new ResponseMessage("User with email: " + user.getEmail() + " has been saved" ));
    }

    @DeleteMapping
    public void deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUserById(id);
    }

}
