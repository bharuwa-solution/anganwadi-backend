package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.AnganwadiCentersDTO;
import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.dto.SendOtpDTO;

import java.io.IOException;
import java.util.List;

public interface UserService {



    OtpDTO sendOtp(SendOtpDTO sendOtpDTO) throws IOException;

    OtpDTO verifyOtp(OtpDTO otpDTO);

    List<AnganwadiCentersDTO> addAnganwadiCenters(List<AnganwadiCentersDTO> centersDTO);

    List<AnganwadiCentersDTO> getAnganwadiCenters();
}
