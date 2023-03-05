package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.config.ApplicationConstants;
import com.anganwadi.anganwadi.domains.dto.LoginUser;
import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.entity.OtpDetails;
import com.anganwadi.anganwadi.domains.entity.User;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.OtpDetailsRepository;
import com.anganwadi.anganwadi.repositories.UserRepository;
import com.anganwadi.anganwadi.service_impl.service.Msg91Services;
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

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl extends ApplicationConstants implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final OtpDetailsRepository otpDetailsRepository;
    private final JwtUtils jwtUtils;
    ;


    @Autowired
    private UserServiceImpl(UserRepository userRepository, JwtUtils jwtUtils, OtpDetailsRepository otpDetailsRepository
    ) {
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
        this.otpDetailsRepository = otpDetailsRepository;

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

    private boolean validateUser(String mobileNumber) {
        boolean isAvailable = false;
        User checkUser = userRepository.findByMobileNumber(mobileNumber);
        log.info("user " + checkUser);
        if (checkUser != null) {
            isAvailable = true;
        }
        return isAvailable;
    }


    @Override
    public OtpDTO sendOtp(OtpDTO otpDTO) throws IOException {
        Random random = new Random();
        boolean status = false;
        if (StringUtils.isEmpty(otpDTO.getMobileNumber())) {
            throw new CustomException("Please Check Your Mobile Number");
        }

        if (validateNumber(otpDTO.getMobileNumber(), status) && validateUser(otpDTO.getMobileNumber())) {
            Calendar date = Calendar.getInstance();
            long t = date.getTimeInMillis();
            Date afterAddingTenMins = new Date(t + (10 * ApplicationConstants.ONE_MINUTE_IN_MILLIS));
            String generateOtp = String.format("%04d", random.nextInt(9999));
            OtpDetails otpDetails = Msg91Services.sendRegSms(generateOtp, otpDTO.getMobileNumber());
            log.info("" + otpDetails);
            if (otpDetails != null) {
                otpDetails.setOtp(generateOtp);
                otpDetails.setExpiryTime(afterAddingTenMins);
                otpDetailsRepository.save(otpDetails);

                otpDTO = OtpDTO.builder()
                        .mobileNumber(otpDetails.getMobileNumber())
                        .status("success")
                        .authToken("")
                        .otp(generateOtp)
                        .build();
            }
        } else {
            otpDTO = OtpDTO.builder()
                    .mobileNumber(otpDTO.getMobileNumber())
                    .otp("")
                    .status("fail")
                    .authToken("")
                    .build();
        }

        return otpDTO;
    }

    @Override
    public OtpDTO verifyOtp(OtpDTO otpDTO) {

        if (StringUtils.isEmpty(otpDTO.getMobileNumber())) {
            throw new CustomException("Please Check Details");
        }

        List<OtpDetails> verifyotp = otpDetailsRepository.findTopOneByMobileNumberAndOtp(otpDTO.getMobileNumber(), otpDTO.getOtp());
        User user = userRepository.getUserByMobileNumber(otpDTO.getMobileNumber());
        String token = jwtUtils.generateToken(user);
        if (verifyotp != null) {
            otpDTO = OtpDTO.builder()
                    .otp(otpDTO.getOtp())
                    .status("success")
                    .mobileNumber(otpDTO.getMobileNumber())
                    .authToken(token)
                    .build();

        } else {
            throw new CustomException("Otp Ver1fy Failed, Please Re-Send Otp");
        }

        return otpDTO;

    }


}
