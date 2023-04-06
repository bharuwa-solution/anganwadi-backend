package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveAdmissionDTO {

    private String id;
    private String centerName;
    private String profilePic;
    private String name;
    private String familyId;
    private String childId;
    private boolean isRegistered;
    private String fatherName;
    private String motherName;
    private String mobileNumber;
    private String minority;
    private String handicap;
    private String gender;
    private String dob;
    private String category;
}
