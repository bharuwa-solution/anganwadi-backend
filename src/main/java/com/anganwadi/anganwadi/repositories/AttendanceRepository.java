package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Attendance;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    List<Attendance> findAllByDate(long date, Sort createdDate);


    @Query("{'childId':?0}")
    List<Attendance> findAllByChildId(String childId);

    @Query("{'childId':{$in:[?0]},'date':?1}")
    List<Attendance> findAllByChildIdAndDate(String childId, long timestamp);

    @Query("{$and:[{'childId':{$in:[?0]}},{'date':?1},{'centerName':?2}]}}")
    List<Attendance> updateAttendance(String childId, long timestamp, String centerId);

    List<Attendance> findAllByChildIdAndDateAndCenterId(String childId, long date, String centerId);

    @Query("{'date':{$gte:?0,$lte:?1},'centerId':{$regex:?2}}")
    List<Attendance> findAllByDateRange(long startDayMillis, long lastDayMillis, String centerId);

    @Query("{'date':?0,'centerId':?1,'isRegistered':true}")
    List<Attendance> findAllByDateAndCenterIdAndRegistered(long timestamp, String centerId, Sort createdDate);

    void deleteAllByChildId(String primaryId);

    List<Attendance> findByDateAndChildId(long format, String childId);

    @Query("{'date':?0,'centerId':{$regex:?1}}")
    List<Attendance> findAllByDateAndCenterId(long date, String centerId, Sort createdDate);

    @Query(value = "{'date':?0,'centerName':?1,'attendance':'P','isRegistered':true}", count = true)
    long countByDateAndCenterId(long time, String centerId, Sort createdDate);
}
