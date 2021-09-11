package com.crud.faststartspringbootcrudrestapi.security;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtTokenResponse {

    private String accessToken;
    private String refreshToken;

}
