package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AnganwadiCenterDTO {

    private String centerName;
    private String centerId;
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
