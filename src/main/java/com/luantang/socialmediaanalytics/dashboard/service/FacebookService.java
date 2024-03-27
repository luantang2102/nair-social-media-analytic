package com.luantang.socialmediaanalytics.dashboard.service;

import com.luantang.socialmediaanalytics.dashboard.dto.DashboardMessageDto;
import com.luantang.socialmediaanalytics.dashboard.dto.FacebookAuthorizationDto;
import com.luantang.socialmediaanalytics.dashboard.dto.FacebookPagePostPerformanceDto;
import com.luantang.socialmediaanalytics.dashboard.model.FacebookAuthDetails;

import java.util.List;

public interface FacebookService {
    DashboardMessageDto createAuthorizationUrl(FacebookAuthorizationDto authorizationDto);

    DashboardMessageDto getAccess(FacebookAuthorizationDto authorizationDto);

    List<FacebookPagePostPerformanceDto> getPostPerformances(String startDate, String endDate);

    FacebookAuthDetails saveAuthDetails(FacebookAuthDetails facebookAuthDetails);

    FacebookAuthDetails getAuthDetailsByAppUserEmail(String email);

}
