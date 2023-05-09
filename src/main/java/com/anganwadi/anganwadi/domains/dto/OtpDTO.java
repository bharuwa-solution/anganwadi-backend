package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpDTO {

    private String id;
    private String centerId;
    private String otp;
    private String status;
    private String role;
    private String centerName;
    private String uniqueCode;
    private String name;
    private String mobileNumber;
    private String gender;
    private String email;
    private String version;
    private String dob;
    private String userPic;
}
