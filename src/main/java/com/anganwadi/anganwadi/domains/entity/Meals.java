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
@Document(collection = "Meals")
public class Meals  extends BaseObject{

    private String familyId;
    private String name;
    private String motherName;
    private String gender;
    private String dob;
    private String category;
}
