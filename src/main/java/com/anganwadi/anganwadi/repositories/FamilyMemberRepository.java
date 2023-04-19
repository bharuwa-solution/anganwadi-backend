package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.FamilyMember;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface FamilyMemberRepository extends MongoRepository<FamilyMember, String> {
    List<FamilyMember> findAllByFamilyId(String familyId, Sort createdDate);

    long countByFamilyId(String familyId);

    @Query("{'dob':{$gte:?0}}")
    List<FamilyMember> findAllByDob(long convertToMills);

    @Query("{'gender':?0}")
    List<FamilyMember> findAllByGender(String female);

    @Query("{'relationWithOwner':'Self'}")
    List<FamilyMember> findAllByHusband();

    @Query("{'gender':'2','centerId':?0}")
    List<FamilyMember> findAllByGenderAndCenterId(String centerName);

    @Query("{'dob':{$gte:?0},'centerName':?1}")
    List<FamilyMember> findAllByDobAndCenterName(long convertToMills, String centerName);

    @Query("{'centerName':{$regex:?0},'recordForMonth':{$regex:?1},'category':{$regex:?2}}")
    List<FamilyMember> findAllByCenterNameAndRecordForMonthAndCategory(String centerName, String month, String category);

    @Query("{'category':{$regex:?0},'gender':{$regex:?1}, 'createdDate':{$gte:?2,$lte:?3}}")
    List<FamilyMember> findAllByChildrenCriteria(String caste, String gender, Date startTime, Date endTime);

    @Query("{'gender':{$regex:?0}}")
    List<FamilyMember> findByCategoryCriteria(String type);

    @Query("{'familyId':?0,'relationWithOwner':'0'}")
    FamilyMember findByHead(String familyId);

    @Query("{'_id':?0, 'name':{$regex:?1,'$options':i}}")
    List<FamilyMember> findAllByIdAndNameSearch(String memberId, String name);

    @Query("{'dob':{$gte:?0},'centerId':?1}")
    List<FamilyMember> findAllByDobAndCenterId(long convertToMills, String centerId);

    @Query("{'dob':{$gte:?1},'centerId':?0}")
    List<FamilyMember> findAllByCenterIdAndDob(String centerId, long convertToMills, Sort createdDate);

    @Query("{'gender':?0,'centerId':?1}")
    List<FamilyMember> findAllGenderByCenterId(String s, String centerId);

    @Query("{'dob':{$gte:?0}}")
    List<FamilyMember> findAllByChildrenUnder7Years(long convertToMills);

    @Query("{'dob':{$gte:?0}, 'centerId':?1}")
    List<FamilyMember> findAllChildrenUnder7YearsByCenterId(long convertToMills, String centerId);

    List<FamilyMember> findAllByCenterId(String centerId);
}
