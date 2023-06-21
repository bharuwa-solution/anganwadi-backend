package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SaveMeals {

    private String itemName;
    private String itemCode;
    private String quantity;
    private String totalCalorie;
    private String totalProtein;
    private String centerId;
    private String centerName;
    private String quantityUnit;
    private String mealType;
    private String date;

}
