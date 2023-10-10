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
@Document(collection = "BloodTestTracking")
public class BloodTestTracking extends BaseObject {

    private String testCode;
    private String centerId;
    private String familyId;
    private String result;
    private String motherId;
    private String visitType;
    private String visitRound;
    private String memberId;
    private long date;
}
