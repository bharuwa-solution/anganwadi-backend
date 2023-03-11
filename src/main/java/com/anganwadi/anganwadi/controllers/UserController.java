package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.OtpDTO;
import com.anganwadi.anganwadi.domains.dto.SendOtpDTO;
import com.anganwadi.anganwadi.service_impl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/sendOtp")
    private OtpDTO sendOtp(@RequestBody SendOtpDTO sendOtpDTO) throws IOException {
        return userService.sendOtp(sendOtpDTO);

    }

    @PostMapping("/verifyOtp")
    private OtpDTO verifyOtp(@RequestBody OtpDTO otpDTO) {
        return userService.verifyOtp(otpDTO);
    }


}
