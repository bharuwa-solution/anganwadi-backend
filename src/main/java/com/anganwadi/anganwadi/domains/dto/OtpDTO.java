package com.anganwadi.anganwadi.domains.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpDTO {

    private String mobileNumber;
    private String status;
    private String otp;
    @JsonIgnore
    private String authToken;
}
