package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveActivitiesDTO {

    private String id;
    private String centerName;
    private String centerId;
    private long childrenCount;
    private String date;
    private boolean gaming;
    private boolean cleaning;
    private boolean preEducation;
}
