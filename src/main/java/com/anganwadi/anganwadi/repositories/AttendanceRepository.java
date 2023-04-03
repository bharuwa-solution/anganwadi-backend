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

    @Query("{$and:[{'childId':{$in:[?0]}},{'date':?1}]}}")
    List<Attendance> updateAttendance(String childId, long timestamp);

    List<Attendance> findAllByDateAndCenterName(long timestamp, String centerName, Sort createdDate);

    List<Attendance> findAllByChildIdAndDateAndCenterName(String childId, long date, String centerName);

    @Query("{'date':{$gte:?0,$lte:?1}}")
    List<Attendance> findAllByDateRange(long startDayMillis, long lastDayMillis);
}
