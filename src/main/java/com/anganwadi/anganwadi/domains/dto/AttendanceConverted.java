package com.anganwadi.anganwadi.domains.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AttendanceConverted {

    private String id;
    private String centerId;
    private String centerName;
    private String name;
    private boolean isRegistered;
    private String childId;
    private String date;
    private String attType;
    private String latitude;
    private String longitude;
    private String dob;
    private String gender;
    private String photo;
    private String attendance;

}
