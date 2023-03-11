package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import com.anganwadi.anganwadi.domains.entity.Attendance;
import com.anganwadi.anganwadi.repositories.AnganwadiChildrenRepository;
import com.anganwadi.anganwadi.repositories.AttendanceRepository;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
@Slf4j
public class AnganwadiChildrenServiceImpl implements AnganwadiChildrenService {

    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final FileManagementService fileManagementService;
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public AnganwadiChildrenServiceImpl(AnganwadiChildrenRepository anganwadiChildrenRepository, FileManagementService fileManagementService,
                                        AttendanceRepository attendanceRepository, ModelMapper modelMapper) {
        this.anganwadiChildrenRepository = anganwadiChildrenRepository;
        this.fileManagementService = fileManagementService;
        this.attendanceRepository = attendanceRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO) throws ParseException {


        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));


        String finalDate = saveAdmissionDTO.getDob();
        Date dob = df2.parse(finalDate);

        UUID familyId = UUID.randomUUID();
        UUID childId = UUID.randomUUID();

        AnganwadiChildren saveAdmission = AnganwadiChildren.builder()
                .name(saveAdmissionDTO.getName())
                .familyId(familyId.toString())
                .childId(childId.toString())
                .fatherName(saveAdmissionDTO.getFatherName())
                .dob(df.format(dob))
                .gender(saveAdmissionDTO.getGender())
                .mobileNumber(saveAdmissionDTO.getMobileNumber())
                .category(saveAdmissionDTO.getCategory())
                .minority(saveAdmissionDTO.getMinority())
                .handicap(saveAdmissionDTO.getHandicap())
                .profilePic(saveAdmissionDTO.getProfilePic())
                .build();

        anganwadiChildrenRepository.save(saveAdmission);

        return modelMapper.map(saveAdmission, SaveAdmissionDTO.class);
    }

    @Override
    public List<ChildrenDTO> getTotalChildren() {


        List<ChildrenDTO> addInList = new ArrayList<>();
        List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        for (AnganwadiChildren getChildren : childrenList) {
            ChildrenDTO childrenDTO = ChildrenDTO.builder()
                    .dob(getChildren.getDob())
                    .category(getChildren.getCategory())
                    .name(getChildren.getName())
                    .gender(getChildren.getGender())
                    .profilePic(getChildren.getProfilePic())
                    .build();
            addInList.add(childrenDTO);
        }

        return addInList;
    }

    @Override
    public UploadDTO uploadPic(MultipartFile file) throws IOException {
        return UploadDTO.builder()
                .url(fileManagementService.uploadPic(file))
                .build();
    }

    @Override
    public DashboardDetails getDashboardDetails() {
        return null;
    }

    @Override
    public DashboardDetails getAttendance() {

        List<Attendance> attendanceList = attendanceRepository.findAll(Sort.by(Sort.Direction.DESC, "childId"));

        return null;

    }

    @Override
    public List<AttendanceDTO> getAttendanceByDate(Date date) {

        return null;
    }

    @Override
    public AttendanceDTO makeAttendance(AttendanceDTO attendanceDTO) {

        return AttendanceDTO.builder()
                .dob(attendanceDTO.getDob())
                .childId(attendanceDTO.getChildId())
                .date(attendanceDTO.getDate())
                .name(attendanceDTO.getName())
                .gender(attendanceDTO.getGender())
                .attendance(attendanceDTO.getAttendance())
                .build();
    }


}
