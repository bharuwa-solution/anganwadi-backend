package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,String > {

    User getUserByMobileNumber(String username);

    User findByMobileNumber(String mobileNumber);

}
