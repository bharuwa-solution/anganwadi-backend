package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Vaccination;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {
    List<Vaccination> findAllByVaccinationCodeAndCenterName(String vaccinationName, String centerName, Sort createdDate);

    List<Vaccination> findAllByCenterName(String centerName, Sort createdDate);

    @Query("{'month':{$regex:?0}}")
    List<Vaccination> findAllByMonth(String month);
}
