package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseVisitsList {

    private String name;
    private String title;
    private String dueDate;
    private String visitRound;
    private String visitDate;
    private String comments;
    private String latitude;
    private String longitude;
}
