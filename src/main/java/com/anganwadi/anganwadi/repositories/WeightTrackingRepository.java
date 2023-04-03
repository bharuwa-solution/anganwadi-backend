package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.WeightTracking;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WeightTrackingRepository extends MongoRepository<WeightTracking, String> {

    List<WeightTracking> findAllByFamilyId(String familyId, Sort createdDate);

    @Query("{'month':{$regex:?0}}")
    List<WeightTracking> findAllByMonth(String month);

    List<WeightTracking> findAllByChildId(String childId, Sort createdDate);

    List<WeightTracking> findAllByCenterName(String centerName, Sort createdDate);
}
