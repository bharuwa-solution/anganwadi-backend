package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.PregnantAndDelivery;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface PregnantAndDeliveryRepository extends MongoRepository<PregnantAndDelivery, String > {
    List<PregnantAndDelivery> findByFamilyId(String familyId);

    List<PregnantAndDelivery> findAllByCenterId(String centerId, Sort createdDate);


    List<PregnantAndDelivery> findAllByMotherMemberId(String id, Sort lastMissedPeriodDate);

    @Query("{'centerId':?0,'dateOfDelivery':{$eq:0}}")
    List<PregnantAndDelivery> findAllByCenterIdAndDateOfDelivery(String centerId, Sort createdDate);

    @Query("{'centerId':?0,'dateOfDelivery':{$gte:?1}}")
    List<PregnantAndDelivery> findAllByDeliveryCriteria(String centerId, long convertToMills, Sort dateOfDelivery);


    PregnantAndDelivery findTopOneByMotherMemberId(String motherMemberId);

    @Query("{'dateOfDelivery':{$eq:0}}")
    List<PregnantAndDelivery> findAllByDateOfDelivery();

    @Query("{'dateOfDelivery':{$gte:?0}}")
    List<PregnantAndDelivery> findAllBeneficiaryDharti(long millis);

    @Query("{'dateOfDelivery':{$eq:0},'createdDate':{$gte:?0,$lte:?1}}")
    List<PregnantAndDelivery> findAllByPregnancyCriteria(Date startTime, Date endTime);

    @Query(value = "{'dateOfDelivery':{$eq:0},'centerId':?0}",count = true)
    long countPregnantWomenByCenterId(String centerId);

    @Query(value = "{'dateOfDelivery':{$gte:?0},'centerId':?1}",count = true)
    long countDhartiWomenByCenterId(long convertToMills, String centerId);


}
