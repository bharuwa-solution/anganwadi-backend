package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.VaccinationSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationScheduleRepository extends MongoRepository<VaccinationSchedule,String > {

    @Query("{$or:[{'pregnantVisit1':{$gte:?0,$lte:?1}},{'pregnantVisit2':{$gte:?0,$lte:?1}},{'deliveryDayVisit':{$gte:?0,$lte:?1}},{" +
            "'birthVisit1':{$gte:?0,$lte:?1}},{'birthVisit2':{$gte:?0,$lte:?1}},{'birthVisit3':{$gte:?0,$lte:?1}},{" +
            "'birthVisit4':{$gte:?0,$lte:?1}},{'birthVisit5':{$gte:?0,$lte:?1}},{'birthVisit6':{$gte:?0,$lte:?1}},{" +
            "'birthVisit7':{$gte:?0,$lte:?1}},{'polioOPB':{$gte:?0,$lte:?1}},{'hipB0':{$gte:?0,$lte:?1}},{" +
            "'bcg':{$gte:?0,$lte:?1}},{'dpt1':{$gte:?0,$lte:?1}},{'hipB1':{$gte:?0,$lte:?1}},{" +
            "'polio1':{$gte:?0,$lte:?1}},{'dpt2':{$gte:?0,$lte:?1}},{'hipB2':{$gte:?0,$lte:?1}},{" +
            "'polio2':{$gte:?0,$lte:?1}},{'dpt3':{$gte:?0,$lte:?1}},{'hipB3':{$gte:?0,$lte:?1}},{" +
            "'polio3':{$gte:?0,$lte:?1}},{'khasra1':{$gte:?0,$lte:?1}},{'vitamin1':{$gte:?0,$lte:?1}},{" +
            "'dptBooster':{$gte:?0,$lte:?1}},{'khasra2':{$gte:?0,$lte:?1}}]}")
    List<VaccinationSchedule> findAllByDateRange(long startTime, long endTime);
}
