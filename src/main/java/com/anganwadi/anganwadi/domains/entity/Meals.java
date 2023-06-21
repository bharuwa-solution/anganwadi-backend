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

    private String itemCode;
    private String quantity;
    private String mealType;
    private String totalCalorie;
    private String totalProtein;
    private String centerId;
    private long date;

}
