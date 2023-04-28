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
@Document(collection = "AttendancePhoto")
public class AttendancePhoto extends BaseObject{

    private String centerId;
    private String centerName;
    private String date;
    private String imageName;

}
