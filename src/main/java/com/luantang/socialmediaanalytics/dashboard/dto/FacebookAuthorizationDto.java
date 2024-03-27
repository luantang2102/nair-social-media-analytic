package com.luantang.socialmediaanalytics.dashboard.dto;

import lombok.*;
import org.springframework.lang.NonNull;

@AllArgsConstructor
@Getter
@Setter
public class FacebookAuthorizationDto {
    @NonNull
    private String redirectUrl;
    private String authorizationCode;
}
