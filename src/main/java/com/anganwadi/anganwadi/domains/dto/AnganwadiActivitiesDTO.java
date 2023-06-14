package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnganwadiActivitiesDTO {

    private String id;
    private String centerName;
    private String centerId;
    private String childId;
    private long childrenCount;
    private String date;
    private String[] selectedActivity;
    private String startDate;
    private String endDate;
}
