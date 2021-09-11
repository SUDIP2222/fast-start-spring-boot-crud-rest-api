package com.crud.faststartspringbootcrudrestapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.crud.faststartspringbootcrudrestapi.domain.Role;
import com.crud.faststartspringbootcrudrestapi.error.AuthenticationErrorException;
import com.crud.faststartspringbootcrudrestapi.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtUtils {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @Value("${jwt.token.key}")
    private String jwtTokenKey;
    @Value("${jwt.token.type}")
    private String jwtTokenType;
    @Value("${jwt.token.expireInMs}")
    private Long jwtTokenExpireInMs;
    @Value("${jwt.token.refresh.expireInMs}")
    private Long jwtRefreshTokenExpireInMs;

    public Authentication authenticateUser(String username, String password) throws AuthenticationErrorException {
        log.info("Init authenticate user");
        UsernamePasswordAuthenticationToken authenticationToken;
        try{
            authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            log.info("Authentication username: {}", authenticationToken.getName());
            return authenticationManager.authenticate(authenticationToken);
        }catch (Exception e) {
            log.error("authentication error : {}", e.getMessage());
            throw new AuthenticationErrorException(e.getMessage());
        }
    }

    public JwtTokenResponse getJwtTokenResponse(String username, String password) throws AuthenticationErrorException {
        Authentication authentication;
        try {
            authentication = authenticateUser(username, password);
        }catch (Exception e) {
            log.error("Authentication error:" + e.getMessage());
            throw new AuthenticationErrorException(e.getMessage());
        }

        return JwtTokenResponse.builder()
                .accessToken(generateAccessToken(authentication))
                .refreshToken(generateRefreshToken(authentication))
                .build();
    }

    public String generateAccessToken(Authentication authentication) {
        log.info("Authentication username: {}", authentication.getName());
        User user = (User) authentication.getPrincipal();
        log.info("User Principal username {}", user.getUsername());
        Algorithm algorithm = Algorithm.HMAC256(jwtTokenKey.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withClaim("roles", user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtTokenExpireInMs))
                .sign(algorithm);
    }

    public String generateRefreshToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Algorithm algorithm = Algorithm.HMAC256(jwtTokenKey.getBytes());
        return JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtRefreshTokenExpireInMs))
                .sign(algorithm);
    }

    public void validateJwtToken(String authorizationHeader) {
        if (authorizationHeader.startsWith(jwtTokenType)) {
            String token = authorizationHeader.substring(jwtTokenType.length());
            Algorithm algorithm = Algorithm.HMAC256(jwtTokenKey.getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(token);
            String username = decodedJWT.getSubject();
            String[] roles = decodedJWT.getClaim("roles").asArray(String.class);
            Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
            Arrays.stream(roles).forEach(role -> authorities.add(new SimpleGrantedAuthority(role)));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
    }

    public JwtTokenResponse validateRefreshJwtToken(HttpServletRequest request) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        String refreshToken = authorizationHeader.substring(jwtTokenType.length());

        Algorithm algorithm = Algorithm.HMAC256(jwtTokenKey.getBytes());
        JWTVerifier verifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = verifier.verify(refreshToken);
        String username = decodedJWT.getSubject();
        com.crud.faststartspringbootcrudrestapi.domain.User user = userService.findByEmail(username);

        String accessToken = JWT.create()
                .withSubject(user.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + jwtTokenExpireInMs))
                .withIssuer(request.getRequestURL().toString())
                .withClaim("roles", user.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                .sign(algorithm);

        return JwtTokenResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
