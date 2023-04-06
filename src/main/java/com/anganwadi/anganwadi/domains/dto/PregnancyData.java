package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PregnancyData {

    private String month;
    private long sc;
    private long st;
    private long obc;
    private long general;
    private long others;
    private String startDate;
    private String endDate;
}
