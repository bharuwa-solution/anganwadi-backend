package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChildrenDTO {

    private String name;
    private String gender;
    private String dob;
    private boolean isRegistered;
    private String profilePic;
    private String category;


}
