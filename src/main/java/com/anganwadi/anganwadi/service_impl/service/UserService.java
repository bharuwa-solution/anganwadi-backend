package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.LoginUser;
import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.entity.User;

import java.io.IOException;

public interface UserService {

    User login(LoginUser loginUser) throws Exception;

    OtpDTO sendOtp(OtpDTO otpDTO) throws IOException;

    OtpDTO verifyOtp(OtpDTO otpDTO);

    OtpDTO verifyJwt(OtpDTO otpDTO);
}
