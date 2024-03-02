package com.luantang.socialmediaanalytics.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponseDto {
    private String accessToken;
    private String tokenType = "Bearer ";
}
