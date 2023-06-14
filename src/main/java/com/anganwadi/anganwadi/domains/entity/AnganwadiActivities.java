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
@Document(collection = "AnganwadiActivities")
public class AnganwadiActivities extends BaseObject {

    private String centerName;
    private String centerId;
    private String childId;
    private String[] selectedActivity;
    private long date;
}
