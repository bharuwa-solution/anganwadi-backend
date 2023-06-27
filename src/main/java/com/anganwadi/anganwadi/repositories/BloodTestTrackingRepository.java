package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.BloodTestTracking;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodTestTrackingRepository extends MongoRepository<BloodTestTracking, String > {
}
