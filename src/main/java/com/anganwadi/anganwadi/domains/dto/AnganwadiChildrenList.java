package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnganwadiChildrenList {

    private String name;
    private String childId;
    private String centerId;
    private String startDate;
    private String endDate;
    private String minority;
    private String motherName;
    private String fatherName;
    private String dob;
    private String category;
    private String religion;
    private String houseNo;

}
