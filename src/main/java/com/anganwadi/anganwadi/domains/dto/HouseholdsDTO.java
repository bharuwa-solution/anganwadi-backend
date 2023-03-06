package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseholdsDTO {

    private String name;
    private String houseNo;
    private String uniqueIdType;
    private String uniqueId;
    private String religion;
    private String mobileNumber;
    private String isMinority;
    private String icdsService;
    private String gender;
    private String dob;
    private int members;
    private String headPic;
    private String category;


}
