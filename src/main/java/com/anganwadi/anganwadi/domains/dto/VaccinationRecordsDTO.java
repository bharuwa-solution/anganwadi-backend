package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VaccinationRecordsDTO {

    private String memberId;
    private String startDate;
    private String centerId;
    private String endDate;
    private String centerName;
    private String vaccinationCode;

}
