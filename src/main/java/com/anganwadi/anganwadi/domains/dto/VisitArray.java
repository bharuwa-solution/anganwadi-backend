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
public class VisitArray {

    private String visitRound;
    private String visitCategory;
    private String description;
    private String visitFor;
    private String date;
    private List<BloodTestCasesDTO> bloodTestArray;
    private List<ChildWeightDTO> weightTrackArray;
    private List<VaccinationDTO> vaccinationArray;

}
