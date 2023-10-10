package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StockDistributionDTO {

    private String familyId;
    private String date;
    private String itemName;
    private String itemCode;
    private String quantity;
    private String unit;
}
