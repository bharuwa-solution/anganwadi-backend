package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.FamilyMember;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FamilyMemberRepository extends MongoRepository<FamilyMember, String> {
    List<FamilyMember> findAllByFamilyId(String familyId, Sort createdDate);

    long countByFamilyId(String familyId);

    @Query("{'dob':{$gte:?0}}")
    List<FamilyMember> findAllByDob(long convertToMills);

    @Query("{'gender':'2'}")
    List<FamilyMember> findAllByGender();

    @Query("{'relationWithOwner':'Self'}")
    List<FamilyMember> findAllByHusband();
}
