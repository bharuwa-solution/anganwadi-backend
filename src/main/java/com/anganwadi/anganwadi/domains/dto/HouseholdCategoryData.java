package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseholdCategoryData {

    private String month;
    private String type;
    private long general;
    private long sc;
    private long st;
    private long obc;
    private long others;
}
