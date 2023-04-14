package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceDTO {

    private String name;
    private String gender;
    private String childId;
    private String centerId;
    private String centerName;
    private String dob;
    private String latitude;
    private String longitude;
    private long date;
    private String attType;
    private String att;
    private String attendance;
    private String photo;
}
