package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.AttendanceDTO;
import com.anganwadi.anganwadi.domains.dto.ChildrenDTO;
import com.anganwadi.anganwadi.domains.dto.SaveAdmissionDTO;
import com.anganwadi.anganwadi.domains.dto.UploadDTO;
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
    public SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO, String centerName) throws ParseException {


        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));


        String finalDate = saveAdmissionDTO.getDob();
        Date dob = df2.parse(finalDate);

        AnganwadiChildren saveAdmission = AnganwadiChildren.builder()
                .name(saveAdmissionDTO.getName() == null ? "" : saveAdmissionDTO.getName())
                .familyId(saveAdmissionDTO.getFamilyId() == null ? "" : saveAdmissionDTO.getFamilyId())
                .childId(saveAdmissionDTO.getChildId() == null ? "" : saveAdmissionDTO.getChildId())
                .fatherName(saveAdmissionDTO.getFatherName() == null ? "" : saveAdmissionDTO.getFatherName())
                .motherName(saveAdmissionDTO.getMotherName() == null ? "" : saveAdmissionDTO.getMotherName())
                .centerName(centerName)
                .dob(df.format(dob))
                .gender(saveAdmissionDTO.getGender()==null?"":saveAdmissionDTO.getGender())
                .mobileNumber(saveAdmissionDTO.getMobileNumber()==null?"":saveAdmissionDTO.getMobileNumber())
                .category(saveAdmissionDTO.getCategory()==null?"":saveAdmissionDTO.getCategory())
                .minority(saveAdmissionDTO.getMinority()==null?"":saveAdmissionDTO.getMinority())
                .handicap(saveAdmissionDTO.getHandicap()==null?"":saveAdmissionDTO.getHandicap())
                .profilePic(saveAdmissionDTO.getProfilePic()==null?"":saveAdmissionDTO.getProfilePic())
                .build();

        anganwadiChildrenRepository.save(saveAdmission);

        return modelMapper.map(saveAdmission, SaveAdmissionDTO.class);
    }

    @Override
    public List<ChildrenDTO> getTotalChildren(String centerName) {


        List<ChildrenDTO> addInList = new ArrayList<>();
        List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByCenterName(centerName);

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

//    @Override
//    public DashboardDetails getDashboardDetails() {
//        return null;
//    }


    @Override
    public List<AttendanceDTO> getAttendanceByDate(String date, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date formatToTime = df.parse(date);
        long timestamp = formatToTime.getTime();

        markAsAbsent(centerName);

        List<Attendance> findRecords = attendanceRepository.findAllByDateAndCenterName(timestamp, centerName, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<AttendanceDTO> addList = new ArrayList<>();

        for (Attendance singleRecord : findRecords) {
            AttendanceDTO dailyRecord = AttendanceDTO.builder()
                    .childId(singleRecord.getChildId())
                    .name(singleRecord.getName())
                    .gender(singleRecord.getGender())
                    .dob(singleRecord.getDob())
                    .photo(singleRecord.getPhoto())
                    .attendance(singleRecord.getAttendance())
                    .date(singleRecord.getDate())
                    .build();

            addList.add(dailyRecord);
        }

        return addList;

    }

    private String checkAttendanceOnDay(String childId, long date, String centerName) {
        String attendance = "";
        List<Attendance> findChild = attendanceRepository.findAllByChildIdAndDateAndCenterName(childId, date, centerName);

        if (findChild.size() > 0) {
            for (Attendance checkAttendance : findChild) {
                attendance = checkAttendance.getAttendance();

            }
        }

        return attendance;

    }

    private void markAsAbsent(String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAllByCenterName(centerName);
        String attendance = "A";


        for (AnganwadiChildren getId : findChildren) {
            List<Attendance> lastVerify = attendanceRepository.findAllByChildIdAndDateAndCenterName(getId.getChildId(), timestamp, centerName);
           String  verifyAttend = checkAttendanceOnDay(getId.getChildId(), timestamp, centerName);
            if (lastVerify.size() <= 0) {

                Attendance saveAttendance = Attendance.builder()
                        .childId(getId.getChildId())
                        .dob(getId.getDob())
                        .centerName(centerName)
                        .name(getId.getName())
                        .photo(getId.getProfilePic())
                        .gender(getId.getGender())
                        .date(timestamp)
                        .attendance(verifyAttend.equals("") ? attendance : verifyAttend)
                        .build();
                attendanceRepository.save(saveAttendance);

            }

        }

    }


    private void markPresent(String childId, String latitude, String longitude, long timestamp) {

        String[] splitString = childId.split(",");

        for (String getChildId : splitString) {
            List<Attendance> findChildInRecord = attendanceRepository.updateAttendance(getChildId.trim(), timestamp);

            if (findChildInRecord.size() > 0) {

                for (Attendance markAttendance : findChildInRecord) {
                    markAttendance.setLatitude(latitude);
                    markAttendance.setLongitude(longitude);
                    markAttendance.setAttendance("P");
                    attendanceRepository.save(markAttendance);
                }
            }
        }

    }

    @Override
    public List<AttendanceDTO> makeAndUpdateAttendance(AttendanceDTO attendanceDTO, String centerName) throws ParseException {

        // convert date to millis

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        // set default value
        boolean isMarked = false;

        List<AttendanceDTO> addList = new ArrayList<>();

        // Checking if record exists for current date
        List<Attendance> checkDailyAttendance = attendanceRepository.findAllByDate(timestamp, Sort.by(Sort.Direction.DESC, "createdDate"));

        // updating if not exists
        markAsAbsent(centerName);

        // After updating Above fields

        markPresent(attendanceDTO.getChildId(), attendanceDTO.getLatitude(), attendanceDTO.getLongitude(), timestamp);

        List<Attendance> getDetails = attendanceRepository.findAllByDateAndCenterName(timestamp, centerName, Sort.by(Sort.Direction.DESC, "createdDate"));


        for (Attendance fetchDetails : getDetails) {

            AttendanceDTO singleEntry = AttendanceDTO
                    .builder()
                    .centerName(centerName)
                    .childId(fetchDetails.getChildId())
                    .dob(fetchDetails.getDob())
                    .name(fetchDetails.getName())
                    .latitude(attendanceDTO.getLatitude())
                    .longitude(attendanceDTO.getLongitude())
                    .photo(fetchDetails.getPhoto())
                    .gender(fetchDetails.getGender())
                    .date(timestamp)
                    .attendance(fetchDetails.getAttendance())
                    .build();

            addList.add(singleEntry);
        }
        return addList;

    }


    @Override
    public List<AttendanceDTO> makeAttendance(AttendanceDTO attendanceDTO) throws ParseException {


        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        String[] spiltComma = attendanceDTO.getChildId().trim().split(",");
        List<AttendanceDTO> addList = new ArrayList<>();
        String attendance = "A";
        long currentDate = 0L;

        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAll();
        String ids = "", angwandiId = "";
        for (AnganwadiChildren getId : findChildren) {
            angwandiId = getId.getId();

            Attendance saveAttendance = Attendance.builder()
                    .childId(getId.getChildId())
                    .dob(getId.getDob())
                    .name(getId.getName())
                    .latitude(attendanceDTO.getLatitude())
                    .longitude(attendanceDTO.getLongitude())
                    .photo(getId.getProfilePic())
                    .gender(getId.getGender())
                    .date(timestamp)
                    .attendance(attendance)
                    .build();
            attendanceRepository.save(saveAttendance);

            currentDate = saveAttendance.getDate();
            ids = saveAttendance.getId();
        }

            for (String attend : spiltComma) {

                List<AnganwadiChildren> checkChild = anganwadiChildrenRepository.findAllByChildId(attend.trim());
                try {
                    if (checkChild.size() > 0) {
                        for (AnganwadiChildren children : checkChild) {
                            List<Attendance> updateAttendance = attendanceRepository.findAllByChildId(children.getChildId());

                            for (Attendance updateAtt : updateAttendance) {
                                updateAtt.setAttendance("P");
                                attendanceRepository.save(updateAtt);
                                log.info("Date " + formatToTime);
                            }
                        }
                    }
                } catch (NoSuchElementException | NullPointerException e) {
                    log.info("Id Not Found");
                }


            }

        List<Attendance> getDetails = attendanceRepository.findAllByDate(currentDate, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (Attendance fetchDetails : getDetails) {

            AttendanceDTO singleEntry = AttendanceDTO
                    .builder()
                    .childId(fetchDetails.getChildId())
                    .dob(fetchDetails.getDob())
                    .name(fetchDetails.getName())
                    .latitude(attendanceDTO.getLatitude())
                    .longitude(attendanceDTO.getLongitude())
                    .photo(fetchDetails.getPhoto())
                    .gender(fetchDetails.getGender())
                    .date(currentDate)
                    .attendance(fetchDetails.getAttendance())
                    .build();

            addList.add(singleEntry);
        }
        return addList;


    }


}
