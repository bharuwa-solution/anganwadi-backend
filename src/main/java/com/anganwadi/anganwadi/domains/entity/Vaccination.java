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
@Document(collection = "Vaccination")
public class Vaccination extends BaseObject {


    private String familyId;
    private String childId;
    private String motherId;
    private String centerId;
    private String visitType;
    private String visitRound;
    private String centerName;
    private String vaccinationCode;
    private long date;

}
