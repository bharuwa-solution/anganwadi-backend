package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Visits;
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

    List<Visits> findAllByMemberId(String memberId);

    Visits findByMemberIdAndVisitType(String memberId, String valueOf);

    List<Visits> findAllByMemberIdAndVisitType(String memberId, String valueOf);

    List<Visits> findAllByCenterName(String centerName);

    List<Visits> findAllByMemberIdAndCenterNameAndVisitType(String memberId, String centerName, String valueOf);

    List<Visits> findAllByMemberIdAndCenterName(String memberId, String centerName);

    @Query("{'centerName':?0,'category':{$regex:?1}}")
    List<Visits> findAllByCenterNameAndCategory(String centerName, String category);

    @Query("{'centerName':?0,'visitType':{$regex:?1},'category':{$regex:?2}}")
    List<Visits> findAllByCenterNameAndVisitTypeAndCategory(String centerName, String duration, String category);

    @Query("{$or:[{'visitType':'1'},{'visitType':'2'}],'createdDate':{$gte:?0,$lte:?1}}")
    List<Visits> findAllByPregnancyCriteria(Date startDate, Date endDate);

    @Query("{$or:[{'visitType':'1'},{'visitType':'2'}],}")
    List<Visits> findAllByPregnancySearchCriteria(String search);


}
