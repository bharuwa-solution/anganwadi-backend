package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeleteBornChildDTO {

    private String id;
    private String name;
    private String birthPlace;
    private String birthType;
    private String relationWithOwner;
    private String dob;
    private String centerId;
    private String centerName;
    private String gender;
    private String firstWeight;
    private String height;
    private boolean deleted;
    private String familyId;

}
