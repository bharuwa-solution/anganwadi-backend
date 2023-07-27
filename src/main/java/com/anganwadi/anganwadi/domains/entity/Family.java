package com.anganwadi.anganwadi.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Family")
public class Family extends StateObject {


    private String houseNo;
    private String centerName;
    private String religion;
    @Indexed
    private String familyId;
    private String centerId;
    private String memberId;
    private String category;
    private String isMinority;
    private String icdsService;
    private String totalMembers;
}

