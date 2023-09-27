package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.PregnantAndDelivery;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PregnantAndDeliveryRepository extends MongoRepository<PregnantAndDelivery, String> {
    List<PregnantAndDelivery> findByFamilyId(String familyId);

    List<PregnantAndDelivery> findAllByCenterId(String centerId, Sort createdDate);


    List<PregnantAndDelivery> findAllByMotherMemberId(String id, Sort lastMissedPeriodDate);

    @Query("{'centerId':?0,'dateOfDelivery':{$eq:0}}")
    List<PregnantAndDelivery> findAllByCenterIdAndDateOfDelivery(String centerId, Sort createdDate);

    @Query("{'centerId':?0,'dateOfDelivery':{$eq:0},'lastMissedPeriodDate':{$gt:0},'misCarriageDate':{$eq:0}}")
    List<PregnantAndDelivery> findAllPregnantWomen(String centerId, Sort createdDate);

    @Query("{'centerId':?0,'dateOfDelivery':{$gte:?1}}")
    List<PregnantAndDelivery> findAllByDeliveryCriteria(String centerId, long convertToMills, Sort dateOfDelivery);


    PregnantAndDelivery findTopOneByMotherMemberId(String motherMemberId);

    @Query("{'dateOfDelivery':{$eq:0}}")
    List<PregnantAndDelivery> findAllByDateOfDelivery();

    @Query("{'regDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2},'dateOfDelivery':{$gte:?3}}")
    List<PregnantAndDelivery> findAllBeneficiaryDharti(Long startTime, Long endTime, String centerId, long millis);

    @Query("{$and:[{'centerId':{$regex:?0,$nin:?2}}],'dateOfDelivery':{$gte:?1}}")
    List<PregnantAndDelivery> findAllDashboardFamilyBeneficiaryDharti(String centerId, long millis, String[] ignoreCenters);

    @Query("{'dateOfDelivery':{$eq:0},'regDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<PregnantAndDelivery> findAllByPregnancyCriteria(Long startTime, Long endTime, String centerId);

    @Query("{$and:[{,'centerId':{$regex:?0,$nin:?1}}],'dateOfDelivery':{$eq:0}}")
    List<PregnantAndDelivery> findAllDashboardFamilyPregnancyCriteria(String centerId, String[] ignoreCenters);

    @Query(value = "{'dateOfDelivery':{$eq:0},'centerId':?0}", count = true)
    long countPregnantWomenByCenterId(String centerId);

    @Query(value = "{'dateOfDelivery':{$gte:?0},'centerId':?1}", count = true)
    long countDhartiWomenByCenterId(long convertToMills, String centerId);

    @Query("{'centerName':{$regex:?0},'category':{$regex:?1},'createdDate':{$gte:?2,$lte:?3}}")
    List<PregnantAndDelivery> findAllByCenterNameAndCategoryAndCreatedDate(String centerName, String category, Date startDate, Date endDate);

    @Query("{'dateOfDelivery':{$gt:0},'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<PregnantAndDelivery> findAllDhartiCriteria(Date startTime, Date endTime, String centerId);

    void deleteByMotherMemberId(String primaryId);

    @Query("{'regDate': {$gte: ?0, $lte: ?1}, 'centerId': {$not:{$in: ?4},$regex: ?2 }, 'dateOfDelivery' : { $gte : ?3}}")
    List<PregnantAndDelivery> findAllBeneficiaryDhartiByActiveCenters(Long startTime, Long endTime, String centerId, long millis, String [] inactiveCenterIds);

    @Query("{'dateOfDelivery': { $eq : 0}, 'regDate' : { $gte : ?0, $lte: ?1},'centerId': {$not:{$in: ?3},'$regex': ?2 }}")
    List<PregnantAndDelivery> findAllByPregnancyCriteriaByActiveCenters(Long startTime, Long endTime, String centerId, String[] ignoreCenters);
}
