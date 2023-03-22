package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.StockList;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockListRepository extends MongoRepository<StockList, String> {



}
