package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitsDetailsDTOTemp {

    private String memberId;
    private String latitude;
    private String longitude;
    private String visitFor;
    private String visitType;
    private String visitCategory;
    private List<BloodTestCases> bloodTest;
    private String weight;
    private String height;
    private String description;
    private String visitRound;

}
