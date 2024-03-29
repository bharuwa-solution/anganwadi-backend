package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private String centerId;
    private String centerName;
    private String uniqueCode;
    private String name;
    private String mobileNumber;
    private String gender;
    private String email;
    private String dob;
    private String userPic;
}