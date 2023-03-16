package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.AnganwadiCentersDTO;
import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.dto.SendOtpDTO;
import com.anganwadi.anganwadi.repositories.AnganwadiCentersRepository;
import com.anganwadi.anganwadi.service_impl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AnganwadiCentersRepository anganwadiCentersRepository;

    @Autowired
    public UserController(UserService userService,AnganwadiCentersRepository anganwadiCentersRepository) {
        this.userService = userService;
        this.anganwadiCentersRepository=anganwadiCentersRepository;
    }


    @PostMapping("/sendOtp")
    private OtpDTO sendOtp(@RequestBody SendOtpDTO sendOtpDTO) throws IOException {
        return userService.sendOtp(sendOtpDTO);

    }

    @PostMapping("/verifyOtp")
    private OtpDTO verifyOtp(@RequestBody OtpDTO otpDTO) {
        return userService.verifyOtp(otpDTO);
    }


    @PostMapping("/addAnganwadiCenters")
    private List<AnganwadiCentersDTO> addAnganwadiCenters(@RequestBody List<AnganwadiCentersDTO> centersDTO) {
        return userService.addAnganwadiCenters(centersDTO);
    }


    @PostMapping("/getAnganwadiCenters")
    private List<AnganwadiCentersDTO> getAnganwadiCenters() {
        return userService.getAnganwadiCenters();
    }


}
