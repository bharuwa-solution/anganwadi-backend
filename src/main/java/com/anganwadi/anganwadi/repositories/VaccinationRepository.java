package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Vaccination;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {

    List<Vaccination> findAllByMotherIdAndVisitTypeAndVisitRound(String memberId, String visitType, String visitRound);
}
