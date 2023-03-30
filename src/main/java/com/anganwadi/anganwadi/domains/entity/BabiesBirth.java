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
@Document(collection = "BabiesBirth")
public class BabiesBirth extends BaseObject {

    private String name;
    private String birthPlace;
    private String birthType;
    private String month;
    private String centerId;
    private String height;
    private String gender;
    private String firstWeight;
    private String motherMemberId;
    private String familyId;

}
