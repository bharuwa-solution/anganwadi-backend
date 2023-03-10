package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.ChildrenDTO;
import com.anganwadi.anganwadi.domains.dto.DashboardDetails;
import com.anganwadi.anganwadi.domains.dto.UploadDTO;
import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import com.anganwadi.anganwadi.domains.entity.Attendance;
import com.anganwadi.anganwadi.repositories.AnganwadiChildrenRepository;
import com.anganwadi.anganwadi.repositories.AttendanceRepository;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service
@Slf4j
public class AnganwadiChildrenServiceImpl implements AnganwadiChildrenService {

    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final FileManagementService fileManagementService;
    private final AttendanceRepository attendanceRepository;

    @Autowired
    public AnganwadiChildrenServiceImpl(AnganwadiChildrenRepository anganwadiChildrenRepository, FileManagementService fileManagementService,
                                        AttendanceRepository attendanceRepository) {
        this.anganwadiChildrenRepository = anganwadiChildrenRepository;
        this.fileManagementService = fileManagementService;
        this.attendanceRepository = attendanceRepository;
    }



    @Override
    public AnganwadiChildren saveChildrenRecord(AnganwadiChildren anganwadiChildren) throws ParseException, IOException {


        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        String finalDate = anganwadiChildren.getDob();
        Date dob = df2.parse(finalDate);
        anganwadiChildren.setDob(df.format(dob));
        anganwadiChildren.setProfilePic(anganwadiChildren.getProfilePic());

        return anganwadiChildrenRepository.save(anganwadiChildren);
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


}
