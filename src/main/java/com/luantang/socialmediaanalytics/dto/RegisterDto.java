package com.luantang.socialmediaanalytics.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NonNull
public class RegisterDto {
    private String firstname;
    private String lastname;
    private String email;
    private String password;
}
