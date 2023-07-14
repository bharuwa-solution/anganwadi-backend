package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.HouseVisitSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseVisitScheduleRepository extends MongoRepository<HouseVisitSchedule, String> {

    @Query("{'visitType':?0,'memberId':?1,'visitRound':?2}")
    List<HouseVisitSchedule> findAllByDeliveryTypeAndVisitRound(String delivery, String motherId, String visitRound);

    @Query("{'dueDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<HouseVisitSchedule> findAllByDateRange(Long startTime, Long endTime, String centerId);

    List<HouseVisitSchedule> findAllByMemberIdAndVisitType(String memberId, String visitType);

    List<HouseVisitSchedule> findAllByMemberId(String memberId);

    void deleteByMemberId(String id);

    @Query("{'memberId':?0,'dueDate':{$gte:?1}}")
    List<HouseVisitSchedule> findPrematureDelivery(String motherId, long babyDob);

    void deleteByMemberIdAndDueDate(String motherId, long dueDate);
}
