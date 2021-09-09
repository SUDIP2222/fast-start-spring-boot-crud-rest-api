package com.crud.faststartspringbootcrudrestapi.domain.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDto {

    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    public String password;
    private String country;
}
