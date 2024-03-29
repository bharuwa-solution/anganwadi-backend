package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Visits;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VisitsRepository extends MongoRepository<Visits, String> {
    List<Visits> findAllByFamilyId(String familyId);

    @Query(value = "{'visitType':?0}", count = true)
    Long countByVisitType(String visitType);

    Visits findByMemberIdAndVisitType(String memberId, String valueOf);

    List<Visits> findAllByMemberIdAndVisitType(String memberId, String valueOf);

    List<Visits> findAllByCenterName(String centerName);

    List<Visits> findAllByMotherIdAndCenterIdAndVisitType(String memberId, String centerId, String valueOf);

    @Query("{'centerName':?0,'category':{$regex:?1}}")
    List<Visits> findAllByCenterNameAndCategory(String centerName, String category);

    @Query("{'centerName':?0,'visitType':{$regex:?1},'category':{$regex:?2}}")
    List<Visits> findAllByCenterNameAndVisitTypeAndCategory(String centerName, String duration, String category);

    @Query("{$or:[{'visitType':'1'},{'visitType':'2'}],'createdDate':{$gte:?0,$lte:?1}}")
    List<Visits> findAllByPregnancyCriteria(Date startDate, Date endDate);

    @Query("{$or:[{'visitType':'1'},{'visitType':'2'}]}")
    List<Visits> findAllByPregnancySearchCriteria(String search);

    @Query("{'visitFor':{$ne:'16'},'memberId':?0}")
    List<Visits> findAllByVisitForCriteria(String childId, Sort createdDate);

    @Query("{$and:[{'visitFor':{$ne:'16'}},{'visitFor':{$regex:?0}}],'centerId':?1}")
    List<Visits> findAllByVisitForSearchCriteria(String vaccineCode, String centerId, Sort createdDate);

    @Query("{$and:[{'visitFor':{$ne:'16'}},{'centerId':?0}]}")
    List<Visits> findAllByVisitForAndCenterId(String centerId, Sort createdDate);

    @Query("{$or:[{'visitType':'1'},{'visitType':'2'}],'memberId':?0}")
    List<Visits> findAllByLadiesBeneficiaryCriteria(String id);

    List<Visits> findAllByCenterId(String centerId);

    @Query("{'visitType':{$ne:'3'},'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<Visits> findAllByVaccinationCriteria(Date startTime, Date endTime, String centerId);

    void deleteByMemberId(String primaryId);

    List<Visits> findAllByMemberIdAndCenterId(String memberId, String centerId);

    List<Visits> findAllByMotherIdAndVisitType(String motherId, String visitType);

    List<Visits> findAllByMotherId(String id, Sort createdDate);

    List<Visits> findAllByMemberId(String memberId);

    List<Visits> findAllByMemberIdOrderByCreatedDateDesc(String memberId);

    @Query("{$or:[{'motherId':?0},{'memberId':?0}]},{$limit:1}")
    List<Visits> findAllByMotherIdOrMemberId(String memberId, Sort createdDate);
}
