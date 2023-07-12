package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.VaccinationName;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VaccinationNameRepository extends MongoRepository<VaccinationName, String>{
	
	VaccinationName findByVaccineName(String vaccineName);
	
	

}
