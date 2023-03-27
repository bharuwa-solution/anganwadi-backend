package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveVaccinationDTO {

    private String familyId;
    private String motherName;
    private String childId;
    private String vaccinationName;
    private String description;
    private String visitRound;
    private String visitType;
    private String visitFor;
    private String date;
    private String latitude;
    private String longitude;
}
