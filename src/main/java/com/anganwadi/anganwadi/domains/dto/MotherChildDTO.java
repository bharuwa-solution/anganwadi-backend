package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MotherChildDTO {

    private String id;
    private String familyId;
    private String[] yojana;
    private String motherMemberId;
    private String profilePic;
    private String motherName;
    private String husbandName;
    private String category;
    private String religion;
    private String houseNumber;
    private String regDate;
    private String lastMissedPeriodDate;
    private String dateOfDelivery;
    private String centerId;
    private boolean isDeleted;
    private String centerName;
    private List<NewBornChildDTO> childDetails;
}
