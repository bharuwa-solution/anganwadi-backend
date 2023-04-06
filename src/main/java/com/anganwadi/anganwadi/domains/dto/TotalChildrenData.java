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

    private long zeroToOne;
    private long oneToTwo;
    private long twoToThree;
    private long threeToFour;
    private long fourToFive;
    private long fiveToSix;
    private String caste;
    private String gender;
    private String startDate;
    private String endDate;
}
