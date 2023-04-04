package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeightRecordsDTO {

    private String familyId;
    private String name;
    private String gender;
    private String motherName;
    private String dob;
    private String photo;
    private String childId;
    private String centerName;
    private String date;
    private String height;
    private String weight;

}
