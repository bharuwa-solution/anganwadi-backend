package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BeneficiaryList {

    private String id;
    private String name;
    private String dob;
    private String centerName;
    private String uniqueCode;
    private String headName;
    private String houseNo;
    private String centerId;
    private String uniqueIdType;
    private String uniqueId;
    private String religion;
    private String mobileNumber;
    private String isMinority;
    private String icdsService;
    private String headGender;
    private String headDob;
    private String totalMembers;
    private String headPic;
    private String category;
}
