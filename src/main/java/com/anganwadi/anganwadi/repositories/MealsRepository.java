package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Meals;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealsRepository extends MongoRepository<Meals, String> {

    @Query("{'month':{$regex:?0}}")
    List<Meals> findAllBYMonth(String selectedMonth, Sort date);
}
