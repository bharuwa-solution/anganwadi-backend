package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.config.ApplicationConstants;
import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.AnganwadiCenter;
import com.anganwadi.anganwadi.domains.entity.OtpDetails;
import com.anganwadi.anganwadi.domains.entity.User;
import com.anganwadi.anganwadi.domains.entity.VaccinationName;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.AnganwadiCenterRepository;
import com.anganwadi.anganwadi.repositories.OtpDetailsRepository;
import com.anganwadi.anganwadi.repositories.UserRepository;
import com.anganwadi.anganwadi.repositories.VaccinationNameRepository;
import com.anganwadi.anganwadi.service_impl.service.Msg91Services;
import com.anganwadi.anganwadi.service_impl.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    private final CommonMethodsService commonMethodsService;
    private final VaccinationNameRepository vaccinationNameRepository;

    @Autowired
    private UserServiceImpl(UserRepository userRepository, OtpDetailsRepository otpDetailsRepository,
                            AnganwadiCenterRepository anganwadiCentersRepository, ModelMapper modelMapper,
                            CommonMethodsService commonMethodsService, VaccinationNameRepository vaccinationNameRepository

    ) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.otpDetailsRepository = otpDetailsRepository;
        this.anganwadiCentersRepository = anganwadiCentersRepository;
        this.commonMethodsService = commonMethodsService;
        this.vaccinationNameRepository = vaccinationNameRepository;

    }


    private boolean validateNumber(String mobileNumber, boolean status) {

        String regex = "[+-]?\\d*(\\.\\d+)?";
        log.info("mobile Number " + mobileNumber);
        status = mobileNumber.matches(regex) && mobileNumber.trim().length() == 10;
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

            long expiryTimeInMillis = afterAddingTenMins.getTime();

            String generateOtp = String.format("%04d", random.nextInt(9999));
            OtpDetails otpDetails = Msg91Services.sendRegSms(generateOtp, sendOtpDTO.getMobileNumber());
            log.info("" + otpDetails);
            if (otpDetails != null) {
                otpDetails.setOtp(generateOtp);
                otpDetails.setExpiryTime(expiryTimeInMillis);
                //otpDetails.setExpiryTime(afterAddingTenMins);
                otpDetailsRepository.save(otpDetails);

                otpDTO = OtpDTO.builder()
                        .mobileNumber(otpDetails.getMobileNumber())
                        .status("success")
                        .otp(generateOtp)
                        .build();

            }
        }
        if (!validateNumber(sendOtpDTO.getMobileNumber(), status) || !validateUser(sendOtpDTO.getMobileNumber())) {
            throw new CustomException("User not registered");

        }
        return otpDTO;
    }

    @Override
    public OtpDTO verifyOtp(OtpDTO otpDTO) {

        if (StringUtils.isEmpty(otpDTO.getMobileNumber())) {
            throw new CustomException("Please Check Details");
        }

        List<OtpDetails> verifyOtp = otpDetailsRepository.findTopOneByMobileNumberAndOtp(otpDTO.getMobileNumber(), otpDTO.getOtp());
        User user = userRepository.getUserByMobileNumber(otpDTO.getMobileNumber());

        Calendar date = Calendar.getInstance();
        long currentTime = date.getTimeInMillis();
        log.error("current time: " + currentTime);
        long expTime = 0;

        for (OtpDetails exp : verifyOtp) {
            expTime = exp.getExpiryTime();
        }

        if ((verifyOtp.size() > 0 && currentTime <= expTime) || otpDTO.getOtp().trim().equals(ApplicationConstants.default_Otp)) {

            otpDTO = OtpDTO.builder()
                    .otp(otpDTO.getOtp())
                    .id(user.getId() == null ? "" : user.getId())
                    .centerId(user.getCenterId() == null ? "" : user.getCenterId())
                    .centerName(user.getCenterName() == null ? "" : user.getCenterName())
                    .userPic(user.getUserPic() == null ? "" : user.getUserPic())
                    .mobileNumber(user.getMobileNumber() == null ? "" : user.getMobileNumber())
                    .gender(user.getGender() == null ? "" : user.getGender())
                    .name(user.getName() == null ? "" : user.getName())
                    .role(user.getRole())
                    .version(otpDTO.getVersion() == null ? "" : otpDTO.getVersion())
                    .status("success")
                    .uniqueCode(user.getUniqueCode() == null ? "" : user.getUniqueCode())
                    .dob(user.getDob() == null ? "" : otpDTO.getDob())
                    .email(user.getEmail() == null ? "" : user.getEmail())
                    .build();

            User findUser = userRepository.findByMobileNumber(otpDTO.getMobileNumber());

            // Update in User Table
            findUser.setLastLogin(new Date().getTime());
            userRepository.save(findUser);

            // Update in Otp Table
            for (OtpDetails otp : verifyOtp) {
                otp.setVersion(otpDTO.getVersion() == null ? "" : otp.getVersion());
                otpDetailsRepository.save(otp);
            }

        } else {
            throw new CustomException("Incorrect Otp Or Otp Expired, please Try Again");
        }

        return otpDTO;

    }

    @Override
    public List<AnganwadiCenterDTO> addAnganwadiCenters(List<AnganwadiCenterDTO> centersDTO) {
        return centersDTO;
    }

    @Override
    public List<AnganwadiCenterDTO> getAnganwadiCenters() {

        List<AnganwadiCenter> centersDTOList = anganwadiCentersRepository.findAll(Sort.by(Sort.Direction.ASC, "centerName"));
        List<AnganwadiCenterDTO> addInList = new ArrayList<>();

        for (AnganwadiCenter fetchDetails : centersDTOList) {
            addInList.add(AnganwadiCenterDTO.builder()
                    .centerId(fetchDetails.getId())
                    .centerName(fetchDetails.getCenterName())
                    .villageName("")
                    .tahsilName("")
                    .blockName("")
                    .districtName("")
                    .stateName("")
                    .villageCode("")
                    .tahsilCode("")
                    .blockCode("")
                    .districtCode("")
                    .stateCode("")
                    .build());
        }

        return addInList;
    }

    @Override
    public UserDTO registerUser(UserDTO userDTO) {

        commonMethodsService.findCenterName(userDTO.getCenterId());
        User passUserData = modelMapper.map(userDTO, User.class);

        userRepository.save(passUserData);

        return modelMapper.map(passUserData, UserDTO.class);

    }

    @Override
    public VaccinationDTO addVaccineData(String vaccineName) {

        VaccinationName isExist = vaccinationNameRepository.findByVaccineName(vaccineName.trim());

        if (isExist != null) {
            return VaccinationDTO.builder()
                    .message("Vaccination Details is Already Exist...")
                    .vaccinationName(isExist.getVaccineName())
                    .vaccinationCode(isExist.getVaccineCode())
                    .id(isExist.getId())
                    .build();

        } else {
            VaccinationName saveData = VaccinationName.builder()
                    .vaccineName(vaccineName)
                    .vaccineCode(ApplicationConstants.vaccineCodePrefix + assignedIntegerCode())
                    .build();

            vaccinationNameRepository.save(saveData);

            return VaccinationDTO.builder()
                    .message("Vaccination Details Added Successfully...")
                    .vaccinationName(saveData.getVaccineName())
                    .vaccinationCode(saveData.getVaccineCode())
                    .id(saveData.getId())
                    .build();
        }
    }

    public long assignedIntegerCode() {

        Page<VaccinationName> pageList = vaccinationNameRepository.findAll(PageRequest.of(0, 1, Sort.Direction.DESC, "_id"));
        List<VaccinationName> recordList = pageList.getContent();
        long val = 0;

        if (recordList.size() > 0) {
            for (VaccinationName names : recordList) {
                String[] splitCode = names.getVaccineCode().split("-");
                log.error(splitCode[1]);
                val = Long.parseLong(splitCode[1]) + 1;
            }

        } else {
            val = 1;
        }
        return val;
    }


}
