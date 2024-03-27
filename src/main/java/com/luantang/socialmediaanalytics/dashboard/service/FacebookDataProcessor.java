package com.luantang.socialmediaanalytics.dashboard.service;

import com.luantang.socialmediaanalytics.dashboard.dto.response.page.feed.Root;
import com.luantang.socialmediaanalytics.dashboard.model.data.FacebookPostPerformanceData;

import java.util.List;

public interface FacebookDataProcessor {

    List<FacebookPostPerformanceData> matchPostPerformanceData(Root responseEntity);
}
