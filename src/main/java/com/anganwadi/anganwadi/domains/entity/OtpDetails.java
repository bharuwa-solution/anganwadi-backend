package com.anganwadi.anganwadi.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "OtpDetails")
public class OtpDetails extends BaseObject {

    private String mobileNumber;
    private String role;
    private String otp;
    private String messageType;
    private String message;
    private String messageResponse;
    private String statusCode;
    private String version;
    private Long expiryTime;

}
