package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewBornChildDTO {

    private String id;
    private String name;
    private String childId;
    private String birthPlace;
    private String visitFor;
    private String srNo;
    private String visitType;
    private String fatherName;
    private String visitRound;
    private String relationWithOwner;
    private String dob;
    private String centerId;
    private String centerName;
    private String birthType;
    private String gender;
    private String firstWeight;
    private String height;
    private String motherMemberId;
    private String familyId;
    private String motherPhoto;
    private String motherName;
    private String houseNumber;

}
