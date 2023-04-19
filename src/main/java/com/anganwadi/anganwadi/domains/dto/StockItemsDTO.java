package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockItemsDTO {

    private String itemName;
    private String itemCode;
    private String date;
    private String centerId;
    private String centerName;
    private String quantity;
    private String unit;


}
