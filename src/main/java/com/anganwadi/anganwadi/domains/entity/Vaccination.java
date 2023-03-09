package com.anganwadi.anganwadi.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Vaccination")
public class Vaccination extends BaseObject {


    private String familyId;
    private String memberId;
    private String childName;
    private String gender;
    private String photo;
    private String vaccinationName;
    private Date date;

}
