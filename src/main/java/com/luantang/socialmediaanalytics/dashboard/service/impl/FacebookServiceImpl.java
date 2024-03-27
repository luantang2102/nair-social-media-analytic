package com.luantang.socialmediaanalytics.dashboard.service.impl;


import com.luantang.socialmediaanalytics.dashboard.constant.FacebookConstant;
import com.luantang.socialmediaanalytics.dashboard.dto.DashboardMessageDto;
import com.luantang.socialmediaanalytics.dashboard.dto.FacebookAuthorizationDto;
import com.luantang.socialmediaanalytics.dashboard.dto.FacebookPagePostPerformanceDto;
import com.luantang.socialmediaanalytics.dashboard.dto.response.user.account.Datum;
import com.luantang.socialmediaanalytics.dashboard.dto.response.user.me.Root;
import com.luantang.socialmediaanalytics.dashboard.exceptions.AuthNotFoundException;
import com.luantang.socialmediaanalytics.dashboard.exceptions.InvalidAuthorizationCodeException;
import com.luantang.socialmediaanalytics.dashboard.exceptions.NullTokenException;
import com.luantang.socialmediaanalytics.dashboard.model.FacebookAuthDetails;
import com.luantang.socialmediaanalytics.dashboard.model.data.FacebookPostPerformanceData;
import com.luantang.socialmediaanalytics.dashboard.repository.FacebookAuthRepository;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookDataProcessor;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class FacebookServiceImpl implements FacebookService {
    private final FacebookAuthRepository facebookAuthRepository;
    private final RestTemplate restTemplate;
    private final FacebookDataProcessor facebookDataProcessor;


    @Autowired
    public FacebookServiceImpl(FacebookAuthRepository facebookAuthRepository, RestTemplate restTemplate, FacebookDataProcessor facebookDataProcessor) {
        this.facebookAuthRepository = facebookAuthRepository;
        this.restTemplate = restTemplate;
        this.facebookDataProcessor = facebookDataProcessor;
    }

    @Override
    public DashboardMessageDto createAuthorizationUrl(FacebookAuthorizationDto authorizationDto) {
        String url = FacebookConstant.FACEBOOK_OAUTH_DIALOG_URL +
                "?client_id=" + FacebookConstant.FACEBOOK_APP_ID +
                "&redirect_uri=" + authorizationDto.getRedirectUrl() +
                "&scope=" + "pages_read_engagement,read_insights,pages_read_user_content,pages_show_list ";
        return mapToMessageDto(url);
    }

    @Override
    public DashboardMessageDto getAccess(FacebookAuthorizationDto authorizationDto) {
        String userAccessToken = getUserAccessToken(authorizationDto);
        String facebookUserId = getFacebookUserId(userAccessToken);
        HashMap<String, String> pages = getPageDetails(facebookUserId, userAccessToken);

        FacebookAuthDetails facebookAuthDetails = new FacebookAuthDetails();
        facebookAuthDetails.setFacebookUserId(facebookUserId);
        facebookAuthDetails.setUserAccessToken(userAccessToken);
        facebookAuthDetails.setPages(pages);
        facebookAuthDetails.setAppUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
        facebookAuthDetails.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 30));

        facebookAuthRepository.save(facebookAuthDetails);
        return mapToMessageDto("Access successfully");
    }

    private String getUserAccessToken(FacebookAuthorizationDto authorizationDto) {
        try {
            String url = FacebookConstant.FACEBOOK_OAUTH_ACCESS_TOKEN_URL +
                    "?client_id=" + FacebookConstant.FACEBOOK_APP_ID +
                    "&redirect_uri=" + authorizationDto.getRedirectUrl() +
                    "&client_secret=" + FacebookConstant.FACEBOOK_APP_SECRET +
                    "&code=" + authorizationDto.getAuthorizationCode();
            ResponseEntity<com.luantang.socialmediaanalytics.dashboard.dto.response.user.token.Root> responseEntity =  restTemplate.getForEntity(url, com.luantang.socialmediaanalytics.dashboard.dto.response.user.token.Root.class);
            return Objects.requireNonNull(responseEntity.getBody()).getAccess_token();
        }
        catch (NullPointerException ex) {
            //TODO change the exception
            throw new NullTokenException("Token is null");
        }
        catch (HttpClientErrorException ex) {
            throw new InvalidAuthorizationCodeException("Invalid verification code format or code has expired");
        }
    }

    private String getFacebookUserId(String userAccessToken) {
        try {
            String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                    "/me?fields=" + "id" +
                    "&access_token=" + userAccessToken;
        ResponseEntity<Root> responseEntity =  restTemplate.getForEntity(url, Root.class);

            return Objects.requireNonNull(responseEntity.getBody()).getId();
        }
        catch (NullPointerException ex) {
            //TODO change the exception
            throw new NullTokenException("User id is null");
        }
    }

    private HashMap<String, String> getPageDetails(String facebookUserId, String userAccessToken) {
        try {
            String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                    facebookUserId +
                    "/accounts?access_token=" + userAccessToken;
            ResponseEntity<com.luantang.socialmediaanalytics.dashboard.dto.response.user.account.Root> responseEntity
                    = restTemplate.getForEntity(url, com.luantang.socialmediaanalytics.dashboard.dto.response.user.account.Root.class);
            com.luantang.socialmediaanalytics.dashboard.dto.response.user.account.Root userPageDetails = Objects.requireNonNull(responseEntity.getBody());
            HashMap<String, String> pages = new HashMap<>();
            for(Datum page : userPageDetails.getData()) {
                pages.put(page.getId(), page.getAccess_token());
            }
            return pages;
        }
        catch (NullPointerException ex) {
            //TODO change the exception
            throw new NullTokenException("Page details is null");
        }
    }

    @Override
    public List<FacebookPagePostPerformanceDto> getPostPerformances
            (String startDate, String endDate) {
        try {
            FacebookAuthDetails authDetails = getAuthDetailsByAppUserEmail(SecurityContextHolder.getContext().getAuthentication().getName());
            List<FacebookPagePostPerformanceDto> metadata = new ArrayList<>();
            for(Map.Entry<String, String> page : authDetails.getPages().entrySet()) {
                //Key is page id
                //Value is page access token
                String url = FacebookConstant.FACEBOOK_GRAPH_API_URL +
                        page.getKey() +
                        "/feed?fields=id," +
                            "message,created_time," +
                            "reactions.limit(100).summary(true)," +
                            "comments.limit(100) .summary(true)," +
                            "likes.limit(100).summary(true)" +
                        "&since=" + startDate +
                        "&until=" + endDate +
                        "&access_token=" + page.getValue();
                ResponseEntity<com.luantang.socialmediaanalytics.dashboard.dto.response.page.feed.Root> responseEntity
                        = restTemplate.getForEntity(url, com.luantang.socialmediaanalytics.dashboard.dto.response.page.feed.Root.class);
                metadata.add(mapToPagePostPerformanceDto(page.getKey(), facebookDataProcessor.matchPostPerformanceData(responseEntity.getBody())));
            }
            return metadata;
        }
        catch (NullPointerException ex) {
            //TODO change the exception
            throw new NullTokenException("Post performances is null");
        }
    }

    @Override
    public FacebookAuthDetails saveAuthDetails(FacebookAuthDetails facebookAuthDetails) {
        try {
            //TODO check if user access token expired, or completely change the token
            return facebookAuthRepository.save(facebookAuthDetails);
        }
        catch (Exception e) {
            System.out.println(e);
            //TODO change the exception
            throw new NullTokenException("Can't save the authentication details");
        }
    }

    @Override
    public FacebookAuthDetails getAuthDetailsByAppUserEmail(String email) {
        return facebookAuthRepository.findByAppUserEmail(email)
                .orElseThrow(() -> new AuthNotFoundException("Authentication details with that associate email could not be found."));
    }


    private DashboardMessageDto mapToMessageDto(String message) {
        DashboardMessageDto dashboardMessageDto = new DashboardMessageDto();
        dashboardMessageDto.setMessage(message);
        dashboardMessageDto.setTimestamp(new Date(System.currentTimeMillis()));
        return dashboardMessageDto;
    }

    private FacebookPagePostPerformanceDto mapToPagePostPerformanceDto(String pageId, List<FacebookPostPerformanceData> postPerformanceDataList) {
        FacebookPagePostPerformanceDto pagePostPerformanceDto = new FacebookPagePostPerformanceDto();
        pagePostPerformanceDto.setPageId(pageId);
        pagePostPerformanceDto.setPostPerformance(postPerformanceDataList);
        return pagePostPerformanceDto;
    }
}
