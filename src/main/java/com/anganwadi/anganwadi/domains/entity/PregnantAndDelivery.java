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
@Document(collection = "PregnantAndDelivery")
public class PregnantAndDelivery extends BaseObject {


    private String familyId;
    private String motherMemberId;
    private String motherName;
    private long dob;
    private String husbandName;
    private String profilePic;
    private String childName;
    private String category;
    private String religion;
    private String houseNumber;
    private long regDate;
    private int noOfChild;
    private long lastMissedPeriodDate;
    private long dateOfDelivery;
    private Date tentitiveDeliveryDate;
    private String placeOfDelivery;
    private String childGender;
    private String childWeight;
    private String birthStatus;
    private String centerId;
    private String centerName;

}
