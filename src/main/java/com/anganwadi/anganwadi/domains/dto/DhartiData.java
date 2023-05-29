package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DhartiData {

    private String motherName;
    private String motherId;
    private String category;
    private String dateOfDelivery;
    private String startDate;
    private String endDate;
}
