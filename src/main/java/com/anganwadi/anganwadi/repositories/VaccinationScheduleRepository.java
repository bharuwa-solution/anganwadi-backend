package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.VaccinationSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationScheduleRepository extends MongoRepository<VaccinationSchedule, String> {

    @Query("{'dueDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<VaccinationSchedule> findAllByDateRange(Long startTime, Long endTime, String centerId);

    List<VaccinationSchedule> findAllByMemberIdAndCode(String memberId, String visitFor);

    List<VaccinationSchedule> findAllByMemberId(String id);

    void deleteByMemberId(String id);

    @Query("{'memberId':?0,'dueDate':{$gte:?1}}")
    List<VaccinationSchedule> findPrematureDelivery(String motherId, long babyDob);

    void deleteByMemberIdAndDueDate(String motherId, long dueDate);
}
