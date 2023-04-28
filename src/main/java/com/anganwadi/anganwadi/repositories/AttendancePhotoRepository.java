package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.AttendancePhoto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendancePhotoRepository extends MongoRepository<AttendancePhoto, String> {
}
