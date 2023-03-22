package com.anganwadi.anganwadi.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BirthPlaceDTO {

    private String name;
    private String birthPlace;
    private String centerId;
    private String birthType;
    private String gender;
    private String firstWeight;
    private String motherMemberId;
    private String familyId;

}
