package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.AnganwadiCenterDTO;
import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.dto.SendOtpDTO;
import com.anganwadi.anganwadi.domains.dto.UserDTO;
import com.anganwadi.anganwadi.domains.dto.VaccinationDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {


    OtpDTO sendOtp(SendOtpDTO sendOtpDTO) throws IOException;

    OtpDTO verifyOtp(OtpDTO otpDTO);

    List<AnganwadiCenterDTO> addAnganwadiCenters(List<AnganwadiCenterDTO> centersDTO);

    List<AnganwadiCenterDTO> getAnganwadiCenters();

    UserDTO registerUser(UserDTO userDTO);

	VaccinationDTO addVaccineData(String vaccineName);
}
