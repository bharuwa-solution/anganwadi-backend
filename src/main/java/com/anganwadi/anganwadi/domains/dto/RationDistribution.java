package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RationDistribution {

    private String itemName;
    private String itemCode;
    private String allocated;
    private String distribution;
    private String shorted;
    private String access;
    private String quantityUnit;
}
