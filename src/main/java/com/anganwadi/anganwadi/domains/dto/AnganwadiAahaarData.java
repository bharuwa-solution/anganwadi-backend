package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnganwadiAahaarData {
    private String foodName;
    private String foodCode;
    private String quantity;
    private String quantityUnit;
    private String date;
    private String mealType;
    private String startDate;
    private String endDate;
}
