package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.AssetsStock;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssetsStockRepository extends MongoRepository<AssetsStock, String> {
    List<AssetsStock> findAllByCenterName(String centerName);

    List<AssetsStock> findAllByCenterNameAndItemCode(String centerName, String itemCode);
}
