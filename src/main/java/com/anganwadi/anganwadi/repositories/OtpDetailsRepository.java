package com.anganwadi.anganwadi.repositories;

import com.anganwadi.anganwadi.domains.entity.OtpDetails;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OtpDetailsRepository extends MongoRepository<OtpDetails, String> {
//    List<OtpDetails> findTopOneByMobileNumberAndOtp(String mobileNumber, String otp);

    List<OtpDetails> findTopOneByMobileNumberAndOtp(String mobileNumber, String otp);
}
