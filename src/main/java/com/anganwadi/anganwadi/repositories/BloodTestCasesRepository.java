package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.BloodTestCases;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodTestCasesRepository extends MongoRepository<BloodTestCases,String > {
}
