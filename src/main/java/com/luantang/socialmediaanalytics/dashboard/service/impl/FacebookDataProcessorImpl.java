package com.luantang.socialmediaanalytics.dashboard.service.impl;

import com.luantang.socialmediaanalytics.dashboard.model.data.FacebookPostPerformanceData;
import com.luantang.socialmediaanalytics.dashboard.service.FacebookDataProcessor;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class FacebookDataProcessorImpl implements FacebookDataProcessor {

    //Match posts performance
    @Override
    public List<FacebookPostPerformanceData> matchPostPerformanceData
    (com.luantang.socialmediaanalytics.dashboard.dto.response.page.feed.Root rawResponseData) {
        //TODO handle the cursors to next page
        try {
            List<FacebookPostPerformanceData> allPostPerformanceData = new ArrayList<>();
            for(com.luantang.socialmediaanalytics.dashboard.dto.response.page.feed.Datum datum : rawResponseData.getData()) {
                FacebookPostPerformanceData postPerformanceData = new FacebookPostPerformanceData();
                postPerformanceData.setId(datum.getId());
                postPerformanceData.setCaption(datum.getMessage());
                try {
                    String inputDatePattern = "yyyy-MM-dd'T'HH:mm:ssZ";
                    String outputDatePattern = "yyyy-MM-dd HH:mm:ss";
                    SimpleDateFormat inputDateFormat = new SimpleDateFormat(inputDatePattern);
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat(outputDatePattern);
                    postPerformanceData.setCreatedTime(outputDateFormat.format(inputDateFormat.parse(datum.getCreated_time())));
                } catch (ParseException e) {
                    //TODO Handle parse exception
                    throw new RuntimeException(e);
                }
                postPerformanceData.setTotalReaction(datum.getReactions().getSummary().getTotal_count());
                postPerformanceData.setTotalComment(datum.getComments().getSummary().getTotal_count());
                allPostPerformanceData.add(postPerformanceData);
            }
            return allPostPerformanceData;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            //TODO handle match failed
            throw new RuntimeException();
        }
    }
}
