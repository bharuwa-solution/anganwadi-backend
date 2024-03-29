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

   // List<AnganwadiChildren> findAllByCenterName(String centerName);

    @Query("{'createdDate':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<AnganwadiChildren> findAllByCreatedDate(Date startDate, Date lastDay, String centerId);

    @Query("{'createdDate':{$lte:?1},'name':{$regex:?2,'$options':i},'centerId':{$regex:?3},'isActive':true,'deleted':false}")
    List<AnganwadiChildren> findAllByCreatedDateAndSearch(Date startDate, Date endDate, String search, String centerId);

  /*  @Query("{'centerName':?0,'isRegistered':true,'deleted':false}")
    List<AnganwadiChildren> findAllByCenterNameAndRegisteredTrue(String centerName);
*/
   /* @Query("{'centerName':?0,'isRegistered':false}")
    List<AnganwadiChildren> findAllByCenterNameAndRegisteredFalse(String centerName);
*/
    @Query("{'childId':?0,'isRegistered':true}")
    List<AnganwadiChildren> findAllByChildIdAndRegisteredTrue(String id);

    void deleteAllByChildId(String primaryId);

  /*  @Query("{'childId':{$in:[?0]}, 'centerName':?1,'isRegistered':true}")
    List<AnganwadiChildren> findAllByChildIdAndCenterNameAndRegisteredTrue(String childId, String centerName);
*/
    @Query(value = "{'centerId':?0,'isRegistered':true}", count = true)
    long countByCenterIdAndRegisteredTrue(String centerId);

    @Query("{'childId':?0,$or:[{'isRegistered':true,'deleted':false}]}")
    List<AnganwadiChildren> findAllByChildIdAndRegisteredTrueAndDeletedFalse(String id);

    @Query("{'centerId':?0,'isRegistered':true,'deleted':false,'isActive':true}")
	List<AnganwadiChildren> findAllByCenterIdAndRegisteredTrue(String centerId);


	List<AnganwadiChildren> findAllByCenterId(String centerId);
}
