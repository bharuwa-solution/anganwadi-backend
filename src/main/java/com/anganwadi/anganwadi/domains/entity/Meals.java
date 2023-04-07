package com.anganwadi.anganwadi.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Meals")
public class Meals extends StateObject {

    private String foodName;
    private String foodCode;
    private String quantity;
    private String quantityUnit;
    private String month;
    private long date;
    private String mealType;
}
