package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FamilyChildrenDetails {

    private String name;
    private String dob;
    private String photo;
    private String gender;
    private String motherName;
    private String fatherName;
    private String houseNo;
    private String category;
    private String religion;

}
