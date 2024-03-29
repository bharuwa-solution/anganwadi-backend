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

    @Query("{'gender':'2','centerId':?0, 'isActive':?1, 'deleted':?2}")
    List<FamilyMember> findAllByGenderAndCenterId(String centerName,boolean isActive,boolean deleted );

    @Query("{'dob':{$gte:?0},'centerName':?1}")
    List<FamilyMember> findAllByDobAndCenterName(long convertToMills, String centerName);

    @Query("{'centerId':{$regex:?0},'createdDate':{$gte:?1,$lte:?2},'category':{$regex:?3},'isActive':true, 'deleted':false}")
    List<FamilyMember> findAllByCenterNameAndRecordForMonthAndCategory(String centerId, Date startDate, Date endDate, String category);

    @Query("{'createdDate':{$gte:?0,$lte:?1},'dob':{$gte:?2}}")
    List<FamilyMember> findAllByChildrenCriteria(Date startTime, Date endTime, long millis);

    @Query("{'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<FamilyMember> findByCategoryCriteria(Date startTime, Date endTime, String centerId);

    @Query("{'familyId':?0,'relationWithOwner':'0'}")
    FamilyMember findByHead(String familyId);

    @Query("{'_id':?0, 'name':{$regex:?1,'$options':i}}")
    List<FamilyMember> findAllByIdAndNameSearch(String memberId, String name);
   
    @Query("{'dob':{$gte:?0,$lte:?2},'centerId':{$regex:?1},'dateOfMortality':''}")
    List<FamilyMember> findAllByDobAndCenterId(long convertToMills, String centerId,long timeLess3Yrs);
   
    @Query("{'dob':{$gte:?1},'centerId':?0,'isActive':true, 'deleted':false}")
    List<FamilyMember> findAllByCenterIdAndDob(String centerId, long convertToMills, Sort createdDate);

    @Query("{'gender':?0,'centerId':?1}")
    List<FamilyMember> findAllGenderByCenterId(String s, String centerId);

    @Query("{'dob':{$gte:?0}}")
    List<FamilyMember> findAllByChildrenUnder7Years(long convertToMills);

    @Query("{'dob':{$gte:?0}, 'centerId':?1}")
    List<FamilyMember> findAllChildrenUnder7YearsByCenterId(long convertToMills, String centerId);

    List<FamilyMember> findAllByCenterId(String centerId);

    @Query("{'dob':{$gte:?0},'centerId':?1,'deleted':false, 'dateOfMortality': ''}")
    List<FamilyMember> findAllByDobCriteria(long convertToMills, String centerId);

    @Query("{'familyId':?0,'name':?1}")
    List<FamilyMember> findByFamilyIdAndName(String familyId, String motherName);

    @Query("{'centerId':?0,'dob':{$gte:?1},'isActive':true, 'deleted':false}")
    List<FamilyMember> findAllFamilyChildrenByCenterId(String centerId, long convertToMills, Sort createdDate);

    @Query("{'createdDate':{$lte:?1},'centerId':{$regex:?2},'dob':{$gte:?3},'isActive':true, 'deleted':false}")
    List<FamilyMember> findAllBeneficiaryChildren(Date startTime, Date endTime, String centerId, long millis);

    @Query("{'centerId':{$regex:?0},'dob':{$gte:?1},'isActive':true, 'deleted':false}")
    List<FamilyMember> findAllDashboardFamilyChildren(String centerId, long millis, String[] ignoreCenters);

    @Query(value = "{'dob':{$gte:?0}, 'centerId':?1}", count = true)
    long countChildrenByCenterId(long convertToMills, String centerId);

	//List<FamilyMember> findAllByChildId(String childId);

    @Query("{'familyId':?0,'name':?1}")
    List<FamilyMember> findAllByFamilyIdAndName(String familyId, String name);

//    @Query("{'centerName':?0}")
//    List<FamilyMember> findAllByCenterName(String centerName);

//    List<FamilyMember> findAllById(String childId);

//    Family findByFamilyId(String familyId);

    @Query("{'_id':?0,'dob':{$lte:?1,$gte:?2},'dateOfMortality':''}")
    List<FamilyMember> findAllByIdAndDob(String childId, long convertToMills, long convertToMills_2);

    @Query("{'createdDate': {$gte : { $date : ?0}, $lte : { $date : ?1}}, 'centerId': {$not:{$in: ?4},$regex: ?2 }, 'dob' : { $gte : ?3}}")
    List<FamilyMember> findAllBeneficiaryChildrenOfActiveCenters(Date startTime, Date endTime, String centerId, long millis, String [] inactiveCenterIds);

    @Query(value = "{'centerId':{$regex:?0},'gender':{$regex:?1},'category':{$regex:?2},'createdDate':{$gte:?3,$lte:?4},'isActive':true, 'deleted':false}",count = true)
    Long countByCenterIdAndGenderAndCategoryAndCreatedDate(String centerId,String gender, String category, Date startDate, Date endDate);

    @Query(value = "{'centerId':{$regex:?0},'category':{$regex:?1},'createdDate':{$gte:?2,$lte:?3},'isActive':false, 'deleted':true}",count = true)
    Long countByAndCenterId(String centerId,String category, Date startDate, Date endDate);

    @Query(value ="{'centerId':{$regex:?0},'category':{$regex:?1},'dob':{$gte:?2,$lte:?3},'isActive':true, 'deleted':false}",count = true )
    Long countByDob(String centerId,String category, long startDate, long endDate);

}
