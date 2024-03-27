package com.luantang.socialmediaanalytics.dashboard.dto.response.user.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Root {
    private List<Datum> data = new ArrayList<Datum>();
    private Paging paging;
}
