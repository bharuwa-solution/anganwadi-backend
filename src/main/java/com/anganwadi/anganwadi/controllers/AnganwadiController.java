package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
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

    @GetMapping("getDashboardDetails")
    private DashboardDetails getDashboardDetails() {
        return anganwadiChildrenService.getDashboardDetails();
    }

    @PostMapping("makeAttendance")
    private AttendanceDTO makeAttendance(@RequestBody AttendanceDTO attendanceDTO) {
        return anganwadiChildrenService.makeAttendance(attendanceDTO);
    }

    @PostMapping("getAttendanceByDate")
    private List<AttendanceDTO> getAttendanceByDate(@RequestParam Date date) {
        return anganwadiChildrenService.getAttendanceByDate(date);
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
    private SaveAdmissionDTO saveChildrenRecord(@RequestBody SaveAdmissionDTO saveAdmissionDTO) throws ParseException, IOException {
        return anganwadiChildrenService.saveChildrenRecord(saveAdmissionDTO);
    }

    @GetMapping("getTotalChildren")
    private List<ChildrenDTO> getTotalChildren() {
        return anganwadiChildrenService.getTotalChildren();
    }


}
