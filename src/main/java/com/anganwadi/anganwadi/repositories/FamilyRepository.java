package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Family;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyRepository extends MongoRepository<Family, String> {
    List<Family> findAllByFamilyId(String familyId);

    List<Family> findAllByCenterName(String centerName, Sort createdDate);
}
