package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.*;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.*;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class AnganwadiChildrenServiceImpl implements AnganwadiChildrenService {

    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final FileManagementService fileManagementService;
    private final AttendanceRepository attendanceRepository;
    private final ModelMapper modelMapper;
    private final FamilyRepository familyRepository;
    private final FamilyMemberRepository familyMemberRepository;
    private final FamilyServiceImpl familyServiceImpl;
    private final AssetsStockRepository assetsStockRepository;
    private final StockListRepository stockListRepository;
    private final StockDistributionRepository stockDistributionRepository;
    private final MealsRepository mealsRepository;
    private final WeightTrackingRepository weightTrackingRepository;
    private final CommonMethodsService commonMethodsService;
    private final AttendancePhotoRepository attendancePhotoRepository;
    private final AnganwadiCenterRepository anganwadiCenterRepository;

    @Autowired
    public AnganwadiChildrenServiceImpl(AnganwadiChildrenRepository anganwadiChildrenRepository, FileManagementService fileManagementService,
                                        AttendanceRepository attendanceRepository, ModelMapper modelMapper,
                                        FamilyRepository familyRepository, FamilyMemberRepository familyMemberRepository,
                                        FamilyServiceImpl familyServiceImpl, AssetsStockRepository assetsStockRepository,
                                        StockListRepository stockListRepository, StockDistributionRepository stockDistributionRepository,
                                        MealsRepository mealsRepository, WeightTrackingRepository weightTrackingRepository,
                                        CommonMethodsService commonMethodsService, AttendancePhotoRepository attendancePhotoRepository,
                                        AnganwadiCenterRepository anganwadiCenterRepository) {
        this.anganwadiChildrenRepository = anganwadiChildrenRepository;
        this.fileManagementService = fileManagementService;
        this.attendanceRepository = attendanceRepository;
        this.modelMapper = modelMapper;
        this.familyRepository = familyRepository;
        this.familyMemberRepository = familyMemberRepository;
        this.familyServiceImpl = familyServiceImpl;
        this.assetsStockRepository = assetsStockRepository;
        this.stockListRepository = stockListRepository;
        this.stockDistributionRepository = stockDistributionRepository;
        this.mealsRepository = mealsRepository;
        this.weightTrackingRepository = weightTrackingRepository;
        this.commonMethodsService = commonMethodsService;
        this.attendancePhotoRepository = attendancePhotoRepository;
        this.anganwadiCenterRepository = anganwadiCenterRepository;
    }


    @Override
    public SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO, String centerId, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));


        String finalDate = saveAdmissionDTO.getDob();
        Date dob = df2.parse(finalDate);
        boolean isRegistered = false;
        if (saveAdmissionDTO.isRegistered()) {
            isRegistered = true;
        }

        AnganwadiChildren saveAdmission = AnganwadiChildren.builder()
                .name(saveAdmissionDTO.getName() == null ? "" : saveAdmissionDTO.getName())
                .familyId(saveAdmissionDTO.getFamilyId() == null ? "" : saveAdmissionDTO.getFamilyId())
                .childId(saveAdmissionDTO.getChildId() == null ? "" : saveAdmissionDTO.getChildId())
                .fatherName(saveAdmissionDTO.getFatherName() == null ? "" : saveAdmissionDTO.getFatherName())
                .motherName(saveAdmissionDTO.getMotherName() == null ? "" : saveAdmissionDTO.getMotherName())
                .isRegistered(isRegistered)
                .centerName(centerName)
                .centerId(centerId)
                .dob(df.format(dob))
                .gender(saveAdmissionDTO.getGender() == null ? "" : saveAdmissionDTO.getGender())
                .mobileNumber(saveAdmissionDTO.getMobileNumber() == null ? "" : saveAdmissionDTO.getMobileNumber())
                .category(saveAdmissionDTO.getCategory() == null ? "" : saveAdmissionDTO.getCategory())
                .minority(saveAdmissionDTO.getMinority() == null ? "" : saveAdmissionDTO.getMinority())
                .handicap(saveAdmissionDTO.getHandicap() == null ? "" : saveAdmissionDTO.getHandicap())
                .profilePic(saveAdmissionDTO.getProfilePic() == null ? "" : saveAdmissionDTO.getProfilePic())
                .build();

        anganwadiChildrenRepository.save(saveAdmission);

        return modelMapper.map(saveAdmission, SaveAdmissionDTO.class);
    }


    @Override
    public SaveAdmissionDTO updateRegisteredValue(String id, boolean isRegistered) {

        AnganwadiChildren findId = anganwadiChildrenRepository.findById(id).get();

        if (findId != null) {
            findId.setRegistered(isRegistered);
            anganwadiChildrenRepository.save(findId);
        }

        return SaveAdmissionDTO.builder()
                .id(findId.getId() == null ? "" : findId.getId())
                .name(findId.getName() == null ? "" : findId.getName())
                .familyId(findId.getFamilyId() == null ? "" : findId.getFamilyId())
                .childId(findId.getChildId() == null ? "" : findId.getChildId())
                .fatherName(findId.getFatherName() == null ? "" : findId.getFatherName())
                .motherName(findId.getMotherName() == null ? "" : findId.getMotherName())
                .isRegistered(findId.isRegistered())
                .centerName(findId.getCenterName())
                .dob(findId.getDob())
                .gender(findId.getGender() == null ? "" : findId.getGender())
                .mobileNumber(findId.getMobileNumber() == null ? "" : findId.getMobileNumber())
                .category(findId.getCategory() == null ? "" : findId.getCategory())
                .minority(findId.getMinority() == null ? "" : findId.getMinority())
                .handicap(findId.getHandicap() == null ? "" : findId.getHandicap())
                .profilePic(findId.getProfilePic() == null ? "" : findId.getProfilePic())
                .build();
    }

    @Override
    public List<AttendanceDTO> makeAttendanceManual(List<AttendanceDTO> attendanceDTO, String centerId) throws ParseException {
        List<AttendanceDTO> addInList = new ArrayList<>();

        for (AttendanceDTO list : attendanceDTO) {
            List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAllByChildIdAndRegisteredTrue(list.getChildId());

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date currentTime = new Date();
            String formatToString = df.format(currentTime.getTime());
            Date formatToTime = df.parse(formatToString);
            long timestamp = formatToTime.getTime();

            List<Attendance> findAttRecords = attendanceRepository.findByDateAndChildId(timestamp, list.getChildId());

            if (findAttRecords.size() > 0) {

                for (Attendance updateStatus : findAttRecords) {
                    updateStatus.setAttType(list.getAttType());
                    updateStatus.setAttendance(list.getAtt());

                    attendanceRepository.save(updateStatus);

                    AttendanceDTO singleEntry = AttendanceDTO.builder()
                            .centerName(updateStatus.getCenterName() == null ? "" : updateStatus.getCenterName())
                            .centerId(centerId == null ? "" : centerId)
                            .childId(updateStatus.getChildId() == null ? "" : updateStatus.getChildId())
                            .dob(updateStatus.getDob() == null ? "" : updateStatus.getDob())
                            .name(updateStatus.getName() == null ? "" : updateStatus.getName())
                            .latitude(updateStatus.getLatitude() == null ? "" : updateStatus.getLatitude())
                            .longitude(updateStatus.getLongitude() == null ? "" : updateStatus.getLongitude())
                            .photo(updateStatus.getPhoto() == null ? "" : updateStatus.getPhoto())
                            .gender(updateStatus.getGender() == null ? "" : updateStatus.getGender())
                            .date(timestamp)
                            .attType(list.getAttType() == null ? "" : list.getAttType())
                            .att(list.getAtt() == null ? "" : list.getAtt())
                            .attendance(list.getAtt() == null ? "" : list.getAtt())
                            .build();
                    addInList.add(singleEntry);

                }
            }
            if (findAttRecords.size() <= 0) {
                for (AnganwadiChildren sc : findChildren) {
                    Attendance saveStatus = Attendance.builder()
                            .centerName(sc.getCenterName() == null ? "" : sc.getCenterName())
                            .centerId(centerId == null ? "" : centerId)
                            .childId(sc.getChildId() == null ? "" : sc.getChildId())
                            .dob(sc.getDob() == null ? "" : sc.getDob())
                            .name(sc.getName() == null ? "" : sc.getName())
                            .latitude(list.getLatitude() == null ? "" : list.getLatitude())
                            .longitude(list.getLongitude() == null ? "" : list.getLongitude())
                            .photo(sc.getProfilePic() == null ? "" : sc.getProfilePic())
                            .gender(sc.getGender() == null ? "" : sc.getGender())
                            .isRegistered(sc.isRegistered())
                            .date(timestamp)
                            .attType(list.getAttType() == null ? "" : list.getAttType())
                            .attendance(list.getAtt() == null ? "" : list.getAtt())
                            .attType(list.getAttType() == null ? "" : list.getAttType())
                            .build();
                    attendanceRepository.save(saveStatus);


                    AttendanceDTO singleEntry = AttendanceDTO.builder()
                            .centerName(saveStatus.getCenterName())
                            .childId(saveStatus.getChildId())
                            .dob(saveStatus.getDob())
                            .name(saveStatus.getName())
                            .latitude(saveStatus.getLatitude())
                            .longitude(saveStatus.getLongitude())
                            .photo(saveStatus.getPhoto())
                            .gender(saveStatus.getGender())
                            .date(timestamp)
                            .attType(list.getAttType())
                            .att(list.getAtt())
                            .attendance(list.getAtt())
                            .build();
                    addInList.add(singleEntry);
                }
            }
        }

        return addInList;
    }

    @Override
    public List<FamilyMemberConverted> convertUnixToDate() {

        List<FamilyMember> convertToDate = familyMemberRepository.findAll();
        List<FamilyMemberConverted> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        for (FamilyMember fm : convertToDate) {

            Date formatToTime = new Date(fm.getDob());

            FamilyMemberConverted singleEntry = FamilyMemberConverted.builder()
                    .id(fm.getId())
                    .familyId(fm.getFamilyId())
                    .name(fm.getName())
                    .photo(fm.getPhoto())
                    .category(fm.getCategory())
                    .isRegistered(fm.isRegistered())
                    .motherName(fm.getMotherName())
                    .fatherName(fm.getFatherName())
                    .mobileNumber(fm.getMobileNumber())
                    .stateCode(fm.getStateCode())
                    .idType(fm.getIdType())
                    .idNumber(fm.getIdNumber())
                    .centerId(fm.getCenterId())
                    .centerName(fm.getCenterName())
                    .relationWithOwner(fm.getRelationWithOwner())
                    .gender(fm.getGender())
                    .dob(df.format(formatToTime))
                    .maritalStatus(fm.getMaritalStatus())
                    .memberCode(fm.getMemberCode())
                    .handicap(fm.getHandicap())
                    .handicapType(fm.getHandicapType())
                    .residentArea(fm.getResidentArea())
                    .dateOfArrival(fm.getDateOfArrival())
                    .dateOfLeaving(fm.getDateOfLeaving())
                    .dateOfMortality(fm.getDateOfMortality())
                    .recordForMonth(fm.getRecordForMonth())
                    .build();

            list.add(singleEntry);

        }

        return list;

    }

    @Override
    public List<AttendanceConverted> convertAttendanceUnixToDate() {

        List<Attendance> getData = attendanceRepository.findAll();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<AttendanceConverted> addInList = new ArrayList<>();

        for (Attendance list : getData) {

            Date date = new Date(list.getDate());

            AttendanceConverted attendance = AttendanceConverted.builder()
                    .id(list.getId())
                    .centerId(list.getCenterId())
                    .centerName(list.getCenterName())
                    .name(list.getName())
                    .isRegistered(list.isRegistered())
                    .childId(list.getChildId())
                    .date(df.format(date))
                    .attType(list.getAttType())
                    .latitude(list.getLatitude())
                    .longitude(list.getLongitude())
                    .dob(list.getDob())
                    .gender(list.getGender())
                    .photo(list.getPhoto())
                    .attendance(list.getAttendance())
                    .build();

            addInList.add(attendance);
        }
        return addInList;
    }

    @Override
    public AttendancePhotoDTO saveAttendancePhoto(AttendancePhotoDTO attendancePhotoDTO) {

        if (StringUtils.isEmpty(attendancePhotoDTO.getCenterId().trim())) {
            throw new CustomException("CenterId is Not Passed");
        }

        AttendancePhoto saveAP = AttendancePhoto.builder()
                .centerId(attendancePhotoDTO.getCenterId())
                .centerName(commonMethodsService.findCenterName(attendancePhotoDTO.getCenterId()))
                .imageName(attendancePhotoDTO.getImageName())
                .date(attendancePhotoDTO.getDate())
                .build();

        attendancePhotoRepository.save(saveAP);
        return modelMapper.map(saveAP, AttendancePhotoDTO.class);
    }


    @Override
    public List<DashboardMaster> getDashboardMasterDetails() throws ParseException {

        List<AnganwadiCenter> listCenters = anganwadiCenterRepository.findAll();
        List<DashboardMaster> addInList = new ArrayList<>();

        for (AnganwadiCenter ac : listCenters) {

            DashboardMaster singleEntry = DashboardMaster.builder()
                    .centerId(ac.getId())
                    .centerName(ac.getCenterName())
                    .pregnantWomenCount(commonMethodsService.pregnantWomenCount(ac.getId()))
                    .dhatriWomenCount(commonMethodsService.dhartiWomenCount(ac.getId()))
                    .childrenCount(commonMethodsService.childrenCount(ac.getId()))
                    .todayAttendance(commonMethodsService.todayAttendance(ac.getId()))
                    .build();

            addInList.add(singleEntry);
        }


        return addInList;
    }

    @Override
    public UpdateStudentDTO updateStudentDetails(UpdateStudentDTO updateStudentDTO) throws ParseException {

        if (StringUtils.isEmpty(updateStudentDTO.getId().trim())) {
            throw new CustomException("Id Not Passed");
        }

        if (anganwadiChildrenRepository.findById(updateStudentDTO.getId().trim()).isPresent()) {
            AnganwadiChildren ac = anganwadiChildrenRepository.findById(updateStudentDTO.getId().trim()).get();

            ac.setProfilePic(updateStudentDTO.getProfilePic() == null ? "" : updateStudentDTO.getProfilePic());
            ac.setDob(updateStudentDTO.getDob() == null ? "" : updateStudentDTO.getDob());
            ac.setGender(updateStudentDTO.getGender() == null ? "" : updateStudentDTO.getGender());
            ac.setName(updateStudentDTO.getName() == null ? "" : updateStudentDTO.getName());

            anganwadiChildrenRepository.save(ac);

            if (familyMemberRepository.findById(updateStudentDTO.getChildId().trim()).isPresent()) {
                FamilyMember findChild = familyMemberRepository.findById(updateStudentDTO.getChildId().trim()).get();

                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date formatToTime = df.parse(updateStudentDTO.getDob().trim());
                long dob = formatToTime.getTime();

                findChild.setDob(dob);
                findChild.setName(updateStudentDTO.getName() == null ? "" : updateStudentDTO.getName());
                findChild.setGender(updateStudentDTO.getGender() == null ? "" : updateStudentDTO.getGender());

                familyMemberRepository.save(findChild);

            }

            return UpdateStudentDTO.builder()
                    .childId(updateStudentDTO.getChildId() == null ? "" : updateStudentDTO.getChildId())
                    .dob(updateStudentDTO.getDob() == null ? "" : updateStudentDTO.getDob())
                    .gender(updateStudentDTO.getGender() == null ? "" : updateStudentDTO.getGender())
                    .profilePic(updateStudentDTO.getProfilePic() == null ? "" : updateStudentDTO.getProfilePic())
                    .id(updateStudentDTO.getId())
                    .name(updateStudentDTO.getName() == null ? "" : updateStudentDTO.getName())
                    .build();


        } else {
            throw new CustomException("Child Not Found");
        }


    }

    @Override
    public List<ChildrenDTO> getTotalChildren(String centerName) {

        List<ChildrenDTO> addInList = new ArrayList<>();
        List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByCenterNameAndRegisteredTrue(centerName);

        for (AnganwadiChildren getChildren : childrenList) {
            ChildrenDTO childrenDTO = ChildrenDTO.builder()
                    .id(getChildren.getId())
                    .childId(getChildren.getChildId())
                    .dob(getChildren.getDob())
                    .isRegistered(getChildren.isRegistered())
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

//        markAsAbsent(centerName);

        List<Attendance> findRecords = attendanceRepository.findAllByDateAndCenterNameAndRegistered(timestamp, centerName, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<AttendanceDTO> addList = new ArrayList<>();

        for (Attendance singleRecord : findRecords) {
            AttendanceDTO dailyRecord = AttendanceDTO.builder()
                    .childId(singleRecord.getChildId() == null ? "" : singleRecord.getChildId())
                    .centerId(singleRecord.getCenterId() == null ? "" : singleRecord.getCenterId())
                    .latitude(singleRecord.getLatitude() == null ? "" : singleRecord.getLatitude())
                    .longitude(singleRecord.getLongitude() == null ? "" : singleRecord.getLongitude())
                    .name(singleRecord.getName() == null ? "" : singleRecord.getName())
                    .attType(singleRecord.getAttType() == null ? "" : singleRecord.getAttType())
                    .att(singleRecord.getAttendance() == null ? "" : singleRecord.getAttendance())
                    .centerName(centerName)
                    .gender(singleRecord.getGender() == null ? "" : singleRecord.getGender())
                    .dob(singleRecord.getDob())
                    .photo(singleRecord.getPhoto() == null ? "" : singleRecord.getPhoto())
                    .attendance(singleRecord.getAttendance() == null ? "" : singleRecord.getAttendance())
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

    private void markAsAbsent(String childId, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAllByCenterNameAndRegisteredTrue(centerName);
        String attendance = "A";


        for (AnganwadiChildren getId : findChildren) {
            List<Attendance> lastVerify = attendanceRepository.findAllByChildIdAndDateAndCenterName(getId.getChildId(), timestamp, centerName);
            String verifyAttend = checkAttendanceOnDay(getId.getChildId(), timestamp, centerName);
            if (lastVerify.size() <= 0) {

                Attendance saveAttendance = Attendance.builder()
                        .childId(getId.getChildId())
                        .dob(getId.getDob())
                        .centerId(getId.getCenterId())
                        .isRegistered(getId.isRegistered())
                        .longitude("")
                        .latitude("")
                        .attType("System")
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


    private void markPresent(String childId, String latitude, String longitude, long timestamp, String centerName) {

        String[] splitString = childId.split(",");

        for (String getChildId : splitString) {
            List<Attendance> findChildInRecord = attendanceRepository.updateAttendance(getChildId.trim(), timestamp, centerName);

            if (findChildInRecord.size() > 0) {

                for (Attendance markAttendance : findChildInRecord) {
                    markAttendance.setLatitude(latitude);
                    markAttendance.setAttType("System");
                    markAttendance.setLongitude(longitude);
                    markAttendance.setAttendance("P");
                    attendanceRepository.save(markAttendance);
                }
            }
        }

    }

    @Override
    public List<AttendanceDTO> makeAndUpdateAttendance(AttendanceDTO attendanceDTO, String centerName) throws ParseException {

        log.error("Child Id : " + attendanceDTO.getChildId());
        log.error("Center Name : " + centerName);
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
        markAsAbsent(attendanceDTO.getChildId(),centerName);

        // After updating Above fields

        markPresent(attendanceDTO.getChildId(), attendanceDTO.getLatitude(), attendanceDTO.getLongitude(), timestamp, centerName);

        List<Attendance> getDetails = attendanceRepository.findAllByDateAndCenterName(timestamp, centerName, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (Attendance fetchDetails : getDetails) {

            AttendanceDTO singleEntry = AttendanceDTO
                    .builder()
                    .centerName(centerName)
                    .centerId(fetchDetails.getCenterId() == null ? "" : fetchDetails.getCenterId())
                    .childId(fetchDetails.getChildId())
                    .attType("System")
                    .att("")
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
    public List<householdsHeadList> getRegisteredHouseholdsList(String centerName) {
        List<householdsHeadList> addInList = new ArrayList<>();
        List<AnganwadiChildren> findFamilyIds = anganwadiChildrenRepository.findAllByCenterName(centerName);
        HashSet<String> uniqueFamilyIds = new HashSet<>();

        if (findFamilyIds.size() > 0) {
            String familyId = "";
            for (AnganwadiChildren ac : findFamilyIds) {

                if (uniqueFamilyIds.add(ac.getFamilyId())) {

                    List<Family> checkHouseDetails = familyRepository.findAllByFamilyIdIn(ac.getFamilyId());
                    if (checkHouseDetails.size() > 0) {
                        for (Family familyDetails : checkHouseDetails) {

                            String headName = "", dob = "", pic = "", gender = "", houseNo = "", religion = "", category = "";


                            houseNo = familyDetails.getHouseNo();
                            religion = familyDetails.getReligion();
                            category = familyDetails.getCategory();


                            List<FamilyMember> findHouseholds = familyMemberRepository.findAllByFamilyId(ac.getFamilyId(), Sort.by(Sort.Direction.DESC, "createdDate"));

                            for (FamilyMember findHeadDetails : findHouseholds) {
                                long millis = findHeadDetails.getDob();
                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                                Date date = new Date(millis);

                                if (findHeadDetails.getRelationWithOwner().equalsIgnoreCase("0")) {
                                    headName = findHeadDetails.getName();
                                    dob = df.format(date);
                                    pic = findHeadDetails.getPhoto();
                                    gender = findHeadDetails.getGender();
                                    break;
                                } else {
                                    headName = findHeadDetails.getName();
                                    dob = df.format(date);
                                    pic = findHeadDetails.getPhoto();
                                    gender = findHeadDetails.getGender();
                                }

                            }


                            long countMembers = familyMemberRepository.countByFamilyId(ac.getFamilyId());

                            householdsHeadList householdsDTO = householdsHeadList.builder()
                                    .headName(headName)
                                    .headDob(dob)
                                    .houseNo(houseNo)
                                    .familyId(ac.getFamilyId())
                                    .religion(religion)
                                    .headGender(gender)
                                    .totalMale(familyServiceImpl.totalHouseholdsMale(ac.getFamilyId()))
                                    .totalFemale(familyServiceImpl.totalHouseholdsFemale(ac.getFamilyId()))
                                    .totalChildren(familyServiceImpl.totalHouseholdsChildren(ac.getFamilyId()))
                                    .category(category)
                                    .totalMembers(String.valueOf(countMembers))
                                    .headPic(pic)
                                    .build();
                            addInList.add(householdsDTO);
                        }
                    }
                }
            }
        }
        return addInList;
    }

    private String sumOfItems(String centerName, String itemCode) {
        String itemSum = "";
        List<AssetsStock> findCenterItems = assetsStockRepository.findAllByCenterNameAndItemCode(centerName, itemCode);
        float itemInFloat = 0L;
        if (findCenterItems.size() > 0) {

            for (AssetsStock fs : findCenterItems) {
                itemInFloat = itemInFloat + Float.parseFloat(fs.getQty());
            }
        }

        itemSum = String.valueOf(itemInFloat);

        return itemSum;
    }

    @Override
    public List<StockListDTO> getAvailableItems() {
        List<StockList> stockLists = stockListRepository.findAll(Sort.by(Sort.Direction.ASC, "itemCode"));
        List<StockListDTO> addInList = new ArrayList<>();
        HashSet<String> uniqueStockCode = new HashSet<>();

        for (StockList mapItems : stockLists) {
            if (uniqueStockCode.add(mapItems.getItemCode())) {
                StockListDTO assets = StockListDTO.builder()
                        .itemCode(mapItems.getItemCode())
                        .quantity(mapItems.getQuantity())
                        .unit(mapItems.getUnit())
                        .itemName(mapItems.getItemName())
                        .build();
                addInList.add(assets);
            }
        }


        return addInList;
    }

    @Override
    public List<StockItemsDTO> addStocks(List<StockItemsDTO> assetsStock, String centerId, String centerName) throws ParseException {

        List<StockItemsDTO> addInList = new ArrayList<>();


        for (StockItemsDTO assetsList : assetsStock) {


            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date currentTime = new Date();
            String formatToString = df.format(currentTime.getTime());
            Date formatToTime = df.parse(formatToString);
            long timestamp = formatToTime.getTime();
            String[] spiltMonth = formatToString.split("-");
            String currentMonth = spiltMonth[1].replace("0", "");

            AssetsStock saveStocks = AssetsStock.builder()
                    .centerId(centerId)
                    .centerName(centerName)
                    .qtyUnit(assetsList.getUnit())
                    .itemCode(assetsList.getItemCode())
                    .itemName(assetsList.getItemName())
                    .qty(assetsList.getQuantity())
                    .date(timestamp)
                    .month(currentMonth)
                    .build();

            assetsStockRepository.save(saveStocks);


            StockItemsDTO addSingleItem = StockItemsDTO.builder()
                    .centerId(centerId)
                    .centerName(centerName)
                    .unit(assetsList.getUnit())
                    .itemCode(assetsList.getItemCode())
                    .itemName(assetsList.getItemName())
                    .quantity(assetsList.getQuantity())
                    .date(formatToString)
                    .build();

            addInList.add(addSingleItem);
        }
        return addInList;
    }

    private String sumOfItemsByDate(String centerName, String itemCode, String selectedMonth) {
        String itemSum = "";
        List<AssetsStock> findCenterItems = assetsStockRepository.findAllByCenterNameAndItemCodeAndMonth(centerName, itemCode, selectedMonth);
        float itemInFloat = 0L;
        if (findCenterItems.size() > 0) {

            for (AssetsStock fs : findCenterItems) {
                itemInFloat = itemInFloat + Float.parseFloat(fs.getQty());

            }

        }

        itemSum = String.valueOf(itemInFloat);

        return itemSum;
    }

    private List<StockOutputArray> getStockArray(String centerName, String selectedMonth) {
        HashSet<String> uniqueCode = new HashSet<>();
        List<StockOutputArray> addInArrayList = new ArrayList<>();
        List<AssetsStock> findDetails = assetsStockRepository.findAllByCenterNameAndMonthOrderByDateDesc(centerName, selectedMonth);

        for (AssetsStock findItemsWise : findDetails) {
            long getMills = findItemsWise.getDate();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);

            if (uniqueCode.add(findItemsWise.getItemCode())) {
                StockOutputArray singleEntry = StockOutputArray.builder()
                        .centerName(findItemsWise.getCenterName())
                        .itemCode(findItemsWise.getItemCode())
                        .itemName(findItemsWise.getItemName())
                        .quantity(sumOfItemsByDate(findItemsWise.getCenterName(), findItemsWise.getItemCode(), selectedMonth))
                        .unit(findItemsWise.getQtyUnit())
                        .build();
                addInArrayList.add(singleEntry);
            }

        }

        return addInArrayList;
    }


    @Override
    public StockOutputItemsDTO getStocks(String centerName, String selectedMonth) {

        List<AssetsStock> findByCenterName = assetsStockRepository.findAllByCenterNameAndMonthOrderByCreatedDateAsc(centerName, selectedMonth);
        HashSet<String> captureMonth = new HashSet<>();
        List<StockOutputItemsDTO> addInList = new ArrayList<>();
        StockOutputItemsDTO addSingle = new StockOutputItemsDTO();
        if (findByCenterName.size() > 0) {
            for (AssetsStock findMonth : findByCenterName) {
                long getMills = findMonth.getDate();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date(getMills);
                if (captureMonth.add(df.format(date))) {
                    log.info("date " + df.format(date));
                    addSingle = StockOutputItemsDTO.builder()
                            .date(df.format(date))
                            .stockArrayList(getStockArray(centerName, selectedMonth))
                            .build();
                }

            }
        } else {
            addSingle = StockOutputItemsDTO.builder()
                    .date("")
                    .stockArrayList(Collections.EMPTY_LIST)
                    .build();
        }

        return addSingle;
    }

    @Override
    public List<StockListDTO> getStocksLists() {

        List<StockList> stockLists = stockListRepository.findAll();
        List<StockListDTO> addInList = new ArrayList<>();
        for (StockList loopStocks : stockLists) {
            StockListDTO addSingle = modelMapper.map(loopStocks, StockListDTO.class);
            addInList.add(addSingle);

        }
        return addInList;

    }

    @Override
    public List<StockDistributionDTO> saveDistributionList(List<StockDistributionDTO> stockDistributionDTOS, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        long mills = date.getTime();
        List<StockDistributionDTO> addInList = new ArrayList<>();
        String[] spiltMonth = df.format(date).split("-");
        String currentMonth = spiltMonth[1].replace("0", "");

        for (StockDistributionDTO stockList : stockDistributionDTOS) {

            StockDistribution saveEntry = StockDistribution.builder()
                    .centerName(centerName)
                    .date(mills)
                    .month(currentMonth)
                    .familyId(stockList.getFamilyId())
                    .itemCode(stockList.getItemCode())
                    .itemName(stockList.getItemName())
                    .quantity(stockList.getQuantity())
                    .unit(stockList.getUnit())
                    .build();

            stockDistributionRepository.save(saveEntry);

            StockDistributionDTO singleEntry = StockDistributionDTO.builder()
                    .centerName(centerName)
                    .date(df.format(date))
                    .familyId(stockList.getFamilyId())
                    .itemCode(stockList.getItemCode())
                    .itemName(stockList.getItemName())
                    .quantity(stockList.getQuantity())
                    .unit(stockList.getUnit())
                    .build();


            addInList.add(singleEntry);

        }

        return addInList;
    }

    private String getDistributionQty(String familyId, String itemCode, String selectedMonth) {

        List<StockDistribution> findItemsQty = stockDistributionRepository.findAllByFamilyIdAndItemCodeAndMonth(familyId, itemCode, selectedMonth, Sort.by(Sort.Direction.ASC, "itemCode"));
        Float sum = 0F;
        String finalQty = "";
        for (StockDistribution findQty : findItemsQty) {

            sum = sum + Float.parseFloat(findQty.getQuantity());
        }
        finalQty = String.valueOf(sum);
        return finalQty;
    }


    private List<DistributionArrayList> getItemArray(String familyId, String selectedMonth) {

        HashSet<String> uniqueItem = new HashSet<>();
        List<StockDistribution> findItems = stockDistributionRepository.findAllByFamilyIdAndMonth(familyId, selectedMonth);
        List<DistributionArrayList> itemList = new ArrayList<>();

        for (StockDistribution items : findItems) {

            if (uniqueItem.add(items.getItemCode())) {

                DistributionArrayList singeList = DistributionArrayList.builder()
                        .itemName(items.getItemName())
                        .itemCode(items.getItemCode())
                        .unit(items.getUnit())
                        .quantity(getDistributionQty(items.getFamilyId(), items.getItemCode(), selectedMonth))
                        .build();

                itemList.add(singeList);
            }
        }

        return itemList;
    }


    @Override
    public List<DistributionOutputList> getDistributionList(String centerName, String selectedMonth) {

        List<StockDistribution> findFamily = stockDistributionRepository.findAllByCenterNameAndMonth(centerName, selectedMonth);
        List<DistributionOutputList> addInList = new ArrayList<>();
        HashSet<String> uniqueFamily = new HashSet<>();


        for (StockDistribution sd : findFamily) {
            String name = "", profilePic = "", houseNo = "", familyId = "";

            List<Family> findHouseholds = familyRepository.findAllByFamilyId(sd.getFamilyId());

            for (Family fy : findHouseholds) {
                houseNo = fy.getHouseNo();
                familyId = fy.getFamilyId();
            }

            List<FamilyMember> findHeadDetails = familyMemberRepository.findAllByFamilyId(sd.getFamilyId(), Sort.by(Sort.Direction.DESC, "createdDate"));

            for (FamilyMember fm : findHeadDetails) {
                if (fm.getRelationWithOwner().trim().equals("0")) {
                    name = fm.getName();
                    profilePic = fm.getPhoto();
                    break;
                } else {
                    name = fm.getName();
                    profilePic = fm.getPhoto();
                }
            }


            if (uniqueFamily.add(sd.getFamilyId())) {

                long getMills = sd.getDate();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = new Date(getMills);


                DistributionOutputList singleEntry = DistributionOutputList.builder()
                        .name(name)
                        .familyId(familyId)
                        .profilePic(profilePic)
                        .houseNo(houseNo)
                        .date(df.format(date))
                        .arrayLists(getItemArray(sd.getFamilyId(), selectedMonth))
                        .build();
                addInList.add(singleEntry);
            }
        }
        return addInList;
    }

//    private String selectMonth(int monthValue) {
//        String month = "";
//        switch (monthValue) {
//            case 1:
//                month = "Jan";
//                break;
//            case 2:
//                month = "Feb";
//                break;
//            case 3:
//                month = "Mar";
//                break;
//            case 4:
//                month = "Apr";
//                break;
//            case 5:
//                month = "May";
//                break;
//            case 6:
//                month = "June";
//                break;
//            case 7:
//                month = "Jul";
//                break;
//            case 8:
//                month = "Aug";
//                break;
//            case 9:
//                month = "Sep";
//                break;
//            case 10:
//                month = "Oct";
//                break;
//            case 11:
//                month = "Nov";
//                break;
//            case 12:
//                month = "Dec";
//                break;
//        }
//        return month;
//    }


    @Override
    public List<AnganwadiAahaarData> getAnganwadiAahaarData(DashboardFilter dashboardFilter) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<AnganwadiAahaarData> addInList = new ArrayList<>();
        HashSet<String> uniqueFood = new HashSet<>();


        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = df.parse(dashboardFilter.getStartDate().trim());
        } else {
            startTime = df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = df.parse(commonMethodsService.endDateOfMonth());
        }


        List<Meals> findMeals = mealsRepository.findAllByMonthCriteria(startTime, endTime, Sort.by(Sort.Direction.ASC, "date"));
        List<AnganwadiAahaarData> dataList = new ArrayList<>();
        HashSet<Integer> uniqueMonth = new HashSet<>();

        for (Meals meals : findMeals) {
            AnganwadiAahaarData singleList = AnganwadiAahaarData.builder()
                    .foodName(meals.getFoodName())
                    .foodCode(meals.getFoodCode())
                    .quantity(meals.getQuantity())
                    .quantityUnit(meals.getQuantityUnit())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .date(df.format(new Date(meals.getDate())))
                    .mealType(meals.getMealType())
                    .build();
                addInList.add(singleList);
        }
        return addInList;
    }

    @Override
    public List<WeightTrackingDTO> getChildrenWeightData(DashboardFilter dashboardFilter) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = df.parse(dashboardFilter.getStartDate().trim());
        } else {
            startTime = df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = df.parse(commonMethodsService.endDateOfMonth());
        }


        List<WeightTracking> findChildren = weightTrackingRepository.findAllByMonthCriteria(startTime, endTime, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<WeightTrackingDTO> addInList = new ArrayList<>();

        for (WeightTracking tracking : findChildren) {
            long getMills = tracking.getDate();
            Date date = new Date(getMills);

            WeightTrackingDTO addSingle = WeightTrackingDTO.builder()
                    .familyId(tracking.getFamilyId())
                    .childId(tracking.getChildId())
                    .date(df.format(date))
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .height(tracking.getHeight())
                    .weight(tracking.getWeight())
                    .build();
            addInList.add(addSingle);
        }

        return addInList;
    }

    @Override
    public List<DashboardAttendanceDTO> getAttendanceData(DashboardFilter dashboardFilter) throws ParseException {

        List<DashboardAttendanceDTO> addInList = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        long startTime = 0, endTime = 0;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = df.parse(dashboardFilter.getStartDate().trim()).getTime();

        } else {
            DateTimeFormatter formatters = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String convertToString = formatters.format(LocalDate.now().withDayOfMonth(1));
            Date date = df.parse(convertToString);
            startTime = date.getTime();
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = df.parse(dashboardFilter.getEndDate().trim()).getTime();
        } else {
            endTime = Long.parseLong(df.format(new Date().getTime()));
        }


        List<Attendance> findAllList = attendanceRepository.findAllByDateRange(startTime, endTime);

        for (Attendance attend : findAllList) {
            Date attendanceDate = new Date(attend.getDate());
            DashboardAttendanceDTO singleEntry = DashboardAttendanceDTO.builder()
                    .centerName(attend.getCenterName())
                    .childId(attend.getChildId())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .date(df.format(attendanceDate))
                    .attendance(attend.getAttendance())
                    .build();

            addInList.add(singleEntry);
        }

        return addInList;

    }

    @Override
    public List<AnganwadiChildrenDTO> getAnganwadiChildrenData(DashboardFilter dashboardFilter) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<AnganwadiChildrenDTO> addInList = new ArrayList<>();

        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = df.parse(dashboardFilter.getStartDate().trim());

        } else {
            startTime = df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = df.parse(commonMethodsService.endDateOfMonth());
        }

        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAllByCreatedDate(startTime, endTime);

        for (AnganwadiChildren childrenList : findChildren) {
            if (childrenList.isRegistered()) {
                addInList.add(AnganwadiChildrenDTO.builder()
                        .startDate(df.format(startTime))
                        .endDate(df.format(endTime))
                        .category(childrenList.getCategory() == null ? "" : childrenList.getCategory())
                        .minority(childrenList.getMinority() == null ? "" : childrenList.getMinority())
                        .childId(childrenList.getChildId() == null ? "" : childrenList.getChildId())
                        .build());
            }
        }

        return addInList;
    }

    @Override
    public List<AnganwadiChildrenList> getAnganwadiChildrenDetails(DashboardFilter dashboardFilter) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<AnganwadiChildrenList> addInList = new ArrayList<>();
        String searchKeyword = dashboardFilter.getSearch() == null ? "" : dashboardFilter.getSearch();
        HashSet<String> uniqueStudent = new HashSet<>();

        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = df.parse(dashboardFilter.getStartDate().trim());

        } else {
            startTime = df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = df.parse(commonMethodsService.endDateOfMonth());
        }

        List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByCreatedDateAndSearch(startTime, endTime, searchKeyword.trim());

        for (AnganwadiChildren dataList : childrenList) {
            if (dataList.isRegistered()) {
                if (uniqueStudent.add(dataList.getChildId())) {
                    Family findReligion = familyRepository.findByFamilyId(dataList.getFamilyId());
                    AnganwadiChildrenList addSingle = AnganwadiChildrenList.builder()
                            .name(dataList.getName() == null ? "" : dataList.getName())
                            .startDate(df.format(startTime))
                            .houseNo(findReligion.getHouseNo() == null ? "" : findReligion.getHouseNo())
                            .childId(dataList.getChildId() == null ? "" : dataList.getChildId())
                            .endDate(df.format(endTime))
                            .motherName(dataList.getMotherName() == null ? "" : dataList.getMotherName())
                            .fatherName(dataList.getFatherName() == null ? "" : dataList.getFatherName())
                            .dob(dataList.getDob())
                            .category(dataList.getCategory() == null ? "" : dataList.getCategory())
                            .religion(findReligion.getReligion() == null ? "" : findReligion.getReligion())
                            .build();
                    addInList.add(addSingle);
                }
            }
        }
        return addInList;
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
