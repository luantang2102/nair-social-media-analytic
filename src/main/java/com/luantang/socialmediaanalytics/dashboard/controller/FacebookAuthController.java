package com.luantang.socialmediaanalytics.dashboard.controller;

import com.luantang.socialmediaanalytics.dashboard.dto.DashboardMessageDto;
import com.luantang.socialmediaanalytics.dashboard.dto.FacebookAuthorizationDto;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user/facebook")
public class FacebookAuthController {
    public final FacebookService facebookService;

    @Autowired
    public FacebookAuthController(FacebookService facebookService) {
        this.facebookService = facebookService;
    }

    @GetMapping("/access")
    public ResponseEntity<DashboardMessageDto> getAccess(@RequestBody FacebookAuthorizationDto facebookAuthDto) {
        return new ResponseEntity<>(facebookService.getAccess(facebookAuthDto), HttpStatus.OK);
    }

    @GetMapping("/authorize")
    public ResponseEntity<DashboardMessageDto> getAuthorizeUrl(@RequestBody FacebookAuthorizationDto facebookAuthDto) {
        return new ResponseEntity<>(facebookService.createAuthorizationUrl(facebookAuthDto), HttpStatus.CREATED);
    }

}
