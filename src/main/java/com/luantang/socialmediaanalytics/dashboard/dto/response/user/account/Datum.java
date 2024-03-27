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
public class Datum {
    private String access_token;
    private String category;
    private List<CategoryList> category_list = new ArrayList<>();
    private String name;
    private String id;
    private List<String> tasks = new ArrayList<>();

}
