package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.StockDistribution;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockDistributionRepository extends MongoRepository<StockDistribution, String> {
    List<StockDistribution> findAllByCenterName(String centerName);

    List<StockDistribution> findAllByCenterNameAndMonth(String centerName, String selectedMonth);

    List<StockDistribution> findAllByFamilyIdAndMonth(String familyId, String selectedMonth);

    List<StockDistribution> findAllByFamilyIdAndItemCodeAndMonth(String familyId, String itemCode, String selectedMonth, Sort itemCode1);
}
