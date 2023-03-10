package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardDetails {

    private String currentAttendance;
    private String totalAttendance;
    private String households;
    private String totalChildren;
    private String houseVisits;
    private String weightTracing;
    private String vaccinations;

}
