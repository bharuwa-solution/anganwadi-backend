package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.WeightTracking;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WeightTrackingRepository extends MongoRepository<WeightTracking, String> {

   List<WeightTracking> findAllByChildId(String childId, Sort createdDate);

    @Query(value = "{'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<WeightTracking> findAllByMonthCriteria(Date startTime, Date endTime, Sort createdDate, String centerId);

    List<WeightTracking> findByChildId(String childId, Sort createdDate);

    void deleteAllByChildId(String childId);

    void deleteByChildId(String primaryId);

    List<WeightTracking> findAllByMotherIdAndVisitTypeAndVisitRound(String memberId, String visitType, String visitRound);
}
