package com.anganwadi.anganwadi.domains.dto;

import io.swagger.annotations.ApiModelProperty;
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
    @ApiModelProperty(hidden = true)
    private String authToken;
}
