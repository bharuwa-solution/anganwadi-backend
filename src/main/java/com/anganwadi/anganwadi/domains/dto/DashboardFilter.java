package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardFilter {

    private String startDate;
    private String endDate;
    private String search;
    private String district;
    private String tehsil;
    private String block;
    private String village;
}
