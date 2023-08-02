package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.AssetsStock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetsStockRepository extends MongoRepository<AssetsStock, String> {
    List<AssetsStock> findAllByCenterName(String centerName);

    List<AssetsStock> findAllByCenterNameAndItemCode(String centerName, String itemCode);

    List<AssetsStock> findAllByCenterNameAndMonthOrderByDateDesc(String centerName, String selectedMonth);

    List<AssetsStock> findAllByCenterNameAndItemCodeAndMonth(String centerName, String itemCode, String selectedMonth);

    @Query("{'date':{$gte:?1,$lte:?2},'centerId':?0}")
    List<AssetsStock> findAllByCenterIdAndDateRange(String centerid, long startTime, long endTime);
}
