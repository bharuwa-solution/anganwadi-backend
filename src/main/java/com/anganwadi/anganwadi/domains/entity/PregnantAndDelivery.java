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
    private String memberId;
    private String motherName;
    private Date regDate;
    private int noOfChild;
    private Date lastMissedPeriodDate;
    private Date dateOfDelivery;
    private Date tentitiveDeliveryDate;
    private String placeOfDelivery;
    private String childGender;
    private String childWeight;
    private String birthStatus;

}
