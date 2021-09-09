package com.crud.faststartspringbootcrudrestapi.domain.dto;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

@ToString
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
public class LoginDto {

    private String username;
    private String password;
}
