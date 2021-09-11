package com.crud.faststartspringbootcrudrestapi.controller;

import com.crud.faststartspringbootcrudrestapi.ResponseMessage;
import com.crud.faststartspringbootcrudrestapi.domain.dto.LoginDto;
import com.crud.faststartspringbootcrudrestapi.security.JwtTokenResponse;
import com.crud.faststartspringbootcrudrestapi.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/oauth")
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController {

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = jwtUtils.authenticateUser(loginDto.getUsername(), loginDto.getPassword());
        JwtTokenResponse jwtTokenResponse = JwtTokenResponse.builder()
                .accessToken(jwtUtils.generateAccessToken(authentication))
                .refreshToken(jwtUtils.generateRefreshToken(authentication))
                .build();

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
}
