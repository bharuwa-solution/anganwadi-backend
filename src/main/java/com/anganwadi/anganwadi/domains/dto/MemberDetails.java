package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDetails {

    private String id;
    private String name;
    private String houseNo;
    private String category;
    private String religion;
    private String gender;
    private List<VaccinationList> vaccination;
    private DeliveryList delivery;
    private List<HouseVisitsList> houseVisits;
}
