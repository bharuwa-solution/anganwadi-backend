package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.AnganwadiActivities;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnganwadiActivitiesRepository extends MongoRepository<AnganwadiActivities,String> {

    @Query("{'date':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<AnganwadiActivities> findAllByDateRange(long startTime, long endTime, String centerId);
}
