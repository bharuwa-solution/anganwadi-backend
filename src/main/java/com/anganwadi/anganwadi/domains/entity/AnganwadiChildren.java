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
@Document(collection = "AnganwadiChildren")
public class AnganwadiChildren extends StateObject {

    private String profilePic;
    private String familyId;
    private String childId;
    private String centerName;
    private String name;
    private String mobileNumber;
    private boolean isRegistered;
    private String fatherName;
    private String motherName;
    private String gender;
    private String dob;
    private String minority;
    private String handicap;
    private String category;

}
