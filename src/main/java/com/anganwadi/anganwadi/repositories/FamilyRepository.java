package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Family;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FamilyRepository extends MongoRepository<Family, String> {
    List<Family> findAllByFamilyId(String familyId);

    @Query("{'familyId':{$in:[?0]}}")
    List<Family> findAllByFamilyIdIn(String familyId);

    Family findByFamilyId(String familyId);

    List<Family> findAllByCenterId(String centerId, Sort createdDate);

    @Query("{'createdDate':{$gte:?0,$lte:?1}}")
    List<Family> findByCategoryCriteria(Date startTime, Date endTime);
}
