package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VaccinationRecords {

    private String name;
    private String motherName;
    private String age;
    private String gender;
    private String vaccination;
    private String photo;
}
