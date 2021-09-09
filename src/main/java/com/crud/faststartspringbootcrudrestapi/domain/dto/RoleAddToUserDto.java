package com.crud.faststartspringbootcrudrestapi.domain.dto;

import lombok.*;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleAddToUserDto {

    private String userEmail;
    private String roleName;
}
