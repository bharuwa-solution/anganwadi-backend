package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildrenDTO {

    private String id;
    private String childId;
    private String name;
    private String motherName;
    private String fatherName;
    private String mobileNumber;
    private String handicap;
    private String gender;
    private boolean deleted;
    private String dob;
    private boolean isRegistered;
    private String profilePic;
    private String category;


}
