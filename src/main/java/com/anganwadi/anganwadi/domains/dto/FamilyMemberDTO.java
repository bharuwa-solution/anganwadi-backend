package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilyMemberDTO {

    private String id;
    private String name;
    private String photo;
    private String centerName;
    private String motherName;
    private String fatherName;
    private String familyId;
    private String category;
    private String mobileNumber;
    private String memberCode;
    private String idType;
    private String idNumber;
    private String relationWithOwner;
    private String gender;
    private String dob;
    private String maritalStatus;
    private String stateCode;
    private String handicapType;
    private String residentArea;
    private String dateOfArrival;
    private String dateOfLeaving;
    private String dateOfMortality;
}
