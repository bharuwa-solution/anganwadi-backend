package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.AnganwadiCenter;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnganwadiCenterRepository extends MongoRepository<AnganwadiCenter, String> {
}
