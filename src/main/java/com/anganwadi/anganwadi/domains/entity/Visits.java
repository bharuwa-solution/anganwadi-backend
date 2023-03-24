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
@Document(collection = "Visits")
public class Visits  extends BaseObject{


    private String familyId;
    private String memberId;
    private long childDob;
    private String category;
    private String centerName;
    private String visitType;
    private String visitFor;
    private String visitRound;
    private String description;
    private long visitDateTime;
    private String latitude;
    private String longitude;
}
