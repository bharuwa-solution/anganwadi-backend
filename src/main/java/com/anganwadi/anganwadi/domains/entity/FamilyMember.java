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
@Document(collection = "FamilyMember")
public class FamilyMember  extends BaseObject{


    private String familyId;
    private String name;
    private String photo;
    private String mobileNumber;
    private String idType;
    private String idNumber;
    private String relationWithOwner;
    private String gender;
    private Date dob;
    private String maritalStatus;
    private String memberCode;
    private String handicapType;
    private String residantArea;
    private Date dateOfArrival;
    private Date dateOfLeaving;
    private Date dateOfMortlity;

}
