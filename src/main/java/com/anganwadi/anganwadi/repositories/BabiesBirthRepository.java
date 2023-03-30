package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.BabiesBirth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BabiesBirthRepository extends MongoRepository<BabiesBirth, String> {

    @Query("{'month':{$regex:?0}}")
    List<BabiesBirth> findAllByMonth(String month);
}
