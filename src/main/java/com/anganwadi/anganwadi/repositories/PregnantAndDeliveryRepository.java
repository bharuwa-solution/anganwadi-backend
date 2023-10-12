package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.PregnantAndDelivery;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface PregnantAndDeliveryRepository extends MongoRepository<PregnantAndDelivery, String> {
    List<PregnantAndDelivery> findByFamilyId(String familyId);

    List<PregnantAndDelivery> findAllByCenterId(String centerId, Sort createdDate);

    List<PregnantAndDelivery> findAllByMotherMemberId(String id, Sort lastMissedPeriodDate);
//
//    @Query("{'centerId':?0,'dateOfDelivery':{$eq:0}}")
//    List<PregnantAndDelivery> findAllByCenterIdAndDateOfDelivery(String centerId, Sort createdDate);

    @Query("{'centerId':?0, 'isActive':?1 ,'deleted':?2 , 'dateOfDelivery':{$eq:0},'lastMissedPeriodDate':{$gt:0},'misCarriageDate':{$eq:0}}")
    List<PregnantAndDelivery> findAllPregnantWomen(String centerId, Sort createdDate, boolean isActive, boolean isDeleted);

    @Query("{'centerId':?0,'dateOfDelivery':{$gte:?1},'isActive':true, 'deleted':false}")
    List<PregnantAndDelivery> findAllByDeliveryCriteria(String centerId, long convertToMills, Sort dateOfDelivery);


    PregnantAndDelivery findTopOneByMotherMemberId(String motherMemberId);

    @Query("{'dateOfDelivery':{$eq:0}}")
    List<PregnantAndDelivery> findAllByDateOfDelivery();

    @Query("{'centerId':{$regex:?2},'dateOfDelivery':{$gte:?3},'isActive':true, 'deleted':false}")
    List<PregnantAndDelivery> findAllBeneficiaryDharti(Long startTime, Long endTime, String centerId, long millis);

    @Query("{'centerId':{$regex:?0},'dateOfDelivery':{$gte:?1},'isActive':true, 'deleted':false}")
    List<PregnantAndDelivery> findAllDashboardFamilyBeneficiaryDharti(String centerId, long millis, String[] ignoreCenters);

    @Query("{'dateOfDelivery':{$eq:0},'centerId':{$regex:?2},'isActive':true, 'deleted':false}")
    List<PregnantAndDelivery> findAllByPregnancyCriteria(Long startTime, Long endTime, String centerId);

    @Query("{'centerId':{$regex:?0},'dateOfDelivery':{$eq:0},'isActive':true, 'deleted':false}")
    List<PregnantAndDelivery> findAllDashboardFamilyPregnancyCriteria(String centerId, String[] ignoreCenters);

    @Query(value = "{'dateOfDelivery':{$eq:0},'centerId':?0}", count = true)
    long countPregnantWomenByCenterId(String centerId);

    @Query(value = "{'dateOfDelivery':{$gte:?0},'centerId':?1}", count = true)
    long countDhartiWomenByCenterId(long convertToMills, String centerId);

    @Query("{'centerId':{$regex:?0},'category':{$regex:?1},'createdDate':{$gte:?2,$lte:?3},'isActive':true, 'deleted':false}")
    List<PregnantAndDelivery> findAllByCenterNameAndCategoryAndCreatedDate(String centerId, String category, Date startDate, Date endDate);

    @Query("{'dateOfDelivery':{$gt:0},'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<PregnantAndDelivery> findAllDhartiCriteria(Date startTime, Date endTime, String centerId);

    void deleteByMotherMemberId(String primaryId);

    @Query("{'regDate': {$gte: ?0, $lte: ?1}, 'centerId': {$not:{$in: ?4},$regex: ?2 }, 'dateOfDelivery' : { $gte : ?3}}")
    List<PregnantAndDelivery> findAllBeneficiaryDhartiByActiveCenters(Long startTime, Long endTime, String centerId, long millis, String [] inactiveCenterIds);

    @Query("{'dateOfDelivery': { $eq : 0}, 'regDate' : { $gte : ?0, $lte: ?1},'centerId': {$not:{$in: ?3},'$regex': ?2 }}")
    List<PregnantAndDelivery> findAllByPregnancyCriteriaByActiveCenters(Long startTime, Long endTime, String centerId, String[] ignoreCenters);

    @Query("{'motherMemberId':?0,'dateOfDelivery':{$eq:0},'lastMissedPeriodDate':{$gt:0},'misCarriageDate':{$eq:0},{$limit:1}'}")
    List<PregnantAndDelivery> findByMotherMemberIdAndDeliveryCriteria(String id);


    @Query(value = "{'centerId':{$regex:?0},'category':{$regex:?1},'dateOfDelivery':{$gte:?2,$lte:?3},'isActive':true, 'deleted':false}",count = true)
    Long countByCenterIdAndCategoryAndDateOfDelivery(String centerId, String category, long startDate, long endDate);

    @Query(value = "{'centerId':{$regex:?0},'category':{$regex:?1},'lastMissedPeriodDate':{$lte:?2},'isActive':true, 'deleted':false}",count = true)
    Long countByCenterIdAndCategoryAndLastMissedPeriodDate(String centerId, String category, long lastMissedPeriodDate);
}
