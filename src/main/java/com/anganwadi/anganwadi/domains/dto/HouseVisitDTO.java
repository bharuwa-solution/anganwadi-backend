package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HouseVisitDTO {

    private String name;
    private String profilePic;
    private String husbandName;
    private String memberId;
    private String houseNo;
    private String visits;
    private String dob;
    private String centerName;


}
