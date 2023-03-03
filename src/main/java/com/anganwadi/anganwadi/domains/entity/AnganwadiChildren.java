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
@Document(collection = "AnganwadiChildren")
public class AnganwadiChildren  extends BaseObject{

    private String familyId;
    private String name;
    private String motherName;
    private String gender;
    private Date dob;
    private String category;

}
