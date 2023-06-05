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
@Document(collection = "HouseVisitSchedule")
public class HouseVisitSchedule extends BaseObject {

    private String visitType;
    private String memberId;
    private String centerId;
    private String centerName;
    private String visitName;
    private String visitRound;
    private String comments;
    private String latitude;
    private String longitude;
    private long dueDate;
    private long visitDate;
}
