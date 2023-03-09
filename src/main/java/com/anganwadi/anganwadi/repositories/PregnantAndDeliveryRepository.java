package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.PregnantAndDelivery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PregnantAndDeliveryRepository extends MongoRepository<PregnantAndDelivery, String > {
    List<PregnantAndDelivery> findByFamilyId(String familyId);
}
