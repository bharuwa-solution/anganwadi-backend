package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilyMemberConverted {

    private String id;
    private String familyId;
    private String name;
    private String photo;
    private String category;
    private boolean isRegistered;
    private String motherName;
    private String fatherName;
    private String mobileNumber;
    private String stateCode;
    private String idType;
    private String idNumber;
    private String centerId;
    private String centerName;
    private String relationWithOwner;
    private String gender;
    private String  dob;
    private String maritalStatus;
    private String memberCode;
    private String handicap;
    private String handicapType;
    private String residentArea;
    private String dateOfArrival;
    private String dateOfLeaving;
    private String dateOfMortality;
    private String recordForMonth;
}
