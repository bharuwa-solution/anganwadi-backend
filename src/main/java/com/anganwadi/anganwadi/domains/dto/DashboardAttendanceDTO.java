package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardAttendanceDTO {

    private String childId;
    private String startDate;
    private String centerId;
    private String endDate;
    private String centerName;
    private String date;
    private String attendance;
    private String attendanceType;
}
