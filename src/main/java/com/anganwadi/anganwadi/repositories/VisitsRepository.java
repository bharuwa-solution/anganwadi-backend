package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Visits;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitsRepository extends MongoRepository<Visits, String> {
    List<Visits> findAllByFamilyId(String familyId);

    @Query(value = "{'visitType':?0}", count = true)
    Long countByVisitType(String visitType);

    List<Visits> findAllByMemberId(String memberId);
}
