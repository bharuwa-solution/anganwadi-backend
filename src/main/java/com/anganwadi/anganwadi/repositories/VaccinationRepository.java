package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Vaccination;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VaccinationRepository extends MongoRepository<Vaccination, String> {

    List<Vaccination> findAllByMotherIdAndVisitTypeAndVisitRound(String memberId, String visitType, String visitRound);

    List<Vaccination> findByChildId(String childId, Sort createdDate);

    @Query("{'centerId':?0,'vaccinationCode':{$regex:?1}}")
    List<Vaccination> findAllByCenterIdAndVaccinationCode(String centerId, String code);
}
