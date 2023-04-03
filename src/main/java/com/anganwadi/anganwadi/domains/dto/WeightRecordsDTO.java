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
    private String childId;
    private String centerName;
    private String date;
    private String height;
    private String weight;

}
