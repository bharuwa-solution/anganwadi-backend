package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Meals;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MealsRepository extends MongoRepository<Meals, String> {

    @Query("{'createdDate':{$gte:?0,$lte:?1}}")
    List<Meals> findAllByMonthCriteria(Date startDate, Date endDate,Sort date);
}
