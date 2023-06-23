package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseVisitsList {

    private String visitType;
    private String title;
    private String dueDate;
    private List<HouseVisitRounds> round;

}
