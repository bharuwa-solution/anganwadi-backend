package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.BabiesBirth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BabiesBirthRepository extends MongoRepository<BabiesBirth,String > {
}
