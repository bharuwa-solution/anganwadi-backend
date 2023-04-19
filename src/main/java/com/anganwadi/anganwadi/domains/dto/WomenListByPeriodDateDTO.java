package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WomenListByPeriodDateDTO {

    private String id;
    private String familyId;
    private String memberId;
    private String profilePic;
    private String name;
    private String dob;
    private String husbandName;
    private String childName;
    private String childGender;
    private String category;
    private String religion;
    private String houseNo;
    private String regDate;
    private int noOfChild;
    private String lastMissedPeriodDate;
    private String dateOfDelivery;
    private String centerId;
    private boolean isDeleted;
    private String centerName;
}
