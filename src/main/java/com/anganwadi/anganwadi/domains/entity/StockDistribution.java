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
@Document(collection = "StockDistribution")
public class StockDistribution extends BaseObject {

    private String familyId;
    private long date;
    private String month;
    private String centerName;
    private String itemName;
    private String itemCode;
    private String quantity;
    private String unit;

}
