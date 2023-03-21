package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockOutputArray {

    private String itemName;
    private String itemCode;
    private String centerName;
    private String quantity;
    private String unit;


}
