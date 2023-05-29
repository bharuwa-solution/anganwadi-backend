package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PregnantWomenDetails {
    private String name;
    private String husbandName;
    private String motherId;
    private String lastMissedPeriodDate;
    private String dob;
    private String minority;
    private String category;
    private String religion;
    private String duration;
}
