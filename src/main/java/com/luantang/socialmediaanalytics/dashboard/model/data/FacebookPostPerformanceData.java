package com.luantang.socialmediaanalytics.dashboard.model.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacebookPostPerformanceData {
    private String id;
    private String caption;
    private String createdTime;
    private int totalReaction;
    private int totalComment;
}
