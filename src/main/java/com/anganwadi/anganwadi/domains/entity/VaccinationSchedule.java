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
@Document(collection = "VaccinationSchedule")
public class VaccinationSchedule extends BaseObject {

    private String familyId;
    private String memberId;
    private String centerId;
    private String centerName;
    private String vaccinationName;
    private String code;
    private long dueDate;
    private long vaccinationDate;


}
