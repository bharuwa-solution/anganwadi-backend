package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Vaccination;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {
    List<Vaccination> findAllByVaccinationCodeAndCenterId(String vaccinationName, String centerId, Sort createdDate);

    List<Vaccination> findAllByCenterId(String centerName, Sort createdDate);

    @Query("{'createdDate':{$gte:?0,$lte:?1}}")
    List<Vaccination> findAllByMonthCriteria(Date startDate, Date endDate);

    List<Vaccination> findAllByChildId(String childId, Sort createdDate);
}
