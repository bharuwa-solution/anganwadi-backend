package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FemaleMembersDTO {

    private String name;
    private String profilePic;
    private String husbandName;
    private String houseNo;
    private String dob;
    private String centerName;


}
