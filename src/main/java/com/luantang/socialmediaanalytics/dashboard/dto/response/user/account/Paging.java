package com.luantang.socialmediaanalytics.dashboard.dto.response.user.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Paging {
    private Cursors cursors;
}