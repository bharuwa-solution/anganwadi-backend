package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetVaccinationDTO {

    private String name;
    private String motherName;
    private String childId;
    private String houseNo;
    private String age;
    private String gender;
    private String centerId;
    private String centerName;
    private String vaccinationCode;
    private String vaccinationName;
    private String photo;
}
