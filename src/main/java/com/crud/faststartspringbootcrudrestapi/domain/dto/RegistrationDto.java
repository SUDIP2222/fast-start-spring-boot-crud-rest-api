package com.crud.faststartspringbootcrudrestapi.domain.dto;

import lombok.*;

@Getter @Setter @Builder @AllArgsConstructor @NoArgsConstructor @ToString
public class RegistrationDto {

    private String username;
    private String password;
    private String rePassword;
    private boolean isAccepted;
}
