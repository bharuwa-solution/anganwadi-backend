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
@Document(collection = "User")
public class User extends BaseObject {

    private String centerId;
    private String uniqueCode;
    private String centerName;
    private String name;
    private String mobileNumber;
    private long lastLogin;
    private String password;
    private String gender;
    private String email;
    private String dob;
    private String userPic;
    private String role;
    private String authToken;


}
