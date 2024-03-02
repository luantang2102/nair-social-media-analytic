package com.luantang.socialmediaanalytics.dto;

import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {
    private String message;
    private Date timestamp;
}
