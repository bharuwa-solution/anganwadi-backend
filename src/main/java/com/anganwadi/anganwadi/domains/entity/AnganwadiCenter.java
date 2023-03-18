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
@Document(collection = "AnganwadiCenter")
public class AnganwadiCenter extends BaseObject {


    private String centerName;
    private String uniqueCode;
    private String villageName;
    private String tahsilName;
    private String blockName;
    private String districtName;
    private String stateName;
    private String villageCode;
    private String tahsilCode;
    private String blockCode;
    private String districtCode;
    private String stateCode;
}
