package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Vaccination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination,String> {
}
