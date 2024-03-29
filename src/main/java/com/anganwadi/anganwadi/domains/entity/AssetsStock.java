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
@Document(collection = "AssetsStock")
public class AssetsStock extends BaseObject {


    private String itemName;
    private String qty;
    private String month;
    private String itemCode;
    private String centerId;
    private String centerName;
    private String qtyUnit;
    private long date;
    private String previousStock;
    private long credit;
    private long withdraw;
    private String comments;

}
