package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.BabiesBirth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BabiesBirthRepository extends MongoRepository<BabiesBirth, String> {

    @Query("{'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<BabiesBirth> findAllByMonth(Date startDate, Date endDate,String centerId);

    @Query("{'dob':{$gte:?0},'deleted':false}")
    List<BabiesBirth> findAllByDobCriteria(long convertToMills);

    BabiesBirth findByChildIdAndDeletedIsFalse(String id);

    List<BabiesBirth> findAllByChildId(String id);

    BabiesBirth findByIdAndDeletedIsFalse(String id);

    BabiesBirth findByChildId(String primaryId);
}
