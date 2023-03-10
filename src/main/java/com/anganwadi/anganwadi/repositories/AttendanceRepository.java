package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.Attendance;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository extends MongoRepository<Attendance,String > {
}
