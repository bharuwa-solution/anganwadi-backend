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
@Document(collection = "Family")
public class Family extends BaseObject {

    private String headName;
    private String headGender;
    private String houseNo;
    private String uniqueIdType;
    private String uniqueId;
    private String religion;
    private String headDob;
    private String mobileNumber;
    private String centerId;
    private String memberId;
    private String category;
    private String isMinority;
    private String icdsService;
    private String headPic;
    private String totalMembers;
}

