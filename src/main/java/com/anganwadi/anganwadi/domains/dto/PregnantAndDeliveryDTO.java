package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PregnantAndDeliveryDTO {

    private String id;
    private String familyId;
    private String[] yojana;
    private String motherMemberId;
    private String profilePic;
    private String motherName;
    private String dob;
    private String husbandName;
    private String childName;
    private String childGender;
    private String category;
    private String religion;
    private String houseNumber;
    private String regDate;
    private int noOfChild;
    private String lastMissedPeriodDate;
    private String dateOfDelivery;
    private String centerId;
    private boolean isDeleted;
    private String centerName;
}
