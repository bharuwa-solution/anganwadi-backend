package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.VaccinationName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationNameRepository extends MongoRepository<VaccinationName, String> {

    VaccinationName findByVaccineName(String vaccineName);

    @Query(value = "{}", sort = "{_id:-1}")
    List<VaccinationName> findTopOneById();
}
