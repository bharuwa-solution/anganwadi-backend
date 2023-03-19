package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnganwadiChildrenRepository extends MongoRepository<AnganwadiChildren, String> {

    AnganwadiChildren findByChildId(String attend);


    List<AnganwadiChildren> findAllByChildId(String attend);

    List<AnganwadiChildren> findAllByCenterName(String centerName);
}
