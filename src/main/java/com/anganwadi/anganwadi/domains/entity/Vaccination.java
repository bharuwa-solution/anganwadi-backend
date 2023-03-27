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
    private String centerName;
    private String motherName;
    private String description;
    private String vaccinationName;
    private long date;

}
