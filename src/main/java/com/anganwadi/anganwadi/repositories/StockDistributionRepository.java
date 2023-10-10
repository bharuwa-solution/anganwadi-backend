package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.StockDistribution;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface StockDistributionRepository extends MongoRepository<StockDistribution, String> {
//    List<StockDistribution> findAllByCenterName(String centerName);

    // List<StockDistribution> findAllByCenterNameAndMonth(String centerName, String selectedMonth);

    List<StockDistribution> findAllByFamilyIdAndDate(String familyId, Long  selectedMonth);

    List<StockDistribution> findAllByFamilyIdAndItemCodeAndDate(String familyId, String itemCode, Long selectedMonth, Sort itemCode1);

    @Query("{'date':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<StockDistribution> findAllByDistributionCriteria(Long startTime, Long endTime, String centerId);

    @Query("{'centerId':?0,date:{$gte:?1,$lte:?2}}")
    List<StockDistribution> findAllByCenterIdAndDateRange(String centerId, long startDataRange, long endDataRange);

    void deleteAllByFamilyId(String familyId);

    List<StockDistribution> findAllByFamilyId(String familyId);
}
