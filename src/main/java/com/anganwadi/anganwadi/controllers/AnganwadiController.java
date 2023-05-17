package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
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

    @PostMapping("makeAttendanceManual")
    private List<AttendanceDTO> makeAttendanceManual(@RequestBody List<AttendanceDTO> attendanceDTO, @RequestHeader String centerId) throws ParseException {
        return anganwadiChildrenService.makeAttendanceManual(attendanceDTO, centerId);
    }


    @GetMapping("getAttendanceByDate")
    private List<AttendanceDTO> getAttendanceByDate(@RequestParam String date, @RequestHeader String centerName) throws ParseException {
        return anganwadiChildrenService.getAttendanceByDate(date, centerName);
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
    private SaveAdmissionDTO saveChildrenRecord(@RequestBody SaveAdmissionDTO saveAdmissionDTO, @RequestHeader String centerId, @RequestHeader String centerName) throws ParseException, IOException {
        return anganwadiChildrenService.saveChildrenRecord(saveAdmissionDTO, centerId, centerName);
    }

    @PutMapping("updateStudentDetails")
    private UpdateStudentDTO updateStudentDetails(@RequestBody UpdateStudentDTO updateStudentDTO) throws ParseException {
        return anganwadiChildrenService.updateStudentDetails(updateStudentDTO);
    }

    @PutMapping("deleteStudentDetails")
    private UpdateStudentDTO deleteStudentDetails(@RequestParam String id) throws ParseException {
        return anganwadiChildrenService.deleteStudentDetails(id);
    }

    @PutMapping("updateRegisteredValue")
    private SaveAdmissionDTO updateRegisteredValue(@RequestParam String id, @RequestParam boolean isRegistered) {
        return anganwadiChildrenService.updateRegisteredValue(id, isRegistered);
    }

    @GetMapping("getTotalChildren")
    private List<ChildrenDTO> getTotalChildren(@RequestHeader String centerName) throws ParseException {
        return anganwadiChildrenService.getTotalChildren(centerName);
    }

    @GetMapping("getRegisteredHouseholdsList")
    private List<householdsHeadList> getRegisteredHouseholdsList(@RequestHeader String centerName) {
        return anganwadiChildrenService.getRegisteredHouseholdsList(centerName);
    }

    @PostMapping("saveAttendancePhoto")
    private AttendancePhotoDTO saveAttendancePhoto(@RequestBody AttendancePhotoDTO attendancePhotoDTO) {
        return anganwadiChildrenService.saveAttendancePhoto(attendancePhotoDTO);
    }

    @PostMapping("getStudentListByChildId")
    private List<PartialStudentList> getStudentListByChildId(@RequestBody PartialStudentList partialStudentList) {
        return anganwadiChildrenService.getStudentListByChildId(partialStudentList);
    }

}
