package com.crud.faststartspringbootcrudrestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication()
public class FastStartSpringBootCrudRestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(FastStartSpringBootCrudRestApiApplication.class, args);
    }
}