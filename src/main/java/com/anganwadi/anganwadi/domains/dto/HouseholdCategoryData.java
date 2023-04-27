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

    private String startDate;
    private String endDate;
    private String name;
    private String centerId;
    private String centerName;
    private String category;
    private String gender;
    private String dob;
}
