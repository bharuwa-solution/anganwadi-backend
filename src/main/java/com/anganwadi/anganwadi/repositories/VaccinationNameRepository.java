package com.anganwadi.anganwadi.repositories;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.anganwadi.anganwadi.domains.entity.VaccinationName;

@Repository
public interface VaccinationNameRepository extends MongoRepository<VaccinationName, String>{
	
	
	VaccinationName findByVaccineName(String vaccineName);
	
	

}
