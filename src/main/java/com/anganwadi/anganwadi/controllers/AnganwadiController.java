package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.AttendanceDTO;
import com.anganwadi.anganwadi.domains.dto.ChildrenDTO;
import com.anganwadi.anganwadi.domains.dto.SaveAdmissionDTO;
import com.anganwadi.anganwadi.domains.dto.UploadDTO;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("anganwadi")
public class AnganwadiController {

    private final AnganwadiChildrenService anganwadiChildrenService;
    private final FamilyService familyService;

    @Autowired
    public AnganwadiController(AnganwadiChildrenService anganwadiChildrenService, FamilyService familyService) {
        this.anganwadiChildrenService = anganwadiChildrenService;
        this.familyService = familyService;
    }

//    @GetMapping("getDashboardDetails")
//    private DashboardDetails getDashboardDetails() {
//        return anganwadiChildrenService.getDashboardDetails();
//    }

    @PostMapping("makeAttendance")
    private List<AttendanceDTO> makeAndUpdateAttendance(@RequestBody AttendanceDTO attendanceDTO, @RequestHeader String centerName) throws ParseException {
        return anganwadiChildrenService.makeAndUpdateAttendance(attendanceDTO, centerName);
    }

    @GetMapping("getAttendanceByDate")
    private List<AttendanceDTO> getAttendanceByDate(@RequestParam String  date, @RequestHeader String centerName) throws ParseException {
        return anganwadiChildrenService.getAttendanceByDate(date,centerName);
    }


    @PostMapping(path = "uploadPic", consumes = "multipart/form-data")
    private UploadDTO uploadPic(@RequestPart MultipartFile file) throws IOException {

        return anganwadiChildrenService.uploadPic(file);
    }

    @GetMapping("getAddress")
    private String getAddress() {
        return "All Users Address";
    }

    @PostMapping("saveChildrenRecord")
    private SaveAdmissionDTO saveChildrenRecord(@RequestBody SaveAdmissionDTO saveAdmissionDTO, @RequestHeader String centerName) throws ParseException, IOException {
        return anganwadiChildrenService.saveChildrenRecord(saveAdmissionDTO,centerName);
    }

    @GetMapping("getTotalChildren")
    private List<ChildrenDTO> getTotalChildren(@RequestHeader String centerName) {
        return anganwadiChildrenService.getTotalChildren(centerName);
    }



}
