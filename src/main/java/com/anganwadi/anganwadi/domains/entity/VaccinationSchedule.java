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
@Document(collection = "VaccinationSchedule")
public class VaccinationSchedule extends BaseObject {

    private String familyId;
    private long pregnantVisit1;
    private long pregnantVisit2;
    private long deliveryDayVisit;
    private long birthVisit1;
    private long birthVisit2;
    private long birthVisit3;
    private long birthVisit4;
    private long birthVisit5;
    private long birthVisit6;
    private long birthVisit7;
    private long polioOPB;
    private long hipB0;
    private long bcg;
    private long dpt1;
    private long hipB1;
    private long polio1;
    private long dpt2;
    private long hipB2;
    private long polio2;
    private long dpt3;
    private long hipB3;
    private long polio3;
    private long khasra1;
    private long vitamin1;
    private long dptBooster;
    private long khasra2;


}
