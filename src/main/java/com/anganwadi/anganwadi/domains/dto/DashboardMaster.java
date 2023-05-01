package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardMaster {

    private String centerId;
    private String centerName;
    private long pregnantWomenCount;
    private long dhatriWomenCount;
    private long childrenCount;
    private String todayAttendance;
}
