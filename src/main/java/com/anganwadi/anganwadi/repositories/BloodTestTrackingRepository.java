package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.BloodTestCases;
import com.anganwadi.anganwadi.domains.entity.BloodTestTracking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BloodTestTrackingRepository extends MongoRepository<BloodTestTracking, String > {

    List<BloodTestTracking> findAllByMotherIdAndVisitTypeAndVisitRound(String motherId, String visitType, String visitRound);

    List<BloodTestTracking> findByMotherId(String id);

    List<BloodTestTracking> findByMemberId(String id);
}
