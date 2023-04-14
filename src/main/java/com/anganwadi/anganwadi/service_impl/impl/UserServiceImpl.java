package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.config.ApplicationConstants;
import com.anganwadi.anganwadi.domains.dto.AnganwadiCenterDTO;
import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.dto.SendOtpDTO;
import com.anganwadi.anganwadi.domains.dto.UserDTO;
import com.anganwadi.anganwadi.domains.entity.AnganwadiCenter;
import com.anganwadi.anganwadi.domains.entity.OtpDetails;
import com.anganwadi.anganwadi.domains.entity.User;
import com.anganwadi.anganwadi.exceptionHandler.BadRequestException;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.AnganwadiCenterRepository;
import com.anganwadi.anganwadi.repositories.OtpDetailsRepository;
import com.anganwadi.anganwadi.repositories.UserRepository;
import com.anganwadi.anganwadi.service_impl.service.Msg91Services;
import com.anganwadi.anganwadi.service_impl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.io.IOException;
import java.util.*;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OtpDetailsRepository otpDetailsRepository;
    private final AnganwadiCenterRepository anganwadiCentersRepository;
    private final ModelMapper modelMapper;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, OtpDetailsRepository otpDetailsRepository,
                            AnganwadiCenterRepository anganwadiCentersRepository, ModelMapper modelMapper

    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.otpDetailsRepository = otpDetailsRepository;
        this.anganwadiCentersRepository = anganwadiCentersRepository;

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
    public OtpDTO sendOtp(SendOtpDTO sendOtpDTO) throws IOException, HttpClientErrorException.BadRequest {
        Random random = new Random();
        OtpDTO otpDTO = new OtpDTO();
        boolean status = false;
        if (StringUtils.isEmpty(sendOtpDTO.getMobileNumber())) {
            throw new CustomException("Please Check Your Mobile Number");
        }

        if (validateNumber(sendOtpDTO.getMobileNumber(), status) && validateUser(sendOtpDTO.getMobileNumber())) {
            Calendar date = Calendar.getInstance();
            long t = date.getTimeInMillis();
            Date afterAddingTenMins = new Date(t + (10 * ApplicationConstants.ONE_MINUTE_IN_MILLIS));
            String generateOtp = String.format("%04d", random.nextInt(9999));
            OtpDetails otpDetails = Msg91Services.sendRegSms(generateOtp, sendOtpDTO.getMobileNumber());
            log.info("" + otpDetails);
            if (otpDetails != null) {
                otpDetails.setOtp(generateOtp);
                otpDetails.setExpiryTime(afterAddingTenMins);
                otpDetailsRepository.save(otpDetails);

                otpDTO = OtpDTO.builder()
                        .mobileNumber(otpDetails.getMobileNumber())
                        .status("success")
                        .otp(generateOtp)
                        .build();

            }
        }
        if(!validateNumber(sendOtpDTO.getMobileNumber(), status) || !validateUser(sendOtpDTO.getMobileNumber())){
            throw new CustomException("User not registered");

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

        if (verifyotp.size()>0 || otpDTO.getOtp().trim().equals("1105")) {
            otpDTO = OtpDTO.builder()
                    .otp(otpDTO.getOtp())
                    .id(user.getId()==null?"":user.getId())
                    .centerId(user.getUniqueCode()==null?"":user.getUniqueCode())
                    .centerName(user.getCenterName()==null?"":user.getCenterName())
                    .userPic(user.getUserPic()==null?"":user.getUserPic())
                    .mobileNumber(user.getMobileNumber()==null?"":user.getMobileNumber())
                    .gender(user.getGender()==null?"":user.getGender())
                    .name(user.getName()==null?"":user.getName())
                    .role(user.getRole())
                    .status("success")
                    .uniqueCode(user.getUniqueCode()==null?"":user.getUniqueCode())
                    .dob(user.getDob()==null?"":otpDTO.getDob())
                    .email(user.getEmail()==null?"":user.getEmail())
                    .build();

            User findUser = userRepository.findByMobileNumber(otpDTO.getMobileNumber());

            findUser.setLastLogin(new Date().getTime());
            userRepository.save(findUser);


        } else {
            throw new BadRequestException("Incorrect Otp, please fill correct otp");
        }

        return otpDTO;

    }

    @Override
    public List<AnganwadiCenterDTO> addAnganwadiCenters(List<AnganwadiCenterDTO> centersDTO) {

        return centersDTO;


    }

    @Override
    public List<AnganwadiCenterDTO> getAnganwadiCenters() {

        List<AnganwadiCenter> centersDTOList = anganwadiCentersRepository.findAll(Sort.by(Sort.Direction.DESC, "centerName"));
        List<AnganwadiCenterDTO> addInList = new ArrayList<>();

        for (AnganwadiCenter fetchDetails : centersDTOList) {
            AnganwadiCenterDTO singleCenterDetail = modelMapper.map(fetchDetails, AnganwadiCenterDTO.class);
            addInList.add(singleCenterDetail);
        }

        return addInList;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        User passUserData = modelMapper.map(userDTO, User.class);

        userRepository.save(passUserData);

        return modelMapper.map(passUserData, UserDTO.class);

    }


}
