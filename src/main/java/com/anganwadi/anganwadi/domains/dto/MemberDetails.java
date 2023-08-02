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
    private String dob;
    private String motherName;
    private String fatherHusbandName;
    private String name;
    private String visits;
    private RecentVisits recentVisits;
    private String centerName;
    private String houseNo;
    private String category;
    private String religion;
    private String gender;
    private String profilePic;
    private List<VaccinationList> vaccination;
    private DeliveryList delivery;
    private List<HouseVisitsList> houseVisits;
}
