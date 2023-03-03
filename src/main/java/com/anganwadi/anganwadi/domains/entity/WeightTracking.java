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
@Document(collection = "WeightTracking")
public class WeightTracking  extends BaseObject{

    private String familyId;
    private String childId;
    private Date date;
    private String height;
    private String weight;

}
