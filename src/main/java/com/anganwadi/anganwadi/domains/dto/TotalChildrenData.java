package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TotalChildrenData {

    private String caste;
    private String gender;
    private String isMinority;
    private String name;
    private String centerId;
    private String centerName;
    private String startDate;
    private String endDate;
}
