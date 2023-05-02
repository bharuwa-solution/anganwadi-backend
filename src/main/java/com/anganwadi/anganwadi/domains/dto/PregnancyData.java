package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PregnancyData {

    private String motherName;
    private String motherId;
    private String lastMissedPeriodDate;
    private String startDate;
    private String endDate;
}
