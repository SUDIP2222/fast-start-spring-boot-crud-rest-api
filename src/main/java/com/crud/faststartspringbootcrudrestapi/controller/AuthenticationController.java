package com.crud.faststartspringbootcrudrestapi.controller;

import com.crud.faststartspringbootcrudrestapi.ResponseMessage;
import com.crud.faststartspringbootcrudrestapi.domain.User;
import com.crud.faststartspringbootcrudrestapi.domain.dto.LoginDto;
import com.crud.faststartspringbootcrudrestapi.domain.dto.RegistrationDto;
import com.crud.faststartspringbootcrudrestapi.error.AuthenticationErrorException;
import com.crud.faststartspringbootcrudrestapi.security.JwtTokenResponse;
import com.crud.faststartspringbootcrudrestapi.security.JwtUtils;
import com.crud.faststartspringbootcrudrestapi.service.RoleService;
import com.crud.faststartspringbootcrudrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final JwtUtils jwtUtils;
    private final UserService userService;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        JwtTokenResponse jwtTokenResponse;
        try {
            jwtTokenResponse = jwtUtils.getJwtTokenResponse(loginDto.getUsername(), loginDto.getPassword());
        }catch (AuthenticationErrorException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Authentication error: " + e.getMessage()));
        }
        return ResponseEntity.ok().body(jwtTokenResponse);
    }

    @GetMapping("/refresh")
    public ResponseEntity<?> refreshToken(HttpServletRequest request) {
        try {
            JwtTokenResponse jwtTokenResponse = jwtUtils.validateRefreshJwtToken(request);
            return ResponseEntity.ok().body(jwtTokenResponse);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body(new ResponseMessage(e.getMessage()));
        }
    }

    @PostMapping("/registration")
    public ResponseEntity<?>registration(@RequestBody RegistrationDto registrationDto) {
        User user = User.builder()
                .email(registrationDto.getUsername())
                .password(passwordEncoder.encode(registrationDto.getPassword()))
                .roles(new ArrayList<>())
                .build();

        user.getRoles().add(roleService.findByName("USER"));
        log.info("User username : {}", user.getUsername());
        log.info("User password : {}", user.getPassword());
        log.info("User roles : {}", user.getRoles().toString());
        userService.save(user);

        JwtTokenResponse jwtTokenResponse;
        try {
            jwtTokenResponse = jwtUtils.getJwtTokenResponse(registrationDto.getUsername(), registrationDto.getPassword());
        }catch (AuthenticationErrorException e) {
            return ResponseEntity.badRequest().body(new ResponseMessage("Authentication error: " + e.getMessage()));
        }

        log.info("token access: {}", jwtTokenResponse.getAccessToken());
        return ResponseEntity.ok().body(jwtTokenResponse);
    }

}
