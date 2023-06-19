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

    @Query("{'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<Meals> findAllByMonthCriteria(Date startDate, Date endDate, Sort date, String centerId);

    @Query("{'mealType':?0,'centerId':?1,'date':?2}")
    List<Meals> findAllByMealTypeAndCenterIdAndDate(String mealType, String centerId, long date);

    @Query("{'date':{$gte:?0,$lte:?1},'centerId':?2}")
    List<Meals> findAllByDateRange(long startTime, long endTime, String centerId);
}
