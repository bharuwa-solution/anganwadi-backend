package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VaccinationScheduleDTO {

    private String date;
    private VaccinationStatusDTO deliveryDetails;
    private VaccinationStatusDTO vaccinationDetails;
    private VaccinationStatusDTO houseVisitsDetails;
}
