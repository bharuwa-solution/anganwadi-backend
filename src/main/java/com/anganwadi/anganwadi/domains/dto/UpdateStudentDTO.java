package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStudentDTO {

    private String id;
    private String childId;
    private String name;
    private String profilePic;
    private String gender;
    private String dob;
}
