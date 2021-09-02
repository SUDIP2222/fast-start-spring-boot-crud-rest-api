package com.crud.faststartspringbootcrudrestapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "api/v1/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> findAllUsers() {
        return userService.findAllUser();
    }

    @PostMapping()
    @ResponseBody
    public void saveUser(
            @RequestParam(name = "email") String email,
            @RequestParam(name = "country") String country,
            @RequestParam(name = "firstName") String firstName,
            @RequestParam(name = "lastName") String lastName) {
        User user = User.builder()
                .country(country)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();
        userService.save(user);
    }

    @DeleteMapping
    public void deleteUser(@RequestParam(name = "id") Long id) {
        userService.deleteUserById(id);
    }

    @PutMapping
    public void updateUser(
            @RequestParam(name = "id") Long id,
            @RequestParam(name = "email", required = false) String email,
            @RequestParam(name = "country", required = false) String country,
            @RequestParam(name = "firstName", required = false) String firstName,
            @RequestParam(name = "lastName", required = false) String lastName) {

        User user = User.builder()
                .id(id)
                .country(country)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .build();

        userService.updateUserById(user);
    }
}
