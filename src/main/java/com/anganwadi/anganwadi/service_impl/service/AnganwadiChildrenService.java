package com.anganwadi.anganwadi.service_impl.service;

import com.anganwadi.anganwadi.domains.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface AnganwadiChildrenService {


    SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO,String centerName) throws ParseException, IOException;

    List<ChildrenDTO> getTotalChildren();

    UploadDTO uploadPic(MultipartFile file) throws IOException;

//    DashboardDetails getDashboardDetails();

    List<AttendanceDTO> getAttendanceByDate(String date,String centerName) throws ParseException;

    List<AttendanceDTO> makeAttendance(AttendanceDTO attendanceDTO) throws ParseException;

    List<AttendanceDTO> makeAndUpdateAttendance(AttendanceDTO attendanceDTO, String centerName) throws ParseException;
}
