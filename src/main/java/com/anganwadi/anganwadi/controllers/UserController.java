package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.repositories.AnganwadiCenterRepository;
import com.anganwadi.anganwadi.service_impl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AnganwadiCenterRepository anganwadiCentersRepository;

    @Autowired
    public UserController(UserService userService, AnganwadiCenterRepository anganwadiCentersRepository) {
        this.userService = userService;
        this.anganwadiCentersRepository = anganwadiCentersRepository;
    }


    @PostMapping("/sendOtp")
    private OtpDTO sendOtp(@RequestBody SendOtpDTO sendOtpDTO) throws IOException {
        return userService.sendOtp(sendOtpDTO);

    }

    @PostMapping("/verifyOtp")
    private OtpDTO verifyOtp(@RequestBody OtpDTO otpDTO) {
        return userService.verifyOtp(otpDTO);
    }


    @ApiIgnore
    @PostMapping("/addAnganwadiCenters")
    private List<AnganwadiCenterDTO> addAnganwadiCenters(@RequestBody List<AnganwadiCenterDTO> centersDTO) {
        return userService.addAnganwadiCenters(centersDTO);
    }

    @PostMapping("/registerUser")
    private UserDTO registerUser(@RequestBody UserDTO userDTO) {
        return userService.registerUser(userDTO);
    }


    @PostMapping("/getAnganwadiCenters")
    private List<AnganwadiCenterDTO> getAnganwadiCenters() {
        return userService.getAnganwadiCenters();
    }

    @ApiIgnore
    @PostMapping("/saveVaccinationDetails")
    private VaccinationDTO saveVaccineDetails(@RequestParam String vaccineName) {

        return userService.addVaccineData(vaccineName);
    }

}
