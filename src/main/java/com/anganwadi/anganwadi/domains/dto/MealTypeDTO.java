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
public class MealTypeDTO {

    private List<FoodItemsDTO> breakFastLists;
    private List<FoodItemsDTO> mealsLists;
}
