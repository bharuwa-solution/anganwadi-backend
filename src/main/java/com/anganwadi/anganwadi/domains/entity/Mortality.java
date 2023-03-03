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
@Document(collection = "Mortality")
public class Mortality extends BaseObject {

    private String id;
    private String familyId;
    private String memberId;
    private String name;
    private Date date;
    private String reason;

}
