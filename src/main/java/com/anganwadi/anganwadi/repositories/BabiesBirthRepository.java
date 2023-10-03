package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.BabiesBirth;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface BabiesBirthRepository extends MongoRepository<BabiesBirth, String> {

    @Query("{'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<BabiesBirth> findAllByMonth(Date startDate, Date endDate, String centerId);

    @Query("{'dob':{$gte:?0},'centerId':?1,'isActive':true,'deleted':false}")
    List<BabiesBirth> findAllByDobCriteria(long convertToMills,String centerId);

    List<BabiesBirth> findAllByChildId(String id);


    BabiesBirth findByChildIdAndDeletedIsFalse(String id);

    BabiesBirth findByChildId(String primaryId);

    List<BabiesBirth> findAllByMotherMemberId(String memberId);

    List<BabiesBirth> findAllByCenterId(String centerId);
}
