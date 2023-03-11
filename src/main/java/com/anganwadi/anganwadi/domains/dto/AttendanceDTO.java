package com.anganwadi.anganwadi.domains.dto;

import com.anganwadi.anganwadi.domains.entity.Attendance;
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
    private String dob;
    private long date;
    private Attendance attendance;
    private String photo;
}
