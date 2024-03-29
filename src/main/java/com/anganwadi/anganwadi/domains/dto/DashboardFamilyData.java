package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DashboardFamilyData {

    private long nursingMothers;
    private long pregnantWomen;
    private long totalBeneficiary;
    private long children;

}
