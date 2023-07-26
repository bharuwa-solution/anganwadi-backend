package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.*;

import java.io.IOException;
import java.util.List;

public interface UserService {


    OtpDTO sendOtp(SendOtpDTO sendOtpDTO) throws IOException;

    OtpDTO verifyOtp(OtpDTO otpDTO);

    AnganwadiCenterDTO addAnganwadiCenters(AnganwadiCenterDTO centersDTO);

    List<AnganwadiCenterDTO> getAnganwadiCenters();

    UserDTO registerUser(UserDTO userDTO);

    VaccinationDTO addVaccineData(String vaccineName);
}
