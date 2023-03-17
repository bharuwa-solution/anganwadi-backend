package com.anganwadi.anganwadi.domains.dto;

import com.anganwadi.anganwadi.domains.entity.Role;
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
    private String name;
    private String mobileNumber;
    private String password;
    private String gender;
    private String email;
    private String dob;
    private String userPic;
    private Role role;
    private String authToken;
}
