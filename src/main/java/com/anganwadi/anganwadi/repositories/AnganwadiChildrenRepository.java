package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface AnganwadiChildrenRepository extends MongoRepository<AnganwadiChildren, String> {

    AnganwadiChildren findByChildId(String attend);


    List<AnganwadiChildren> findAllByChildId(String attend);

    List<AnganwadiChildren> findAllByCenterName(String centerName);

    @Query("{'createdDate':{$gte:?0,$lte:?1}}")
    List<AnganwadiChildren> findAllByCreatedDate(Date startDate, Date lastDay);

    @Query("{'createdDate':{$gte:?0,$lte:?1},'name':{$regex:?2,'$options':i}}")
    List<AnganwadiChildren> findAllByCreatedDateAndSearch(Date startDate, Date endDate, String search);

    @Query("{'centerName':?0,'isRegistered':true}")
    List<AnganwadiChildren> findAllByCenterNameAndRegisteredTrue(String centerName);

    @Query("{'centerName':?0,'isRegistered':false}")
    List<AnganwadiChildren> findAllByCenterNameAndRegisteredFalse(String centerName);

    @Query("{'childId':?0,'isRegistered':true}")
    List<AnganwadiChildren> findAllByChildIdAndRegisteredTrue(String id);
}
