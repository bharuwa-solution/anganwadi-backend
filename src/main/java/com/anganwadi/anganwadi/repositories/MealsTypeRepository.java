package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.MealsType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MealsTypeRepository extends MongoRepository<MealsType, String> {
    Optional<MealsType> findByItemCodeAndMealType(String itemCode, String mealType);

}
