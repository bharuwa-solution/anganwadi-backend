package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Attendance;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance, String> {
    List<Attendance> findAllByDate(long date, Sort createdDate);
}
