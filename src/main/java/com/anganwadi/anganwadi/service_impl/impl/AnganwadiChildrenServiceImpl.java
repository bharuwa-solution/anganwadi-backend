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
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private final AnganwadiActivitiesRepository anganwadiActivitiesRepository;
    private final MealsTypeRepository mealsTypeRepository;

    @Autowired
    public AnganwadiChildrenServiceImpl(AnganwadiChildrenRepository anganwadiChildrenRepository, FileManagementService fileManagementService,
                                        AttendanceRepository attendanceRepository, ModelMapper modelMapper,
                                        FamilyRepository familyRepository, FamilyMemberRepository familyMemberRepository,
                                        FamilyServiceImpl familyServiceImpl, AssetsStockRepository assetsStockRepository,
                                        StockListRepository stockListRepository, StockDistributionRepository stockDistributionRepository,
                                        MealsRepository mealsRepository, WeightTrackingRepository weightTrackingRepository,
                                        CommonMethodsService commonMethodsService, AttendancePhotoRepository attendancePhotoRepository,
                                        AnganwadiCenterRepository anganwadiCenterRepository, AnganwadiActivitiesRepository anganwadiActivitiesRepository,
                                        MealsTypeRepository mealsTypeRepository) {
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
        this.anganwadiActivitiesRepository = anganwadiActivitiesRepository;
        this.mealsTypeRepository = mealsTypeRepository;
    }


    private void checkAbove3Yrs(SaveAdmissionDTO saveAdmissionDTO) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date date = df.parse(saveAdmissionDTO.getDob());
        long dob = date.getTime();

        LocalDateTime check3YrsCriteria = LocalDateTime.now().minusYears(3);
        ZonedDateTime zdt = ZonedDateTime.of(check3YrsCriteria, ZoneId.systemDefault());

        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Dob : " + dob);
        log.info("Age Limit : " + convertToMills);
        if (dob >= convertToMills) {
            throw new CustomException("Children Below 3 Years, Can't be Added");
        }

    }

    @Override
    public SaveAdmissionDTO saveChildrenRecord(SaveAdmissionDTO saveAdmissionDTO, java.lang.String centerId, java.lang.String centerName) throws ParseException, IOException {
        commonMethodsService.findCenterName(centerId);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat df2 = new SimpleDateFormat("dd-MM-yyyy");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));



        String finalDate = saveAdmissionDTO.getDob();
        Date dob = df2.parse(finalDate);
        log.info("reg " + saveAdmissionDTO.isRegistered());

        AnganwadiChildren saveAdmission = new AnganwadiChildren();
        SaveAdmissionDTO admissionDTO = new SaveAdmissionDTO();

        checkAbove3Yrs(saveAdmissionDTO);

        try {
            Family findFamily = familyRepository.findByFamilyId(saveAdmissionDTO.getFamilyId());
            List<AnganwadiChildren> findExistingRecord = anganwadiChildrenRepository.findAllByChildId(saveAdmissionDTO.getChildId());

            if (findExistingRecord.size() > 0) {
                for (AnganwadiChildren ac : findExistingRecord) {
                    ac.setName(saveAdmissionDTO.getName() == null ? "" : saveAdmissionDTO.getName());
                    ac.setFamilyId(saveAdmissionDTO.getFamilyId() == null ? "" : saveAdmissionDTO.getFamilyId());
                    ac.setChildId(saveAdmissionDTO.getChildId() == null ? "" : saveAdmissionDTO.getChildId());
                    ac.setFatherName(saveAdmissionDTO.getFatherName() == null ? "" : saveAdmissionDTO.getFatherName());
                    ac.setMotherName(saveAdmissionDTO.getMotherName() == null ? "" : saveAdmissionDTO.getMotherName());
                    ac.setCenterName(commonMethodsService.findCenterName(centerId));
                    ac.setCenterId(centerId);
                    ac.setIsGoingSchool("0");
                    ac.setDob(df.format(dob));
                    ac.setGender(saveAdmissionDTO.getGender() == null ? "" : saveAdmissionDTO.getGender());
                    ac.setMobileNumber(saveAdmissionDTO.getMobileNumber() == null ? "" : saveAdmissionDTO.getMobileNumber());
                    ac.setCategory(findFamily.getCategory() == null ? "" : findFamily.getCategory());
                    ac.setMinority(findFamily.getIsMinority() == null ? "" : findFamily.getIsMinority());
                    ac.setHandicap(saveAdmissionDTO.getHandicap() == null ? "" : saveAdmissionDTO.getHandicap());
                    ac.setProfilePic(saveAdmissionDTO.getProfilePic() == null ? "" : saveAdmissionDTO.getProfilePic());
                    ac.setRegistered(saveAdmissionDTO.isRegistered());
                    ac.setDeleted(false);
                    anganwadiChildrenRepository.save(ac);
                    admissionDTO = modelMapper.map(ac, SaveAdmissionDTO.class);
                }
            } else {
                saveAdmission = AnganwadiChildren.builder()
                        .name(saveAdmissionDTO.getName() == null ? "" : saveAdmissionDTO.getName())
                        .familyId(saveAdmissionDTO.getFamilyId() == null ? "" : saveAdmissionDTO.getFamilyId())
                        .childId(saveAdmissionDTO.getChildId() == null ? "" : saveAdmissionDTO.getChildId())
                        .fatherName(saveAdmissionDTO.getFatherName() == null ? "" : saveAdmissionDTO.getFatherName())
                        .motherName(saveAdmissionDTO.getMotherName() == null ? "" : saveAdmissionDTO.getMotherName())
                        .isRegistered(saveAdmissionDTO.isRegistered())
                        .centerName(centerName)
                        .centerId(centerId)
                        .isGoingSchool("0")
                        .dob(df.format(dob))
                        .gender(saveAdmissionDTO.getGender() == null ? "" : saveAdmissionDTO.getGender())
                        .mobileNumber(saveAdmissionDTO.getMobileNumber() == null ? "" : saveAdmissionDTO.getMobileNumber())
                        .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                        .minority(findFamily.getIsMinority() == null ? "" : findFamily.getIsMinority())
                        .handicap(saveAdmissionDTO.getHandicap() == null ? "" : saveAdmissionDTO.getHandicap())
                        .profilePic(saveAdmissionDTO.getProfilePic() == null ? "" : saveAdmissionDTO.getProfilePic())
                        .build();

                anganwadiChildrenRepository.save(saveAdmission);
                admissionDTO = modelMapper.map(saveAdmission, SaveAdmissionDTO.class);
            }
            return admissionDTO;

        } catch (Exception e) {
            throw new CustomException("Their is Some Error, Please Contact Support Team");
        }
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

        // Update in Anganwadi Children
        if (anganwadiChildrenRepository.findById(updateStudentDTO.getId().trim()).isPresent()) {
            AnganwadiChildren ac = anganwadiChildrenRepository.findById(updateStudentDTO.getId().trim()).get();

            ac.setProfilePic(updateStudentDTO.getProfilePic() == null ? "" : updateStudentDTO.getProfilePic());
            ac.setDob(updateStudentDTO.getDob() == null ? "" : updateStudentDTO.getDob());
            ac.setGender(updateStudentDTO.getGender() == null ? "" : updateStudentDTO.getGender());
            ac.setName(updateStudentDTO.getName() == null ? "" : updateStudentDTO.getName());
            ac.setHandicap(updateStudentDTO.getHandicap() == null ? "" : updateStudentDTO.getHandicap());
            ac.setIsGoingSchool(updateStudentDTO.getIsGoingSchool()==null?"0":updateStudentDTO.getIsGoingSchool());
            anganwadiChildrenRepository.save(ac);

            // Update in Family Children

            if (familyMemberRepository.findById(updateStudentDTO.getChildId().trim()).isPresent()) {
                FamilyMember findChild = familyMemberRepository.findById(updateStudentDTO.getChildId().trim()).get();

                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date formatToTime = df.parse(updateStudentDTO.getDob().trim());
                long dob = formatToTime.getTime();

                findChild.setDob(dob);
                findChild.setName(updateStudentDTO.getName() == null ? "" : updateStudentDTO.getName());
                findChild.setGender(updateStudentDTO.getGender() == null ? "" : updateStudentDTO.getGender());
                findChild.setHandicap(updateStudentDTO.getHandicap() == null ? "" : updateStudentDTO.getHandicap());
                familyMemberRepository.save(findChild);
            }

            // Update in Attendance Table

            List<Attendance> findInAttend = attendanceRepository.findAllByChildId(updateStudentDTO.getChildId());

            if (findInAttend.size() > 0) {
                for (Attendance attend : findInAttend) {
                    attend.setName(updateStudentDTO.getName() == null ? "" : updateStudentDTO.getName());
                    attend.setGender(updateStudentDTO.getGender() == null ? "" : updateStudentDTO.getGender());
                    attend.setPhoto(updateStudentDTO.getProfilePic() == null ? "" : updateStudentDTO.getProfilePic());
                    attendanceRepository.save(attend);
                }
            }

            return UpdateStudentDTO.builder()
                    .childId(ac.getChildId() == null ? "" : ac.getChildId())
                    .dob(ac.getDob() == null ? "" : ac.getDob())
                    .gender(ac.getGender() == null ? "" : ac.getGender())
                    .handicap(ac.getHandicap() == null ? "" : ac.getHandicap())
                    .profilePic(ac.getProfilePic() == null ? "" : ac.getProfilePic())
                    .id(ac.getId())
                    .isGoingSchool(ac.getIsGoingSchool() == null ? "" : ac.getIsGoingSchool())
                    .name(ac.getName() == null ? "" : ac.getName())
                    .deleted(ac.isDeleted())
                    .build();


        } else {
            throw new CustomException("Child Not Found");
        }


    }

    @Override
    public UpdateStudentDTO deleteStudentDetails(String id) {

        if (anganwadiChildrenRepository.findById(id).isPresent()) {
            AnganwadiChildren findStudent = anganwadiChildrenRepository.findById(id).get();

            findStudent.setDeleted(true);
            findStudent.setRegistered(false);
            anganwadiChildrenRepository.save(findStudent);

            return UpdateStudentDTO.builder()
                    .childId(findStudent.getChildId() == null ? "" : findStudent.getChildId())
                    .dob(findStudent.getDob() == null ? "" : findStudent.getDob())
                    .gender(findStudent.getGender() == null ? "" : findStudent.getGender())
                    .profilePic(findStudent.getProfilePic() == null ? "" : findStudent.getProfilePic())
                    .id(findStudent.getId())
                    .name(findStudent.getName() == null ? "" : findStudent.getName())
                    .deleted(findStudent.isDeleted())
                    .build();

        } else {
            throw new CustomException("Student Not Found");
        }
    }

    @Override
    public List<PartialStudentList> getStudentListByChildId(PartialStudentList partialStudentList) {

        if (StringUtils.isEmpty(partialStudentList.getChildId())) {
            throw new CustomException("Child Id is Empty, Please Check!!");
        }
        List<PartialStudentList> addInList = new ArrayList<>();

            String[] splitComma = partialStudentList.getChildId().trim().split(",");

            for (String childId : splitComma) {

                List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByChildIdAndRegisteredTrue(childId.trim());

                for (AnganwadiChildren ac : childrenList) {

                    PartialStudentList singeList = PartialStudentList.builder()
                            .childId(ac.getChildId()==null?"":ac.getChildId())
                            .name(ac.getName() == null ? "" : ac.getName())
                            .build();

                    addInList.add(singeList);
                }

            }

        return addInList;
    }

    private long getRationQty(String itemCode, List<StockDistribution> stockDistributionList) {
        long totalQty = 0;

        for (StockDistribution qty : stockDistributionList) {
            if (qty.getItemCode().trim().equals(itemCode)) {

                totalQty += Long.parseLong(qty.getQuantity());
            }
        }
        return totalQty;
    }


    @Override
    public List<RationDistribution> getRationDistributionData(DashboardFilter dashboardFilter) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<RationDistribution> addInList = new ArrayList<>();
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

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE,1);
        endTime = addOneDay.getTime();

        List<StockDistribution> stockDistributionList = stockDistributionRepository.findAllByDistributionCriteria(startTime, endTime, dashboardFilter.getCenterId().trim());

        addOneDay.add(Calendar.DATE,-1);
        endTime = addOneDay.getTime();

        for (StockDistribution sc : stockDistributionList) {
            if (uniqueFood.add(sc.getItemCode().trim())) {
                RationDistribution rd = RationDistribution.builder()
                        .itemName(sc.getItemName())
                        .centerId(sc.getCenterId())
                        .itemCode(sc.getItemCode())
                        .quantityUnit(sc.getUnit())
                        .distribution(String.valueOf(getRationQty(sc.getItemCode().trim(), stockDistributionList)))
                        .allocated(String.valueOf(new Random().nextInt(40)))
                        .shorted("")
                        .access("")
                        .build();
                addInList.add(rd);
            }
        }
        return addInList;
    }

    @Override
    public List<SaveActivitiesDTO> saveActivity(AnganwadiActivitiesDTO anganwadiActivitiesDTO, String centerId, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        List<SaveActivitiesDTO> addList = new ArrayList<>();

        AnganwadiActivities ac = anganwadiActivitiesRepository.findByCenterIdAndDate(centerId, timestamp);

        if (ac != null) {
            ac.setCleaning(anganwadiActivitiesDTO.isCleaning());
            ac.setGaming(anganwadiActivitiesDTO.isGaming());
            ac.setPreEducation(anganwadiActivitiesDTO.isPreEducation());
            anganwadiActivitiesRepository.save(ac);
        } else {
            anganwadiActivitiesRepository.save(AnganwadiActivities.builder()
                    .centerId(centerId)
                    .centerName(centerName)
                    .gaming(anganwadiActivitiesDTO.isGaming())
                    .preEducation(anganwadiActivitiesDTO.isPreEducation())
                    .cleaning(anganwadiActivitiesDTO.isCleaning())
                    .date(timestamp)
                    .build());
        }

        long startTime = 0, endTime = 0;

        startTime = df.parse(commonMethodsService.startDateOfMonth()).getTime();

        endTime = df.parse(commonMethodsService.endDateOfMonth()).getTime();

        List<AnganwadiActivities> findAc = anganwadiActivitiesRepository.findAllByDateRange(startTime, endTime, centerId.trim());

        for (AnganwadiActivities activities : findAc) {
            addList.add(SaveActivitiesDTO.builder()
                    .id(activities.getId())
                    .centerId(activities.getCenterId())
                    .centerName(activities.getCenterName())
                    .childrenCount(getChildrenPresentCounts(commonMethodsService.findCenterName(centerId), activities.getDate()))
                    .gaming(activities.isGaming())
                    .preEducation(activities.isPreEducation())
                    .cleaning(activities.isCleaning())
                    .date(df.format(activities.getDate()))
                    .build());
        }

        return addList;
    }

    private long getChildrenPresentCounts(String centerName, long date) {

        Set<String> uniqueStudents = new HashSet<>();
        List<Attendance> findPresentCounts = attendanceRepository.findAllByDateAndCenterName(date, centerName, Sort.by(Sort.Direction.ASC, "createdDate"));

        if (findPresentCounts.size() > 0) {
            for (Attendance counts : findPresentCounts) {
                if (counts.isRegistered() && counts.getAttendance().trim().equals("P")) {
                    uniqueStudents.add(counts.getChildId());
                }
            }
        }

        return uniqueStudents.size();
    }

    @Override
    public List<AnganwadiActivitiesDTO> getAllActivity(AnganwadiActivitiesDTO anganwadiActivitiesDTO, String centerId) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        long startTime = 0, endTime = 0;
        List<AnganwadiActivitiesDTO> addList = new ArrayList<>();

        if (anganwadiActivitiesDTO.getStartDate().trim().length() > 0) {
            startTime = df.parse(anganwadiActivitiesDTO.getStartDate().trim()).getTime();
        } else {
            startTime = df.parse(commonMethodsService.startDateOfMonth()).getTime();
        }

        if (anganwadiActivitiesDTO.getEndDate().trim().length() > 0) {
            endTime = df.parse(anganwadiActivitiesDTO.getEndDate().trim()).getTime();
        } else {
            endTime = df.parse(commonMethodsService.endDateOfMonth()).getTime();
        }

        List<AnganwadiActivities> findTodayActivities = anganwadiActivitiesRepository.findAllByDateRange(startTime, endTime, centerId);

        if (findTodayActivities.size() > 0) {
            for (AnganwadiActivities activities : findTodayActivities) {
                addList.add(AnganwadiActivitiesDTO.builder()
                        .id(activities.getId())
                        .centerId(activities.getCenterId())
                        .centerName(activities.getCenterName())
                        .childrenCount(getChildrenPresentCounts(commonMethodsService.findCenterName(centerId), activities.getDate()))
                        .gaming(activities.isGaming())
                        .preEducation(activities.isPreEducation())
                        .cleaning(activities.isCleaning())
                        .date(df.format(activities.getDate()))
                        .startDate(df.format(startTime))
                        .endDate(df.format(endTime))
                        .build());
            }

        }


        return addList;
    }

    private List<FoodItemsDTO> getBreakFastItems(List<MealsType> mealsTypes) {

        List<FoodItemsDTO> breakFastLists = new ArrayList<>();

        for (MealsType breakFast : mealsTypes) {

            if (breakFast.getMealType().trim().equals("1")) {
                breakFastLists.add(FoodItemsDTO.builder()
                        .itemName(breakFast.getItemName().trim())
                        .itemCode(breakFast.getItemCode().trim())
                        .build());
            }

        }

        return breakFastLists;
    }

    private List<FoodItemsDTO> getMealsList(List<MealsType> mealsTypes) {

        List<FoodItemsDTO> MealsLists = new ArrayList<>();

        for (MealsType meals : mealsTypes) {

            if (meals.getMealType().trim().equals("2")) {
                MealsLists.add(FoodItemsDTO.builder()
                        .itemName(meals.getItemName().trim())
                        .itemCode(meals.getItemCode().trim())
                        .build());
            }

        }

        return MealsLists;
    }


    @Override
    public MealTypeDTO getMealsItems() {

        List<MealsType> mealsTypes = mealsTypeRepository.findAll();

        return MealTypeDTO.builder()
                .breakFastLists(getBreakFastItems(mealsTypes))
                .mealsLists(getMealsList(mealsTypes))
                .build();


    }

    @Override
    public SaveMeals saveMeals(SaveMeals saveMeals, String centerId) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        List<Meals> checkMeals = mealsRepository.findAllByMealTypeAndCenterIdAndDate(saveMeals.getMealType(), centerId, timestamp);
        Optional<MealsType> checkItemCode = mealsTypeRepository.findByItemCode(saveMeals.getItemCode().trim());

        if (checkItemCode.isPresent()) {
            if (checkMeals.size() > 0) {
                for (Meals meals : checkMeals) {
                    mealsRepository.deleteById(meals.getId());

                }
            }
            mealsRepository.save(Meals.builder()
                    .date(timestamp)
                    .itemCode(checkItemCode.get().getItemCode())
                    .quantity(saveMeals.getQuantity())
                    .mealType(checkItemCode.get().getMealType())
                    .centerId(centerId)
                    .build());
        } else {
            throw new CustomException("Selected Food Item Is Not Avaiable, Please Contact Anganwadi To Add In List");
        }

        return SaveMeals.builder()
                .date(df.format(timestamp))
                .itemName(checkItemCode.get().getItemName())
                .itemCode(checkItemCode.get().getItemCode())
                .quantityUnit(checkItemCode.get().getQuantityUnit())
                .quantity(saveMeals.getQuantity())
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .mealType(checkItemCode.get().getMealType())
                .build();
    }

    @Override
    public List<SaveMeals> getMonthlyDistributedMeals(DashboardFilter dashboardFilter, String centerId) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<SaveMeals> addInList = new ArrayList<>();

        long startTime, endTime;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = df.parse(dashboardFilter.getStartDate().trim()).getTime();
        } else {
            startTime = df.parse(commonMethodsService.startDateOfMonth()).getTime();
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = df.parse(dashboardFilter.getEndDate().trim()).getTime();
        } else {
            endTime = df.parse(commonMethodsService.endDateOfMonth()).getTime();
        }

        List<Meals> findMonthlyData = mealsRepository.findAllByDateRange(startTime, endTime, centerId);

        if(findMonthlyData.size()>0){
            for(Meals getMeals :  findMonthlyData) {
                Optional<MealsType> checkItemCode = mealsTypeRepository.findByItemCode(getMeals.getItemCode().trim());
                addInList.add(SaveMeals.builder()
                        .itemName(checkItemCode.get().getItemName())
                        .itemCode(checkItemCode.get().getItemCode())
                        .quantityUnit(getMeals.getQuantity())
                        .quantity(getMeals.getQuantity())
                        .mealType(checkItemCode.get().getMealType())
                        .centerId(centerId)
                        .centerName(commonMethodsService.findCenterName(centerId))
                        .date(df.format(getMeals.getDate()))
                        .build());
            }

        }


        return addInList;
    }

    @Override
    public List<ChildrenDTO> getTotalChildren(String centerName) throws ParseException {

        List<ChildrenDTO> addInList = new ArrayList<>();
        List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByCenterNameAndRegisteredTrue(centerName);

        // Check 3 Years Criteria
        // Initially all the age group was added, but currently is was stop from backend && to avoid those old records, below ,condition is added"
        LocalDateTime date = LocalDateTime.now().minusYears(3);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());

        long convertToMills = zdt.toInstant().toEpochMilli();
//        log.info("millis " + convertToMills);
        for (AnganwadiChildren getChildren : childrenList) {

            // Convert Dob to Millis
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date dob_date = df.parse(getChildren.getDob());

            long dob = dob_date.getTime();

            if (dob <= convertToMills && getChildren.getIsGoingSchool().trim().equals("0")) {
//                log.info("Name " + getChildren.getName());
//                log.info("dob " + dob);

                ChildrenDTO childrenDTO = ChildrenDTO.builder()
                        .id(getChildren.getId())
                        .isGoingSchool(getChildren.getIsGoingSchool() == null ? "" : getChildren.getIsGoingSchool())
                        .childId(getChildren.getChildId() == null ? "" : getChildren.getChildId())
                        .dob(getChildren.getDob() == null ? "" : getChildren.getDob())
                        .motherName(getChildren.getMotherName() == null ? "" : getChildren.getMotherName())
                        .fatherName(getChildren.getFatherName() == null ? "" : getChildren.getFatherName())
                        .mobileNumber(getChildren.getMobileNumber() == null ? "" : getChildren.getMobileNumber())
                        .handicap(getChildren.getHandicap() == null ? "" : getChildren.getHandicap())
                        .isRegistered(getChildren.isRegistered())
                        .category(getChildren.getCategory() == null ? "" : getChildren.getCategory())
                        .name(getChildren.getName() == null ? "" : getChildren.getName())
                        .gender(getChildren.getGender() == null ? "" : getChildren.getGender())
                        .profilePic(getChildren.getProfilePic() == null ? "" : getChildren.getProfilePic())
                        .deleted(getChildren.isDeleted())
                        .build();
                addInList.add(childrenDTO);
            }
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

        long convertToMills = commonMethodsService.checkAgeCriteria(3);

        List<Attendance> findRecords = attendanceRepository.findAllByDateAndCenterNameAndRegistered(timestamp, centerName, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<AttendanceDTO> addList = new ArrayList<>();

        for (Attendance singleRecord : findRecords) {

            long millis = df.parse(singleRecord.getDob()).getTime();

            if (millis <= convertToMills) {
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

        long millis = commonMethodsService.checkAgeCriteria(3);

        for (AnganwadiChildren getId : findChildren) {

            long dob = df.parse(getId.getDob()).getTime();

            List<Attendance> lastVerify = attendanceRepository.findAllByChildIdAndDateAndCenterName(getId.getChildId(), timestamp, centerName);
            String verifyAttend = checkAttendanceOnDay(getId.getChildId(), timestamp, centerName);
            if (lastVerify.size() <= 0 && dob <= millis && getId.getIsGoingSchool().equals("0")) {
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
        markAsAbsent(attendanceDTO.getChildId(), centerName);

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
                    .centerName(commonMethodsService.findCenterName(centerId))
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
                    .centerName(commonMethodsService.findCenterName(centerId))
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
    public List<StockDistributionDTO> saveDistributionList(List<StockDistributionDTO> stockDistributionDTOS, String centerId, String centerName) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        long mills = date.getTime();
        List<StockDistributionDTO> addInList = new ArrayList<>();
        String[] spiltMonth = df.format(date).split("-");
        String currentMonth = spiltMonth[1].replace("0", "");

        for (StockDistributionDTO stockList : stockDistributionDTOS) {

            StockDistribution saveEntry = StockDistribution.builder()
                    .centerName(commonMethodsService.findCenterName(centerId))
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
                    .centerName(commonMethodsService.findCenterName(centerId))
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


        Date startTime, endTime;

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

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<Meals> findMeals = mealsRepository.findAllByMonthCriteria(startTime, endTime, Sort.by(Sort.Direction.ASC, "date"), dashboardFilter.getCenterId().trim());

        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        for (Meals meals : findMeals) {
            Optional<MealsType> checkItemCode = mealsTypeRepository.findByItemCode(meals.getItemCode());

            AnganwadiAahaarData singleList = AnganwadiAahaarData.builder()
                    .foodName(checkItemCode.get().getItemName())
                    .centerId(meals.getCenterId())
                    .foodCode(meals.getItemCode())
                    .quantity(meals.getQuantity())
                    .quantityUnit(checkItemCode.get().getQuantityUnit())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .date(df.format(new Date(meals.getDate())))
                    .mealType(checkItemCode.get().getMealType())
                    .build();
            addInList.add(singleList);
        }
        return addInList;
    }

    private String calBMI(String height, String weight) {
        String status = "";
        if (height.trim().length() > 0 && weight.trim().length() > 0) {
            float meter = Float.parseFloat(height) / 100;
            float weightInt = Float.parseFloat(weight);

            float resul = weightInt / (meter * meter);

            log.error("weight " + resul);

            if (resul >= 5 && resul <= 15) {
                status = "NORMAL";
            } else if (resul < 5) {
                status = "UNDER_WEIGHT";
            } else {
                status = "OVER_WEIGHT";
            }
        }

        return status;
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

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<WeightTracking> findChildren = weightTrackingRepository.findAllByMonthCriteria(startTime, endTime, Sort.by(Sort.Direction.ASC, "createdDate"), dashboardFilter.getCenterId().trim());
        List<WeightTrackingDTO> addInList = new ArrayList<>();

        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        for (WeightTracking tracking : findChildren) {
            long getMills = tracking.getDate();
            Date date = new Date(getMills);

            WeightTrackingDTO addSingle = WeightTrackingDTO.builder()
                    .familyId(tracking.getFamilyId())
                    .centerId(tracking.getCenterId())
                    .childId(tracking.getChildId())
                    .date(df.format(date))
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .height(tracking.getHeight())
                    .weight(tracking.getWeight())
                    .bmi(calBMI(tracking.getHeight(), tracking.getWeight()))
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
            startTime = df.parse(commonMethodsService.startDateOfMonth()).getTime();
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = df.parse(dashboardFilter.getEndDate().trim()).getTime();
        } else {
            endTime = df.parse(commonMethodsService.endDateOfMonth()).getTime();
        }

        List<Attendance> findAllList = attendanceRepository.findAllByDateRange(startTime, endTime, dashboardFilter.getCenterId().trim());

        for (Attendance attend : findAllList) {
            Date attendanceDate = new Date(attend.getDate());
            DashboardAttendanceDTO singleEntry = DashboardAttendanceDTO.builder()
                    .centerName(attend.getCenterName())
                    .centerId(attend.getCenterId())
                    .childId(attend.getChildId())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .attendanceType(attend.getAttType() == null ? "" : attend.getAttType())
                    .date(df.format(attendanceDate))
                    .attendance(attend.getAttendance())
                    .build();

            addInList.add(singleEntry);
        }

        return addInList;

    }

//    @Override
//    public List<AnganwadiChildrenDTO> getAnganwadiChildrenData(DashboardFilter dashboardFilter) throws ParseException {
//
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        List<AnganwadiChildrenDTO> addInList = new ArrayList<>();
//
//        Date startTime = null, endTime = null;
//
//        if (dashboardFilter.getStartDate().trim().length() > 0) {
//            startTime = df.parse(dashboardFilter.getStartDate().trim());
//
//        } else {
//            startTime = df.parse(commonMethodsService.startDateOfMonth());
//        }
//
//        if (dashboardFilter.getEndDate().trim().length() > 0) {
//            endTime = df.parse(dashboardFilter.getEndDate().trim());
//        } else {
//            endTime = df.parse(commonMethodsService.endDateOfMonth());
//        }
//
//        Calendar addOneDay = Calendar.getInstance();
//        addOneDay.setTime(endTime);
//        addOneDay.add(Calendar.DATE,1);
//        endTime = addOneDay.getTime();
//
//
//        List<AnganwadiChildren> findChildren = anganwadiChildrenRepository.findAllByCreatedDate(startTime, endTime, dashboardFilter.getCenterId().trim());
//        addOneDay.add(Calendar.DATE,-1);
//        endTime = addOneDay.getTime();
//
//        for (AnganwadiChildren childrenList : findChildren) {
//            if (childrenList.isRegistered()) {
//                addInList.add(AnganwadiChildrenDTO.builder()
//                        .startDate(df.format(startTime))
//                        .centerId(childrenList.getCenterId())
//                        .endDate(df.format(endTime))
//                        .category(childrenList.getCategory() == null ? "" : childrenList.getCategory())
//                        .minority(childrenList.getMinority() == null ? "" : childrenList.getMinority())
//                        .childId(childrenList.getChildId() == null ? "" : childrenList.getChildId())
//                        .build());
//            }
//        }
//
//        return addInList;
//    }

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

        List<AnganwadiChildren> childrenList = anganwadiChildrenRepository.findAllByCreatedDateAndSearch(startTime, endTime, searchKeyword.trim(), dashboardFilter.getCenterId().trim());

        for (AnganwadiChildren dataList : childrenList) {
            if (dataList.isRegistered() && dataList.getIsGoingSchool().equals("0")) {
                if (uniqueStudent.add(dataList.getChildId())) {
                    Family findReligion = familyRepository.findByFamilyId(dataList.getFamilyId());
                    AnganwadiChildrenList addSingle = AnganwadiChildrenList.builder()
                            .name(dataList.getName() == null ? "" : dataList.getName())
                            .centerId(dataList.getCenterId())
                            .startDate(df.format(startTime))
                            .minority(dataList.getMinority() == null ? "" : dataList.getMinority())
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
