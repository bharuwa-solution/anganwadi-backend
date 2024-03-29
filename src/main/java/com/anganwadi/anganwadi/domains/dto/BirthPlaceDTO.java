package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BirthPlaceDTO {

    private String id;
    private String name;
    private String birthPlace;
    private String visitFor;
    private String srNo;
    private String visitType;
    private String visitRound;
    private String relationWithOwner;
    private String dob;
    private String latitude;
    private String longitude;
    private String centerId;
    private String centerName;
    private String birthType;
    private String gender;
    private String firstWeight;
    private String height;
    private String motherMemberId;
    private String familyId;

}
