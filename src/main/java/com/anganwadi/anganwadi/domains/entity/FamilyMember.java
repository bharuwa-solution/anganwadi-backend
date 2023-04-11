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
@Document(collection = "FamilyMember")
public class FamilyMember extends BaseObject {


    private String familyId;
    private String name;
    private String photo;
    private String category;
    private boolean isRegistered;
    private String motherName;
    private String fatherName;
    private String mobileNumber;
    private String stateCode;
    private String idType;
    private String idNumber;
    private String centerId;
    private String centerName;
    private String relationWithOwner;
    private String gender;
    private long dob;
    private String maritalStatus;
    private String memberCode;
    private String handicap;
    private String handicapType;
    private String residentArea;
    private String dateOfArrival;
    private String dateOfLeaving;
    private String dateOfMortality;
    private String recordForMonth;

}
