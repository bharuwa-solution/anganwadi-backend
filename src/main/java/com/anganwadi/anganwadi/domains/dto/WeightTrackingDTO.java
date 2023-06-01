package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeightTrackingDTO {

    private String familyId;
    private String startDate;
    private String centerId;
    private String endDate;
    private String childId;
    private String date;
    private String bmi;
    private String height;
    private String weight;

}
