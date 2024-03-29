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

    List<Vaccination> findAllByMotherIdAndVisitTypeAndVisitRound(String memberId, String visitType, String visitRound);
    List<Vaccination> findByChildId(String childId, Sort createdDate);

    @Query("{'centerId':?0,'vaccinationCode':{$regex:?1},'isActive':true,'deleted':false}")
    List<Vaccination> findAllByCenterIdAndVaccinationCode(String centerId, String code);

    void deleteAllByChildId(String primaryId);
    List<Vaccination> findByMotherId(String primaryId, Sort createdDate);
    void deleteAllByMotherId(String primaryId);

    @Query("{'centerId':{$regex:?2},'isActive':true,'deleted':false}")
    List<Vaccination> findAllByVaccinationCriteria(Date startTime, Date endTime, String centerId);
}
