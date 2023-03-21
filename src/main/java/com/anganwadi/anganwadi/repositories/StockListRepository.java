package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.StockList;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StockListRepository extends MongoRepository<StockList, String> {
}
