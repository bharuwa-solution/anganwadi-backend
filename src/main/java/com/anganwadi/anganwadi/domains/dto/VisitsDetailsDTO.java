package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VisitsDetailsDTO {

    private String memberId;
    private String centerName;
    private String visitType;
    private String visitFor;
    private String visitRound;
    private String description;
    private String visitDateTime;
    private String latitude;
    private String longitude;


}
