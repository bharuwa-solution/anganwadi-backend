package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.LoginUser;
import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.entity.User;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.UserRepository;
import com.anganwadi.anganwadi.service_impl.service.UserService;
import com.anganwadi.anganwadi.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;


    @Autowired
    private UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }


    @Override
    public User login(LoginUser loginUser) throws Exception {
        if (loginUser.getUsername().equals(null) || loginUser.getPassword().equals(null)) {
            throw new CustomException("Credentials are not valid");
        }

        User user = userRepository.getUserByMobileNumber(loginUser.getUsername());
        log.info("users " + user);
        if (user == null) {
            throw new NoSuchElementException(loginUser.getUsername());
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginUser.getPassword(), user.getPassword())) {
            log.error("encoder===ifff===", encoder);
            throw new CustomException("Invalid Credentials. Please Try Again");
        }

//        String token = jwtUtils.generateToken(user);

        return null;
    }

    private boolean validateNumber(String mobileNumber, boolean status) {

        String regex = "[+-]?\\d*(\\.\\d+)?";
        log.info("mobile Number " + mobileNumber);
        if (!mobileNumber.matches(regex) || mobileNumber.trim().length() != 10) {
            status = false;
        } else {
            status = true;
        }
        return status;
    }

    @Override
    public OtpDTO sendOtp(OtpDTO otpDTO) {

        if (StringUtils.isEmpty(otpDTO.getMobileNumber())) {
            throw new CustomException("Please Check Your Mobile Number");
        }

        if (validateNumber(otpDTO.getMobileNumber(), false)) {

        }

        return null;
    }
}
