package com.anganwadi.anganwadi.domains.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "Attendance")
public class Attendance  extends BaseObject{

    private UUID familyId;
    private UUID childId;
    private long date;
    private Attendance attendance;

    private enum attendance {
        P,A;
    }

}
