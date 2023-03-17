package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Visits;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitsRepository extends MongoRepository<Visits, String> {
    List<Visits> findAllByFamilyId(String familyId);
}
