package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseholdsChildren {

    private String childName;
    private String gender;
    private String  dob;
    private String childId;
    private String familyId;
    private String motherName;
    private String photoUrl;
    private String fatherName;
    private String mobileNumber;
    private String category;
    private String minority;
    private String handicap;


}
