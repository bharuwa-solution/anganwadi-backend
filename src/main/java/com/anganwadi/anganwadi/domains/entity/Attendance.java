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
@Document(collection = "Attendance")
public class Attendance extends StateObject {

    private String familyId;
    private String centerId;
    private String centerName;
    private String name;
    private boolean isRegistered;
    private String childId;
    private long date;
    private String attType;
    private String latitude;
    private String longitude;
    private String dob;
    private String gender;
    private String photo;
    private String attendance;

}
