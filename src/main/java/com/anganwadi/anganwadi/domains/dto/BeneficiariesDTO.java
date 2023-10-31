package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BeneficiariesDTO {

    private String id;
    private String familyId;
    private String name;
    private String centerName;
    private String fatherName;
    private String motherName;
    private String category;
    private String religion;
    private String dob;

}
