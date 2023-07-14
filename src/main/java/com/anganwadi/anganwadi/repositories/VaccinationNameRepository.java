package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.VaccinationName;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationNameRepository extends MongoRepository<VaccinationName, String> {

    VaccinationName findByVaccineName(String vaccineName);

    @Aggregation(pipeline = {"{'$match':{'_id':{$ne:null}}}", "{$limit:1}", "{$sort:{'_id':-1}}"})
    List<VaccinationName> findTopOneById();
}
