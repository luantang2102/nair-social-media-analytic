package com.luantang.socialmediaanalytics.dashboard.dto;

import com.luantang.socialmediaanalytics.dashboard.model.data.FacebookPostPerformanceData;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FacebookPagePostPerformanceDto {
    private String pageId;
    private List<FacebookPostPerformanceData>  postPerformance;
}
