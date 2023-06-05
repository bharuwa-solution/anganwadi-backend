package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.HouseVisitSchedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseVisitScheduleRepository extends MongoRepository<HouseVisitSchedule, String> {

    @Query("{'visitType':?0,'memberId':?1,'visitRound':?2}")
    List<HouseVisitSchedule> findAllByDeliveryTypeAndVisitRound(String delivery, String motherId,String visitRound);

    List<HouseVisitSchedule> findAllByMemberId(String memberId);

    List<HouseVisitSchedule> findAllByVisitTypeAndMemberIdAndVisitRound(String birth_visit_1, String memberId, String visitRound);

    @Query("{'dueDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<HouseVisitSchedule> findAllByDateRange(Long startTime, Long endTime, String centerId);

    List<HouseVisitSchedule> findAllByVisitNameAndMemberIdAndVisitRound(String months, String memberId, String visitRound);
}
