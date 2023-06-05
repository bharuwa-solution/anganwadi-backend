package com.anganwadi.anganwadi.service_impl.impl;

import com.anganwadi.anganwadi.config.ApplicationConstants;
import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.*;
import com.anganwadi.anganwadi.exceptionHandler.CustomException;
import com.anganwadi.anganwadi.repositories.*;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class FamilyServiceImpl implements FamilyService {

    private final FamilyRepository familyRepository;
    private final ModelMapper modelMapper;
    private final FamilyMemberRepository familyMemberRepository;
    private final VisitsRepository visitsRepository;
    private final WeightTrackingRepository weightTrackingRepository;
    private final VaccinationRepository vaccinationRepository;
    private final PregnantAndDeliveryRepository pregnantAndDeliveryRepository;
    private final BabiesBirthRepository babiesBirthRepository;
    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final AnganwadiCenterRepository anganwadiCenterRepository;
    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;
    private final CommonMethodsService commonMethodsService;
    private final VaccinationScheduleRepository vaccinationScheduleRepository;
    private final HouseVisitScheduleRepository houseVisitScheduleRepository;

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository, ModelMapper modelMapper, FamilyMemberRepository familyMemberRepository,
                             VisitsRepository visitsRepository, WeightTrackingRepository weightTrackingRepository, VaccinationRepository vaccinationRepository,
                             PregnantAndDeliveryRepository pregnantAndDeliveryRepository, BabiesBirthRepository babiesBirthRepository,
                             AnganwadiChildrenRepository anganwadiChildrenRepository, AnganwadiCenterRepository anganwadiCenterRepository,
                             UserRepository userRepository, AttendanceRepository attendanceRepository, CommonMethodsService commonMethodsService,
                             VaccinationScheduleRepository vaccinationScheduleRepository, HouseVisitScheduleRepository houseVisitScheduleRepository) {
        this.familyRepository = familyRepository;
        this.modelMapper = modelMapper;
        this.familyMemberRepository = familyMemberRepository;
        this.visitsRepository = visitsRepository;
        this.weightTrackingRepository = weightTrackingRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.pregnantAndDeliveryRepository = pregnantAndDeliveryRepository;
        this.babiesBirthRepository = babiesBirthRepository;
        this.anganwadiChildrenRepository = anganwadiChildrenRepository;
        this.anganwadiCenterRepository = anganwadiCenterRepository;
        this.userRepository = userRepository;
        this.attendanceRepository = attendanceRepository;
        this.commonMethodsService = commonMethodsService;
        this.vaccinationScheduleRepository = vaccinationScheduleRepository;
        this.houseVisitScheduleRepository = houseVisitScheduleRepository;
    }



    public int totalHouseholdsMale(String familyId) {
        int totalMale = 0;
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date parseTime = df.parse(convertToString);

        List<FamilyMember> checkFamily = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.DESC, "createdDate"));
//        List<HouseholdsChildren> addList = new ArrayList<>();


        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Time " + convertToMills);

        for (FamilyMember checkMales : checkFamily) {

            if (checkMales.getGender().equalsIgnoreCase("1")) {
                totalMale++;
            }

        }


        return totalMale;

    }

    public int totalHouseholdsFemale(String familyId) {
        int totalFemale = 0;
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date parseTime = df.parse(convertToString);

        List<FamilyMember> checkFamily = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.DESC, "createdDate"));
//        List<HouseholdsChildren> addList = new ArrayList<>();


        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Time " + convertToMills);

        for (FamilyMember checkMales : checkFamily) {

            if (checkMales.getGender().equalsIgnoreCase("2")) {
                totalFemale++;
            }

        }


        return totalFemale;

    }

    public int totalHouseholdsChildren(String familyId) {
        int totalChildren = 0;
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date parseTime = df.parse(convertToString);

        List<FamilyMember> checkFamily = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.DESC, "createdDate"));
//        List<HouseholdsChildren> addList = new ArrayList<>();


        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Time " + convertToMills);

        for (FamilyMember checkChildren : checkFamily) {

            if (checkChildren.getDob() >= convertToMills) {
                totalChildren++;
            }

        }


        return totalChildren;
    }


    @Override
    public List<householdsHeadList> getAllHouseholds(String centerId) {
        List<householdsHeadList> addInList = new ArrayList<>();
        List<Family> familyList = familyRepository.findAllByCenterId(centerId, Sort.by(Sort.Direction.DESC, "createdDate"));

        // Get Head of Family Details

        for (Family getHouseholds : familyList) {
            String headName = "", dob = "", pic = "", gender = "", headId = "";
            int male = 0, female = 0, children = 0;
            if (!getHouseholds.isDeleted()) {

                List<FamilyMember> findHeadDetails = familyMemberRepository.findAllByFamilyId(getHouseholds.getFamilyId().trim(), Sort.by(Sort.Direction.DESC, "createdDate"));
                boolean headFound = false;

                for (int i = 0; i <= findHeadDetails.size(); i++) {

                    for (FamilyMember checkDetails : findHeadDetails) {

                        long getDob = checkDetails.getDob();
                        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                        Date date = new Date(getDob);


                        if (checkDetails.getRelationWithOwner().equalsIgnoreCase("0")) {
                            headName = checkDetails.getName();
                            headId = checkDetails.getId();
                            dob = df.format(date);
                            pic = checkDetails.getPhoto();
                            gender = checkDetails.getGender();
                            break;
                        } else {
                            headName = checkDetails.getName();
                            headId = checkDetails.getId();
                            dob = df.format(date);
                            pic = checkDetails.getPhoto();
                            gender = checkDetails.getGender();
                        }
                    }
                }


                // Count Members
                long countMembers = familyMemberRepository.countByFamilyId(getHouseholds.getFamilyId());

                // Map Details
                householdsHeadList householdsDTO = householdsHeadList.builder()
                        .id(getHouseholds.getId())
                        .headName(headName)
                        .headDob(dob)
                        .houseNo(getHouseholds.getHouseNo())
                        .familyId(getHouseholds.getFamilyId())
                        .religion(getHouseholds.getReligion())
                        .headGender(gender)
                        .totalMale(totalHouseholdsMale(getHouseholds.getFamilyId()))
                        .totalFemale(totalHouseholdsFemale(getHouseholds.getFamilyId()))
                        .totalChildren(totalHouseholdsChildren(getHouseholds.getFamilyId()))
                        .category(getHouseholds.getCategory())
                        .totalMembers(String.valueOf(countMembers))
                        .headPic(pic)
                        .build();
                addInList.add(householdsDTO);
            }
        }

        return addInList;
    }


    @Override
    public HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO, String centerId, String centerName) throws ParseException {

        String name = householdsDTO.getHeadName() == null ? "" : householdsDTO.getHeadName();
        String headDob = householdsDTO.getHeadDob() == null ? "" : householdsDTO.getHeadDob();
        String centerID = centerId == null ? "" : centerId;
        String centerNames = centerName == null ? "" : centerName;
        String houseNo = householdsDTO.getHouseNo() == null ? "" : householdsDTO.getHouseNo();
        String mobileNo = householdsDTO.getMobileNumber() == null ? "" : householdsDTO.getMobileNumber();
        String headPic = householdsDTO.getHeadPic() == null ? "" : householdsDTO.getHeadPic();
        String uniqueIdType = householdsDTO.getUniqueIdType() == null ? "" : householdsDTO.getUniqueIdType();
        String uniqueCode = householdsDTO.getUniqueCode() == null ? "" : householdsDTO.getUniqueCode();
        String uniqueId = householdsDTO.getUniqueId() == null ? "" : householdsDTO.getUniqueId();
        String category = householdsDTO.getCategory() == null ? "" : householdsDTO.getCategory();
        String religion = householdsDTO.getReligion() == null ? "" : householdsDTO.getReligion();
        String isMinority = householdsDTO.getIsMinority() == null ? "" : householdsDTO.getIsMinority();
        String icdsService = householdsDTO.getIcdsService() == null ? "" : householdsDTO.getIcdsService();
        String headGender = householdsDTO.getHeadGender() == null ? "" : householdsDTO.getHeadGender();

        commonMethodsService.findCenterName(centerID);


        long currentTime = System.currentTimeMillis();
        String familyId = ApplicationConstants.familyId + currentTime;

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse(householdsDTO.getHeadDob());
        long mills = date.getTime();

        Date currentMonth = new Date();
        String[] splitMonth = df.format(currentMonth).split("-");
        String getMonth = splitMonth[1].replace("0", "");

        Family saveFamily = Family.builder()
                .houseNo(houseNo)
                .familyId(familyId)
                .centerName(centerNames)
                .centerId(centerID)
                .category(category)
                .religion(religion)
                .isMinority(isMinority)
                .icdsService(icdsService)
                .build();
        familyRepository.save(saveFamily);

        FamilyMember saveInMember = FamilyMember.builder()
                .name(name)
                .dob(mills)
                .centerId(centerID)
                .centerName(centerNames)
                .maritalStatus("1")
                .familyId(familyId)
                .idNumber(uniqueId)
                .idType(uniqueIdType)
                .dateOfMortality("")
                .dateOfLeaving("")
                .dateOfArrival("")
                .motherName("")
                .fatherName("")
                .handicapType("")
                .handicap("")
                .memberCode("")
                .residentArea("")
                .stateCode("")
                .recordForMonth(getMonth)
                .mobileNumber(mobileNo)
                .category(category)
                .photo(headPic)
                .relationWithOwner("0")
                .gender(headGender)
                .build();

        familyMemberRepository.save(saveInMember);

        return HouseholdsDTO.builder()
                .id(saveInMember.getId())
                .headName(name)
                .centerName(centerName)
                .headDob(headDob)
                .uniqueCode(uniqueCode)
                .uniqueId(uniqueId)
                .totalMembers("")
                .headPic(headPic)
                .centerId(centerID)
                .headGender(headGender)
                .houseNo(houseNo)
                .mobileNumber(mobileNo)
                .uniqueIdType(uniqueIdType)
                .uniqueId(uniqueId)
                .category(category)
                .religion(religion)
                .isMinority(isMinority)
                .icdsService(icdsService)
                .build();
    }

    @Override
    public FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO, String centerId, String centerName) throws ParseException {

        String name = familyMemberDTO.getName() == null ? "" : familyMemberDTO.getName();
        String relationShip = familyMemberDTO.getRelationWithOwner() == null ? "" : familyMemberDTO.getRelationWithOwner();
        String gender = familyMemberDTO.getGender() == null ? "" : familyMemberDTO.getGender();
        String dob = familyMemberDTO.getDob() == null ? "" : familyMemberDTO.getDob();
        String martialStatus = familyMemberDTO.getMaritalStatus() == null ? "" : familyMemberDTO.getMaritalStatus();
        String stateCode = familyMemberDTO.getStateCode() == null ? "" : familyMemberDTO.getStateCode();
        String handicap = familyMemberDTO.getHandicap() == null ? "" : familyMemberDTO.getHandicap();
        String handicapType = familyMemberDTO.getHandicapType() == null ? "" : familyMemberDTO.getHandicapType();
        String area = familyMemberDTO.getResidentArea() == null ? "" : familyMemberDTO.getResidentArea();
        String arrivalDate = familyMemberDTO.getDateOfArrival() == null ? "" : familyMemberDTO.getDateOfArrival();
        String leavingDate = familyMemberDTO.getDateOfLeaving() == null ? "" : familyMemberDTO.getDateOfLeaving();
        String deathDate = familyMemberDTO.getDateOfMortality() == null ? "" : familyMemberDTO.getDateOfMortality();
        String code = familyMemberDTO.getMemberCode() == null ? "" : familyMemberDTO.getMemberCode();
        String photo = familyMemberDTO.getPhoto() == null ? "" : familyMemberDTO.getPhoto();
        String category = familyMemberDTO.getCategory() == null ? "" : familyMemberDTO.getCategory();
        String motherName = familyMemberDTO.getMotherName() == null ? "" : familyMemberDTO.getMotherName();
        String fatherName = familyMemberDTO.getFatherName() == null ? "" : familyMemberDTO.getFatherName();
        String memberCode = familyMemberDTO.getMemberCode() == null ? "" : familyMemberDTO.getMemberCode();

        log.info("centerId " + centerId);
        log.info("centerName " + centerName);

        commonMethodsService.findCenterName(centerId);

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse(familyMemberDTO.getDob());
        long mills = date.getTime();
        String headCategory = "";

        List<Family> findHod = familyRepository.findAllByFamilyId(familyMemberDTO.getFamilyId());
        for (Family getFamilyId : findHod) {
            headCategory = getFamilyId.getCategory();
        }

        boolean isRegistered = false;
        if (familyMemberDTO.isRegistered()) {
            isRegistered = true;
        }

        Date currentMonth = new Date();
        String[] splitMonth = df.format(currentMonth).split("-");
        String getMonth = splitMonth[1].replace("0", "");


        FamilyMember saveMember = FamilyMember.builder()
                .name(name)
                .relationWithOwner(relationShip)
                .gender(gender)
                .familyId(familyMemberDTO.getFamilyId())
                .mobileNumber(familyMemberDTO.getMobileNumber() == null ? "" : familyMemberDTO.getMobileNumber())
                .idType(familyMemberDTO.getIdType() == null ? "" : familyMemberDTO.getIdType())
                .idNumber(familyMemberDTO.getIdNumber() == null ? "" : familyMemberDTO.getIdNumber())
                .dob(mills)
                .recordForMonth(getMonth)
                .centerId(centerId)
                .centerName(centerName)
                .motherName(motherName)
                .fatherName(fatherName)
                .memberCode(memberCode)
                .memberCode(code)
                .category(category.equals("") ? headCategory : category)
                .maritalStatus(martialStatus)
                .stateCode(stateCode)
                .handicap(handicap)
                .handicapType(handicapType)
                .residentArea(area)
                .dateOfArrival(arrivalDate)
                .dateOfLeaving(leavingDate)
                .dateOfMortality(deathDate)
                .photo(photo)
                .isRegistered(isRegistered)
                .build();

        familyMemberRepository.save(saveMember);

        Date formatDate = new Date(mills);

        return FamilyMemberDTO.builder()
                .id(saveMember.getId())
                .name(name)
                .relationWithOwner(relationShip)
                .gender(gender)
                .centerName(centerName)
                .memberCode(code)
                .familyId(familyMemberDTO.getFamilyId())
                .mobileNumber(familyMemberDTO.getMobileNumber() == null ? "" : familyMemberDTO.getMobileNumber())
                .idType(familyMemberDTO.getIdType() == null ? "" : familyMemberDTO.getIdType())
                .idNumber(familyMemberDTO.getIdNumber() == null ? "" : familyMemberDTO.getIdNumber())
                .dob(df.format(formatDate))
                .maritalStatus(martialStatus)
                .stateCode(stateCode)
                .handicap(handicap)
                .handicapType(handicapType)
                .motherName(motherName)
                .fatherName(fatherName)
                .memberCode(memberCode)
                .residentArea(area)
                .dateOfArrival(arrivalDate)
                .dateOfLeaving(leavingDate)
                .dateOfMortality(deathDate)
                .photo(photo)
                .isRegistered(isRegistered)
                .build();
    }


//    @Override
//    public FamilyMemberDTO updateRegisteredValue(String id, boolean isRegistered) {
//
//        FamilyMember findId = familyMemberRepository.findById(id).get();
//
//        if (findId != null) {
//
//            findId.setRegistered(isRegistered);
//            familyMemberRepository.save(findId);
//
//        }
//
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = new Date(findId.getDob());
//
//
//        return FamilyMemberDTO.builder()
//                .id(findId.getId())
//                .name(findId.getName())
//                .relationWithOwner(findId.getRelationWithOwner())
//                .gender(findId.getGender())
//                .centerName(findId.getCenterName())
//                .memberCode(findId.getMemberCode())
//                .familyId(findId.getFamilyId())
//                .mobileNumber(findId.getMobileNumber() == null ? "" : findId.getMobileNumber())
//                .idType(findId.getIdType() == null ? "" : findId.getIdType())
//                .idNumber(findId.getIdNumber() == null ? "" : findId.getIdNumber())
//                .dob(df.format(date))
//                .maritalStatus(findId.getMaritalStatus())
//                .stateCode(findId.getStateCode())
//                .handicap(findId.getHandicap())
//                .handicapType(findId.getHandicapType())
//                .motherName(findId.getMotherName())
//                .fatherName(findId.getFatherName())
//                .residentArea(findId.getResidentArea())
//                .dateOfArrival(findId.getDateOfArrival())
//                .dateOfLeaving(findId.getDateOfLeaving())
//                .dateOfMortality(findId.getDateOfMortality())
//                .photo(findId.getPhoto())
//                .isRegistered(isRegistered)
//                .build();
//    }

    @Override
    public List<FamilyMemberDTO> getFamilyMembers(String familyId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }

        Family checkDeleted = familyRepository.findByFamilyId(familyId);

        if (checkDeleted.isDeleted()) {
            throw new CustomException("The Family Is Deleted, Please Check With Support Team, to Re-activate It!!");
        }


        List<FamilyMemberDTO> addInList = new ArrayList<>();
        List<FamilyMember> getMembers = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.ASC, "createdDate"));
        String gender = "";
        for (FamilyMember passDetails : getMembers) {

            String handicap = "";


            long getMills = passDetails.getDob();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);


            FamilyMemberDTO singleList = FamilyMemberDTO
                    .builder()
                    .id(passDetails.getId())
                    .name(passDetails.getName() == null ? "" : passDetails.getName())
                    .photo(passDetails.getPhoto() == null ? "" : passDetails.getPhoto())
                    .category(passDetails.getCategory() == null ? "" : passDetails.getCategory())
                    .familyId(passDetails.getFamilyId() == null ? "" : passDetails.getFamilyId())
                    .mobileNumber(passDetails.getMobileNumber() == null ? "" : passDetails.getMobileNumber())
                    .relationWithOwner(passDetails.getRelationWithOwner() == null ? "" : passDetails.getRelationWithOwner())
                    .idType(passDetails.getIdType() == null ? "" : passDetails.getIdType())
                    .memberCode(passDetails.getMemberCode() == null ? "" : passDetails.getMemberCode())
                    .motherName(passDetails.getMotherName() == null ? "" : passDetails.getMotherName())
                    .fatherName(passDetails.getFatherName() == null ? "" : passDetails.getFatherName())
                    .idNumber(passDetails.getIdNumber() == null ? "" : passDetails.getIdNumber())
                    .gender(passDetails.getGender() == null ? "" : passDetails.getGender())
                    .dob(df.format(date))
                    .centerName(commonMethodsService.findCenterName(passDetails.getCenterId()))
                    .category(passDetails.getCategory()) //join
                    .idType(passDetails.getIdType())
                    .idNumber(passDetails.getIdNumber())
                    .maritalStatus(passDetails.getMaritalStatus() == null ? "" : passDetails.getMaritalStatus())
                    .stateCode(passDetails.getStateCode() == null ? "" : passDetails.getStateCode())
                    .handicap(passDetails.getHandicap() == null ? "" : passDetails.getHandicap())
                    .handicapType(passDetails.getHandicapType() == null ? "" : passDetails.getHandicapType())
                    .residentArea(passDetails.getResidentArea() == null ? "" : passDetails.getResidentArea())
                    .dateOfArrival(passDetails.getDateOfArrival() == null ? "" : passDetails.getDateOfArrival())
                    .dateOfLeaving(passDetails.getDateOfLeaving() == null ? "" : passDetails.getDateOfLeaving())
                    .dateOfMortality(passDetails.getDateOfLeaving() == null ? "" : passDetails.getDateOfLeaving())
                    .build();
            addInList.add(singleList);
        }

        return addInList;
    }

    private void UpdateVaccinationTable(Visits visits) {

        List<VaccinationSchedule> scheduleList = vaccinationScheduleRepository.findAllByMemberIdAndCode(visits.getMemberId().trim(), visits.getVisitFor().trim());

        if (scheduleList.size() > 0) {

            for (VaccinationSchedule vs : scheduleList) {
                vs.setVaccinationDate(visits.getVisitDateTime());
                vaccinationScheduleRepository.save(vs);
            }
        }
    }

    private String selectVisitType(String visitFor) {
        String visitType = "";

        switch (visitFor) {
            case "(1 || 2)":
                visitType = "PREGNANCY";
                break;
            case "3":
                visitType = "DELIVERY";
                break;
            case "4":
                visitType = "BIRTH_VISIT_1";
                break;
            case "5":
                visitType = "BIRTH_VISIT_2";
                break;
            case "6":
                visitType = "BIRTH_VISIT_3";
                break;
            case "7":
                visitType = "BIRTH_VISIT_4";
                break;
            case "8":
                visitType = "BIRTH_VISIT_5";
                break;
            case "9":
                visitType = "BIRTH_VISIT_6";
                break;
            case "10":
                visitType = "BIRTH_VISIT_7";
                break;
        }
        return visitType;
    }

    private String selectVisitName(String visitFor) {

        String visitName = "";

        switch (visitFor) {

            case "1":
                visitName = "TILL_04_06_MONTHS";
                break;
            case "2":
                visitName = "TILL_07_09_MONTHS";
                break;
            case "3":
                visitName = "DAY_OF_DELIVERY";
                break;
            case "4":
                visitName = "TILL_1_7_DAYS";
                break;
            case "5":
                visitName = "TILL_8_30_DAYS";
                break;
            case "6":
                visitName = "TILL_1_5_MONTHS";
                break;
            case "7":
                visitName = "TILL_6_8_MONTHS";
                break;
            case "8":
                visitName = "TILL_9_11_MONTHS";
                break;
            case "9":
                visitName = "TILL_12_17_MONTHS";
                break;
            case "10":
                visitName = "TILL_18_24_DAYS";
                break;


        }
        return visitName;
    }

    private void updateAfterBirthSchedule(Visits visits) {

        List<HouseVisitSchedule> hs = houseVisitScheduleRepository.findAllByMemberId(visits.getMemberId());
        List<HouseVisitSchedule> findMember = new ArrayList<>();

        if (hs.size() > 0 && Integer.parseInt(visits.getVisitRound()) < 3) {
            switch (visits.getVisitType()) {

                case "4":
                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_1", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

                case "5":
                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_2", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

                case "6":
                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_3", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

                case "7":
                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_4", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

                case "8":
                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_5", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

                case "9":
                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_6", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

                case "10":
                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_7", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

            }
        } else {
            HouseVisitSchedule addVisit_1 = HouseVisitSchedule.builder()
                    .visitType(selectVisitType(visits.getVisitFor()))
                    .visitRound(visits.getVisitRound())
                    .comments(visits.getDescription())
                    .latitude(visits.getLatitude())
                    .centerName(visits.getCenterName())
                    .centerId(visits.getCenterId())
                    .longitude(visits.getLongitude())
                    .visitName(selectVisitName(visits.getVisitFor()))
                    .memberId(visits.getMemberId())
                    .visitDate(visits.getVisitDateTime())
                    .dueDate(visits.getVisitDateTime())
                    .build();

            houseVisitScheduleRepository.save(addVisit_1);
        }
    }


    private void updateBeforeBirthSchedule(Visits visits) {
        List<HouseVisitSchedule> hs = houseVisitScheduleRepository.findAllByMemberId(visits.getMemberId());
        List<HouseVisitSchedule> findMember = new ArrayList<>();

        if (hs.size() > 0 && Integer.parseInt(visits.getVisitRound()) < 3) {

            switch (visits.getVisitType()) {

                case "1":
                    findMember = houseVisitScheduleRepository.findAllByVisitNameAndMemberIdAndVisitRound("TILL_04_06_MONTHS", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;

                case "2":
                    findMember = houseVisitScheduleRepository.findAllByVisitNameAndMemberIdAndVisitRound("TILL_07_09_MONTHS", visits.getMemberId(), visits.getVisitRound());
                    if (findMember.size() > 0) {
                        for (HouseVisitSchedule updateVisits : findMember) {
                            updateVisits.setVisitDate(visits.getVisitDateTime());
                            updateVisits.setLongitude(visits.getLongitude());
                            updateVisits.setLatitude(visits.getLatitude());
                            updateVisits.setComments(visits.getDescription());
                            houseVisitScheduleRepository.save(updateVisits);
                        }
                    }
                    break;
            }

        } else {
            HouseVisitSchedule addVisit_1 = HouseVisitSchedule.builder()
                    .visitType(selectVisitType(visits.getVisitFor()))
                    .visitRound(visits.getVisitRound())
                    .comments(visits.getDescription())
                    .latitude(visits.getLatitude())
                    .longitude(visits.getLongitude())
                    .centerName(visits.getCenterName())
                    .centerId(visits.getCenterId())
                    .visitName(selectVisitName(visits.getVisitFor()))
                    .memberId(visits.getMemberId())
                    .visitDate(visits.getVisitDateTime())
                    .dueDate(visits.getVisitDateTime())
                    .build();

            houseVisitScheduleRepository.save(addVisit_1);
        }
    }

    @Override
    public VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO, String centerId, String centerName) throws ParseException {


//        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC+5.30"));

        Date date = new Date();
        String formattedDate = df.format(date);
        log.info("Formatted Date : " + formattedDate);

        Date AfterFormat = df.parse(formattedDate);
        long millis = AfterFormat.getTime();
        log.info("Date in Millis  : " + millis);
        FamilyMember findCat = new FamilyMember();

        if (familyMemberRepository.findById(visitsDetailsDTO.getMemberId()).isPresent()) {
            findCat = familyMemberRepository.findById(visitsDetailsDTO.getMemberId()).get();
        } else {
            throw new CustomException("Member Not Found");
        }
        Visits saveVisitDetails = Visits.builder()
                .memberId(visitsDetailsDTO.getMemberId() == null ? "" : visitsDetailsDTO.getMemberId())
                .familyId(findCat.getFamilyId() == null ? "" : findCat.getFamilyId())
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .visitType(visitsDetailsDTO.getVisitType() == null ? "" : visitsDetailsDTO.getVisitType())
                .visitFor(visitsDetailsDTO.getVisitFor() == null ? "" : visitsDetailsDTO.getVisitFor())
                .description(visitsDetailsDTO.getDescription() == null ? "" : visitsDetailsDTO.getDescription())
                .visitRound(visitsDetailsDTO.getVisitRound() == null ? "" : visitsDetailsDTO.getVisitRound())
                .latitude(visitsDetailsDTO.getLatitude() == null ? "" : visitsDetailsDTO.getLatitude())
                .longitude(visitsDetailsDTO.getLongitude() == null ? "" : visitsDetailsDTO.getLongitude())
                .visitDateTime(millis)
                .category(findCat.getCategory() == null ? "" : findCat.getCategory())
                .childDob(findCat.getDob())
                .build();
        visitsRepository.save(saveVisitDetails);


        // Update Vaccination & House Schedule Table

        if (Integer.parseInt(visitsDetailsDTO.getVisitType()) > 3) {

            // Update in House visit Schedule
            updateAfterBirthSchedule(saveVisitDetails);

            // Update in Vaccination visit Schedule
            UpdateVaccinationTable(saveVisitDetails);
        }
        if (Integer.parseInt(visitsDetailsDTO.getVisitType()) > 0 && Integer.parseInt(visitsDetailsDTO.getVisitType()) < 3) {

            updateBeforeBirthSchedule(saveVisitDetails);


        }


        return modelMapper.map(saveVisitDetails, VisitsDetailsDTO.class);
    }

    @Override
    public WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO, String centerId, String centerName) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String convertToString = df.format(date);
        Date convertToDate = df.parse(convertToString);
        long mills = convertToDate.getTime();
        FamilyMember findDetails = new FamilyMember();
        try {
            findDetails = familyMemberRepository.findById(weightRecordsDTO.getChildId()).get();
        } catch (Exception e) {
            throw new CustomException("Child Not Found");
        }

        WeightTracking saveRecord = WeightTracking.builder()
                .familyId(findDetails.getFamilyId() == null ? "" : findDetails.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .centerId(centerId)
                .date(mills)
                .centerName(commonMethodsService.findCenterName(centerId))
                .height(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .weight(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight())
                .build();

        weightTrackingRepository.save(saveRecord);

        Date dob = new Date(findDetails.getDob());


        return WeightRecordsDTO.builder()
                .familyId(findDetails.getFamilyId() == null ? "" : findDetails.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .name(findDetails.getName() == null ? "" : findDetails.getName())
                .gender(findDetails.getGender() == null ? "" : findDetails.getGender())
                .motherName(findDetails.getMotherName() == null ? "" : findDetails.getMotherName())
                .dob(df.format(dob))
                .houseNo("")
                .photo(findDetails.getPhoto() == null ? "" : weightRecordsDTO.getPhoto())
                .date(df.format(date))
                .centerName(commonMethodsService.findCenterName(centerId))
                .height(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .weight(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight())
                .build();
    }

    @Override
    public WeightRecordsDTO saveWeightRecordsCloned(WeightRecordsDTO weightRecordsDTO, String centerId, String centerName) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        String convertToString = df.format(date);
        Date convertToDate = df.parse(convertToString);
        long mills = convertToDate.getTime();
        FamilyMember findDetails = new FamilyMember();
        try {
            findDetails = familyMemberRepository.findById(weightRecordsDTO.getChildId()).get();
        } catch (Exception e) {
            throw new CustomException("Child Not Found");
        }

        WeightTracking saveRecord = WeightTracking.builder()
                .familyId(findDetails.getFamilyId() == null ? "" : findDetails.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .centerId(centerId)
                .date(mills)
                .centerName(commonMethodsService.findCenterName(centerId))
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight().replaceAll(" ", ""))
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight().replaceAll(" ", ""))
                .build();

        weightTrackingRepository.save(saveRecord);

        Date dob = new Date(findDetails.getDob());


        return WeightRecordsDTO.builder()
                .familyId(findDetails.getFamilyId() == null ? "" : findDetails.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .name(findDetails.getName() == null ? "" : findDetails.getName())
                .gender(findDetails.getGender() == null ? "" : findDetails.getGender())
                .motherName(findDetails.getMotherName() == null ? "" : findDetails.getMotherName())
                .dob(df.format(dob))
                .houseNo("")
                .photo(findDetails.getPhoto() == null ? "" : weightRecordsDTO.getPhoto())
                .date(df.format(date))
                .centerName(commonMethodsService.findCenterName(centerId))
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight().replaceAll(" ", ""))
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight().replaceAll(" ", ""))
                .build();
    }

    @Override
    public List<HouseholdRelCatData> getReligionCategoryData(DashboardFilter dashboardFilter) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<HouseholdRelCatData> addInList = new ArrayList<>();

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

        // Beneficiary Children
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        // Beneficiary dharti
        LocalDateTime dhartiDate = LocalDateTime.now().minusMonths(6);
        ZonedDateTime dhartiZdt = ZonedDateTime.of(dhartiDate, ZoneId.systemDefault());
        long convertToMills2 = dhartiZdt.toInstant().toEpochMilli();


        List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository.findAllByPregnancyCriteria(startTime.getTime(), endTime.getTime(), dashboardFilter.getCenterId().trim());
        log.error("pdd :" + pdd.size());

        List<PregnantAndDelivery> dhartiWomen = pregnantAndDeliveryRepository.findAllBeneficiaryDharti(startTime.getTime(), endTime.getTime(), dashboardFilter.getCenterId().trim(), convertToMills2);
        log.error("dhartiWomen :" + dhartiWomen.size());

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<FamilyMember> findChildren = familyMemberRepository.findAllBeneficiaryChildren(startTime, endTime, dashboardFilter.getCenterId().trim(), convertToMills);
        log.error("findChildren :" + findChildren.size());

        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        // Adding Children
        for (FamilyMember fm : findChildren) {
            Family findFamily = familyRepository.findByFamilyId(fm.getFamilyId());

            HouseholdRelCatData singleEntry_1 = HouseholdRelCatData.builder()
                    .category(fm.getCategory() == null ? "" : fm.getCategory())
                    .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                    .name(fm.getName() == null ? "" : fm.getName())
                    .dob(df.format(new Date(fm.getDob())))
                    .centerId(fm.getCenterId() == null ? "" : fm.getCenterId())
                    .centerName(fm.getCenterName() == null ? "" : fm.getCenterName())
                    .gender(fm.getGender() == null ? "" : fm.getGender())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .build();

            addInList.add(singleEntry_1);

        }

        // Adding Dharti
        for (PregnantAndDelivery dharti : dhartiWomen) {
            Family findFamily = familyRepository.findByFamilyId(dharti.getFamilyId());
            FamilyMember member = familyMemberRepository.findById(dharti.getMotherMemberId()).get();

            HouseholdRelCatData singleEntry_2 = HouseholdRelCatData.builder()
                    .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                    .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                    .name(member.getName() == null ? "" : member.getName())
                    .dob(df.format(new Date(member.getDob())))
                    .centerId(member.getCenterId() == null ? "" : member.getCenterId())
                    .centerName(member.getCenterName() == null ? "" : member.getCenterName())
                    .gender(member.getGender() == null ? "" : member.getGender())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .build();

            addInList.add(singleEntry_2);

        }

        // Adding Pregnant
        for (PregnantAndDelivery pregnant : pdd) {
            Family findFamily = familyRepository.findByFamilyId(pregnant.getFamilyId());
            FamilyMember member = familyMemberRepository.findById(pregnant.getMotherMemberId()).get();

            HouseholdRelCatData singleEntry_3 = HouseholdRelCatData.builder()
                    .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                    .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                    .name(member.getName() == null ? "" : member.getName())
                    .dob(df.format(new Date(member.getDob())))
                    .centerId(member.getCenterId() == null ? "" : member.getCenterId())
                    .centerName(member.getCenterName() == null ? "" : member.getCenterName())
                    .gender(member.getGender() == null ? "" : member.getGender())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .build();

            addInList.add(singleEntry_3);

        }
        log.error("addInList Size " + addInList.size());

        return addInList;
    }

    private List<VaccinationList> getVaccinationList(long dates, String id, List<VaccinationSchedule> vaccinationSchedule) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<VaccinationList> addVaccinationList = new ArrayList<>();

        for (VaccinationSchedule vss : vaccinationSchedule) {
            if (vss.getDueDate() == dates && vss.getMemberId().equals(id)) {
                VaccinationList singleVaccination = VaccinationList.builder()
                        .name(vss.getVaccinationName())
                        .code(vss.getCode())
                        .dueDate(df.format(vss.getDueDate()))
                        .vaccinatedDate(df.format(vss.getVaccinationDate()))
                        .build();
                addVaccinationList.add(singleVaccination);
            }
        }

        return addVaccinationList;
    }

    private DeliveryList getDeliveryList(long dates, List<HouseVisitSchedule> houseVisit) {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        long LMPDate = 0, actualDelivery = 0;
        DeliveryList delivery = new DeliveryList();

        for (HouseVisitSchedule hv : houseVisit) {
            if (hv.getDueDate() == dates && hv.getVisitType().equals("DELIVERY")) {
                List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository.findAllByMotherMemberId(hv.getMemberId(), Sort.by(Sort.Direction.ASC, "createdDate"));
                for (PregnantAndDelivery lmpDates : pdd) {
                    LMPDate = lmpDates.getLastMissedPeriodDate();
                    actualDelivery = lmpDates.getDateOfDelivery();
                }
                delivery = DeliveryList.builder()
                        .LMPDate(df.format(LMPDate).length() == 0 ? "" : df.format(LMPDate))
                        .exceptedDeliveryDate(df.format(hv.getDueDate()))
                        .actualDeliveryDate(df.format(actualDelivery))
                        .build();
            } else {
                delivery = DeliveryList.builder()
                        .LMPDate("")
                        .exceptedDeliveryDate("")
                        .actualDeliveryDate("")
                        .build();
            }
        }

        return delivery;
    }

    private List<HouseVisitsList> getHouseVisitList(long dates, String id, List<HouseVisitSchedule> houseVisit) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        List<HouseVisitsList> addHouseList = new ArrayList<>();

        for (HouseVisitSchedule hvv : houseVisit) {
            if (hvv.getDueDate() == dates && hvv.getMemberId().equals(id)) {
                String visitDate = "";
                if(hvv.getVisitDate()==0){
                    visitDate="";
                }
                else {
                    visitDate = df.format(hvv.getVisitDate());
                }
                HouseVisitsList houseSingleList = HouseVisitsList.builder()
                        .name(hvv.getVisitType())
                        .title(hvv.getVisitName())
                        .dueDate(df.format(hvv.getDueDate()))
                        .visitRound(hvv.getVisitRound())
                        .visitDate(visitDate)
                        .comments(hvv.getComments())
                        .latitude(hvv.getLatitude())
                        .longitude(hvv.getLongitude())
                        .build();
                addHouseList.add(houseSingleList);
            }

        }

        return addHouseList;

    }

    private List<MemberDetails> getMembersDetails(long dates, List<VaccinationSchedule> vs, List<HouseVisitSchedule> hs) {
        List<MemberDetails> addInList = new ArrayList<>();

        Set<String> uniqueMember = new TreeSet<>();

        for (VaccinationSchedule vaccinationList : vs) {
            if (vaccinationList.getDueDate() == dates) {
                uniqueMember.add(vaccinationList.getMemberId());
            }
        }

        for (HouseVisitSchedule houseVisitList : hs) {
            if (houseVisitList.getDueDate() == dates) {
                uniqueMember.add(houseVisitList.getMemberId());
            }
        }

        for (String id : uniqueMember) {
            if (familyMemberRepository.findById(id).isPresent()) {
                FamilyMember findMember = familyMemberRepository.findById(id).get();
                Family findFamily = familyRepository.findByFamilyId(findMember.getFamilyId());

                MemberDetails member = MemberDetails.builder()
                        .id(findMember.getId())
                        .name(findMember.getName() == null ? "" : findMember.getName())
                        .category(findFamily.getCategory())
                        .religion(findFamily.getCategory())
                        .houseNo(findFamily.getHouseNo())
                        .gender(findMember.getGender())
                        .vaccination(getVaccinationList(dates, id, vs))
                        .delivery(getDeliveryList(dates, hs))
                        .houseVisits(getHouseVisitList(dates, id, hs))
                        .build();
                addInList.add(member);
            }

        }

        return addInList;
    }

    @Override
    public List<VaccinationScheduleDTO> getVaccinationSchedule(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        List<VaccinationScheduleDTO> addInList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Set<Long> uniqueDate = new TreeSet<>();


        long startTime = 0L, endTime = 0L;

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


        List<VaccinationSchedule> vaccinationScheduleList = vaccinationScheduleRepository.findAllByDateRange(startTime, endTime, dashboardFilter.getCenterId());
        List<HouseVisitSchedule> houseVisitScheduleList = houseVisitScheduleRepository.findAllByDateRange(startTime, endTime, dashboardFilter.getCenterId());

        for (VaccinationSchedule vs : vaccinationScheduleList) {
            uniqueDate.add(vs.getDueDate());
        }

        for (HouseVisitSchedule hs : houseVisitScheduleList) {
            uniqueDate.add(hs.getDueDate());
        }


        for (long sd : uniqueDate) {
            VaccinationScheduleDTO addSingle = VaccinationScheduleDTO.builder()
                    .date(df.format(sd))
                    .members(getMembersDetails(sd, vaccinationScheduleList, houseVisitScheduleList))
                    .build();

            addInList.add(addSingle);
        }
        return addInList;
    }


    @Override
    public List<Visits> updateMissingFields() {
        List<Visits> checkMissing = visitsRepository.findAll();

        for (Visits validate : checkMissing) {
            if (familyMemberRepository.findById(validate.getMemberId()).isPresent()) {
                FamilyMember member = familyMemberRepository.findById(validate.getMemberId()).get();
                if (validate.getFamilyId().length() <= 0) {
                    validate.setFamilyId(member.getFamilyId());
                }

                if (validate.getCategory().length() <= 0) {
                    validate.setCategory(member.getCategory());
                }

                visitsRepository.save(validate);
            }

        }

        return checkMissing;
    }

//    @Override
//    public List<DhartiData> getDhartiData(DashboardFilter dashboardFilter) throws ParseException {
//
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        List<DhartiData> addInList = new ArrayList<>();
//        Date startTime = null, endTime = null;
//
//        if (dashboardFilter.getStartDate().trim().length() > 0) {
//            startTime = df.parse(dashboardFilter.getStartDate().trim());
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
//        List<PregnantAndDelivery> findPdd = pregnantAndDeliveryRepository.findAllDhartiCriteria(startTime, endTime);
//        for (PregnantAndDelivery cat : findPdd) {
//            DhartiData singleEntry = DhartiData.builder()
//                    .dateOfDelivery(df.format(new Date(cat.getDateOfDelivery())))
//                    .motherId(cat.getMotherMemberId())
//                    .category(cat.getCategory())
//                    .startDate(df.format(startTime))
//                    .endDate(df.format(endTime))
//                    .build();
//            addInList.add(singleEntry);
//        }
//
//
//        return addInList;
//    }

    @Override
    public List<DhartiData> getDhartiWomenDetails(DashboardFilter dashboardFilter) throws ParseException {

        List<DhartiData> addInList = new ArrayList<>();
        HashSet<String> uniqueMemberId = new HashSet<>();
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

        List<PregnantAndDelivery> findPregnancyData = pregnantAndDeliveryRepository.findAllDhartiCriteria(startTime, endTime,dashboardFilter.getCenterId().trim());

        try {
            for (PregnantAndDelivery visits : findPregnancyData) {

                if (uniqueMemberId.add(visits.getMotherMemberId())) {
                    List<FamilyMember> searchNames = familyMemberRepository.findAllByIdAndNameSearch(visits.getMotherMemberId(), dashboardFilter.getSearch().trim());

                    if (searchNames.size() > 0) {
                        for (FamilyMember searchResults : searchNames) {
                            Family households = familyRepository.findByFamilyId(searchResults.getFamilyId());

                            DhartiData addSingle = DhartiData.builder()
                                    .name(searchResults.getName())
                                    .centerId(searchResults.getCenterId())
                                    .husbandName(searchResults.getFatherName())
                                    .dob(df.format(searchResults.getDob()))
                                    .motherId(visits.getMotherMemberId())
                                    .lastMissedPeriodDate(df.format(visits.getLastMissedPeriodDate()))
                                    .category(searchResults.getCategory())
                                    .minority(households.getIsMinority())
                                    .religion(households.getReligion())
                                    .dateOfDelivery(df.format(visits.getDateOfDelivery()))
                                    .startDate(dashboardFilter.getStartDate())
                                    .endDate(dashboardFilter.getEndDate())
                                    .duration("")
                                    .build();

                            addInList.add(addSingle);
                        }
                    }

                }
            }

        } catch (NullPointerException ignored) {

        }
        return addInList;
    }

    @Override
    public List<WeightRecordsDTO> getWeightRecords(String childId) {

        if (StringUtils.isEmpty(childId.trim())) {
            throw new CustomException("Child Id Is Missed, Please Check!!");
        }

        List<WeightTracking> findChildRecords = weightTrackingRepository.findAllByChildId(childId, Sort.by(Sort.Direction.ASC, "createdDate"));
        FamilyMember findChildDetails = familyMemberRepository.findById(childId).get();
        List<WeightRecordsDTO> addList = new ArrayList<>();

        for (WeightTracking passRecords : findChildRecords) {

            long millis = passRecords.getDate();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(millis);

            WeightRecordsDTO getRecords = WeightRecordsDTO.builder()
                    .familyId(passRecords.getFamilyId() == null ? "" : passRecords.getFamilyId())
                    .childId(passRecords.getChildId() == null ? "" : passRecords.getChildId())
                    .name(findChildDetails.getName())
                    .gender(findChildDetails.getGender())
                    .houseNo("")
                    .motherName(findChildDetails.getMotherName())
                    .dob(df.format(findChildDetails.getDob()))
                    .photo(findChildDetails.getPhoto())
                    .date(df.format(date))
                    .centerName(passRecords.getCenterName())
                    .weight(passRecords.getWeight() == null ? "" : passRecords.getWeight().replaceAll(" ", ""))
                    .height(passRecords.getHeight() == null ? "" : passRecords.getHeight().replaceAll(" ", ""))
                    .build();

            addList.add(getRecords);
        }
        return addList;
    }

    @Override
    public List<WeightRecordsDTO> getAllChildWeightRecords(String centerId) {

        if (StringUtils.isEmpty(centerId.trim())) {
            throw new CustomException("center Name Is Missed, Please Check!!");
        }

        LocalDateTime minus6Years = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(minus6Years, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();


        HashSet<String> uniqueChildId = new HashSet<>();
        List<WeightRecordsDTO> addInList = new ArrayList<>();
        List<FamilyMember> findAllChildRecords = familyMemberRepository.findAllByCenterIdAndDob(centerId, convertToMills, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (FamilyMember tracking : findAllChildRecords) {

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            List<WeightTracking> findChildDetails = weightTrackingRepository.findAllByChildId(tracking.getId(), Sort.by(Sort.Direction.DESC, "createdDate"));
            Date date = new Date();
            String weight = "", height = "";

            for (WeightTracking wt : findChildDetails) {
                date = new Date(wt.getDate());

                if (wt.getWeight().length() > 0) {
                    weight = wt.getWeight().trim();
                    break;
                }
            }

            for (WeightTracking wt : findChildDetails) {
                date = new Date(wt.getDate());

                if (wt.getHeight().length() > 0) {
                    height = wt.getHeight().trim();
                    break;
                }
            }


            Family findFamilyDetails = familyRepository.findByFamilyId(tracking.getFamilyId());

            if (uniqueChildId.add(tracking.getId())) {
                WeightRecordsDTO singleEntry = WeightRecordsDTO.builder()
                        .familyId(tracking.getFamilyId() == null ? "" : tracking.getFamilyId())
                        .childId(tracking.getId() == null ? "" : tracking.getId())
                        .name(tracking.getName())
                        .houseNo(findFamilyDetails.getHouseNo() == null ? "" : findFamilyDetails.getHouseNo())
                        .gender(tracking.getGender())
                        .motherName(tracking.getMotherName())
                        .dob(df.format(tracking.getDob()))
                        .photo(tracking.getPhoto())
                        .date(df.format(date))
                        .centerName(tracking.getCenterName())
                        .weight(weight.replaceAll(" ", ""))
                        .height(height.replaceAll(" ", ""))
                        .build();

                addInList.add(singleEntry);
            }
        }

        return addInList;
    }

    private long MPRDurationEndDate(String duration) throws ParseException {
        long endMillis = 0L;
        switch (duration) {

            case "2":
                LocalDateTime minus7Months = LocalDateTime.now().minusMonths(7);
                String temp7Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus7Months);
                DateFormat df7 = new SimpleDateFormat("dd-MM-yyyy");

                Date last7Months = df7.parse(temp7Date);
                endMillis = last7Months.getTime();
                break;

            case "3":
                LocalDateTime minus12Months = LocalDateTime.now().minusMonths(12);
                String temp12Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus12Months);
                DateFormat df12 = new SimpleDateFormat("dd-MM-yyyy");

                Date last12Months = df12.parse(temp12Date);
                endMillis = last12Months.getTime();
                break;

            case "4":
                LocalDateTime minus24Months = LocalDateTime.now().minusMonths(24);
                String temp24Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus24Months);
                DateFormat df24 = new SimpleDateFormat("dd-MM-yyyy");

                Date last24Months = df24.parse(temp24Date);
                endMillis = last24Months.getTime();
                break;

            case "5":
                LocalDateTime minus36Months = LocalDateTime.now().minusMonths(36);
                String temp36Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus36Months);
                DateFormat df36 = new SimpleDateFormat("dd-MM-yyyy");

                Date last36Months = df36.parse(temp36Date);
                endMillis = last36Months.getTime();
                break;

            case "6":
                LocalDateTime minus48Months = LocalDateTime.now().minusMonths(48);
                String temp48Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus48Months);
                DateFormat df48 = new SimpleDateFormat("dd-MM-yyyy");

                Date last48Months = df48.parse(temp48Date);
                endMillis = last48Months.getTime();
                break;

            default:
                endMillis = new Date().getTime();
                break;
        }


        return endMillis;

    }

    private long MPRDurationStartDate(String duration) throws ParseException {
        long startMillis = 0L;
        switch (duration) {

            case "1":
                LocalDateTime minus6Months = LocalDateTime.now().minusMonths(6);
                String temp6Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus6Months);
                DateFormat df6 = new SimpleDateFormat("dd-MM-yyyy");

                Date last6Months = df6.parse(temp6Date);
                startMillis = last6Months.getTime();
                break;

            case "2":
                LocalDateTime minus12Months = LocalDateTime.now().minusMonths(12);
                String temp12Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus12Months);
                DateFormat df12 = new SimpleDateFormat("dd-MM-yyyy");

                Date last12Months = df12.parse(temp12Date);
                startMillis = last12Months.getTime();
                break;

            case "3":
                LocalDateTime minus24Months = LocalDateTime.now().minusMonths(24);
                String temp24Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus24Months);
                DateFormat df24 = new SimpleDateFormat("dd-MM-yyyy");

                Date last24Months = df24.parse(temp24Date);
                startMillis = last24Months.getTime();
                break;

            case "4":
                LocalDateTime minus36Months = LocalDateTime.now().minusMonths(36);
                String temp36Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus36Months);
                DateFormat df36 = new SimpleDateFormat("dd-MM-yyyy");

                Date last36Months = df36.parse(temp36Date);
                startMillis = last36Months.getTime();
                break;

            case "5":
                LocalDateTime minus48Months = LocalDateTime.now().minusMonths(48);
                String temp48Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus48Months);
                DateFormat df48 = new SimpleDateFormat("dd-MM-yyyy");

                Date last48Months = df48.parse(temp48Date);
                startMillis = last48Months.getTime();
                break;

            case "6":
                LocalDateTime minus60Months = LocalDateTime.now().minusMonths(60);
                String temp60Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus60Months);
                DateFormat df60 = new SimpleDateFormat("dd-MM-yyyy");

                Date last60Months = df60.parse(temp60Date);
                startMillis = last60Months.getTime();
                break;


            default:
                startMillis = -5364683608000L;
                break;
        }


        return startMillis;

    }


    private String MPRMonthStartDate(String startMonth) throws ParseException {
        String inputMonth = startMonth == null ? "" : startMonth;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (inputMonth.length() <= 0) {
            inputMonth = commonMethodsService.startDateOfMonth();
        }

        return inputMonth;
    }

    private String MPRMonthEndDate(String endMonth) throws ParseException {
        String inputMonth = endMonth == null ? "" : endMonth;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (inputMonth.length() <= 0) {
            inputMonth = commonMethodsService.endDateOfMonth();
        }

        return inputMonth;
    }


    @Override
    public MPRDTO getMPRRecords(String month, String duration, String category, String centerName) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String startMonth = month == null ? "" : month;
        String endMonth = month == null ? "" : month;
        duration = duration == null ? "" : duration;
        category = category == null ? "" : category;
        long male = 0, female = 0, dharti = 0, pregnant = 0, birth = 0, mortality = 0;
        MPRDTO MprCounts = new MPRDTO();

        startMonth = MPRMonthStartDate(startMonth);
        endMonth = MPRMonthEndDate(endMonth);

        List<FamilyMember> findByCenter = familyMemberRepository.findAllByCenterNameAndRecordForMonthAndCategory(centerName, df.parse(startMonth), df.parse(endMonth), category);
        long startDate = 0, endDate = 0;

        for (FamilyMember formatDetails : findByCenter) {


            startDate = MPRDurationStartDate(duration);
            endDate = MPRDurationEndDate(duration);
            long deathInMillis = 0L;


            if (formatDetails.getDateOfMortality().trim().length() > 0) {

                Date deathDate = df.parse(formatDetails.getDateOfMortality());
                deathInMillis = deathDate.getTime();
            }


            if (formatDetails.getDob() >= startDate && formatDetails.getDob() < endDate && formatDetails.getGender().trim().equals("1")) {
                male++;
//                log.error("Male Name " + formatDetails.getName());
            }

            if (formatDetails.getDob() >= startDate && formatDetails.getDob() < endDate && formatDetails.getGender().trim().equals("2")) {
                female++;
//                log.error("Female Name " + formatDetails.getName());
            }

            if (formatDetails.getDob() >= startDate && formatDetails.getDob() < endDate) {
                birth++;
//                log.error("Birth Name " + formatDetails.getName());
            }

            if (formatDetails.getDateOfMortality().trim().length() > 0 && (deathInMillis >= startDate && deathInMillis < endDate)) {
                mortality++;
//                log.error("Birth Name " + formatDetails.getName());
            }
        }


        List<PregnantAndDelivery> findDhatri = pregnantAndDeliveryRepository.findAllByCenterNameAndCategoryAndCreatedDate(centerName, category, df.parse(startMonth), df.parse(endMonth));
        HashSet<String> uniqueDhartiMemberId = new HashSet<>();
        HashSet<String> uniquePregnantMemberId = new HashSet<>();
        String visitCat = "";


        // Find Dharti
        for (PregnantAndDelivery checkDetails : findDhatri) {

            LocalDateTime date = LocalDateTime.now().minusMonths(6);
            ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
            long convertToMills = zdt.toInstant().toEpochMilli();
            log.info("dharti " + convertToMills);
            if (checkDetails.getDateOfDelivery() >= convertToMills) {
                if (uniqueDhartiMemberId.add(checkDetails.getMotherMemberId())) {
                    dharti++;
                }

            }

        }

        // Find Pregnant

        for (PregnantAndDelivery preg : findDhatri) {

            if (preg.getDateOfDelivery() <= 0) {
                if (uniquePregnantMemberId.add(preg.getMotherMemberId())) {
//                        log.error("MemberId " + preg.getMemberId());
                    pregnant++;
                }

            }
        }


        MprCounts = MPRDTO.builder()
                .male(male)
                .female(female)
                .birth(birth)
                .mortality(mortality)
                .dharti(dharti)
                .pregnant(pregnant)
                .build();

//        List<FamilyMember> chekByCat = familyMemberRepository.findAllByMPRPeriod(month, duration, category, centerName);


        return MprCounts;
    }

    @Override
    public FamilyMemberCounts getMembersByFamilyId(String familyId) {

        List<FamilyMember> checkMembers = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.ASC, "createdDate"));
        int male = 0, female = 0, children = 0;

        if (checkMembers.size() > 0) {
            for (FamilyMember countByGender : checkMembers) {
                if (countByGender.getGender().equalsIgnoreCase("1")) {
                    male++;
                }
                if (countByGender.getGender().equalsIgnoreCase("2")) {
                    female++;
                }
            }
        }

        return FamilyMemberCounts.builder()
                .male(male)
                .female(female)
                .children(children)
                .build();
    }

    @Override
    public List<GetVaccinationDTO> getVaccinationRecords(String vaccineCode, String centerId) {

        List<GetVaccinationDTO> addList = new ArrayList<>();
        List<Visits> vaccinationList = new ArrayList<>();
        HashSet<String> uniqueFamilyId = new HashSet<>();
        String code = vaccineCode == null ? "" : vaccineCode;

        if (code.trim().length() > 0) {
            vaccinationList = visitsRepository.findAllByVisitForSearchCriteria(vaccineCode, centerId, Sort.by(Sort.Direction.DESC, "createdDate"));
        } else {
            vaccinationList = visitsRepository.findAllByVisitForAndCenterId(centerId, Sort.by(Sort.Direction.DESC, "createdDate"));
        }

        for (Visits vaccDetails : vaccinationList) {
            FamilyMember fmd = familyMemberRepository.findById(vaccDetails.getMemberId()).get();
            Family findHouse = familyRepository.findByFamilyId(fmd.getFamilyId());
            long getMills = fmd.getDob();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);

            if (uniqueFamilyId.add(fmd.getId())) {
                GetVaccinationDTO addSingle = GetVaccinationDTO.builder()
                        .name(fmd.getName())
                        .gender(fmd.getGender())
                        .childId(vaccDetails.getMemberId())
                        .centerId(centerId)
                        .centerName(commonMethodsService.findCenterName(centerId))
                        .houseNo(findHouse.getHouseNo())
                        .vaccinationCode(vaccDetails.getVisitFor())
                        .vaccinationName("")
                        .age(df.format(date))
                        .photo(fmd.getPhoto())
                        .motherName(fmd.getMotherName())
                        .build();
                addList.add(addSingle);
            }
            }



        return addList;

    }

    @Override
    public List<HouseholdsChildren> getAllHouseholdsChildren(String centerId) throws ParseException {

        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date parseTime = df.parse(convertToString);

        List<HouseholdsChildren> addList = new ArrayList<>();


        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Time "+convertToMills);

        List<FamilyMember> findAllChildren = familyMemberRepository.findAllByDobAndCenterId(convertToMills, centerId);
        String gender = "";

        if (findAllChildren.size() > 0) {
            String minority = "", category = "";
            for (FamilyMember member : findAllChildren) {

                List<AnganwadiChildren> findNonRegistered = anganwadiChildrenRepository.findAllByChildIdAndRegisteredTrueAndDeletedFalse(member.getId());

            if(findNonRegistered.size()<=0) {
                Date getMillis = new Date(member.getDob());
                List<Family> findFamilyDetails = familyRepository.findAllByFamilyId(member.getFamilyId());
                for (Family findDetails : findFamilyDetails) {
                    minority = findDetails.getIsMinority();
                    category = findDetails.getCategory();
                }

                HouseholdsChildren singeEntry = HouseholdsChildren.builder()
                        .childName(member.getName())
                        .motherName("")
                        .fatherName("")
                        .familyId(member.getFamilyId())
                        .childId(member.getId())
                        .dob(df.format(getMillis))
                        .motherName(member.getMotherName())
                        .fatherName(member.getFatherName())
                        .memberCode(member.getMemberCode())
                        .gender(member.getGender())
                        .mobileNumber(member.getMobileNumber())
                        .category(category)
                        .minority(minority)
                        .handicap(member.getHandicapType())
                        .photoUrl(member.getPhoto())
                        .build();

                addList.add(singeEntry);
                }
            }

        }
        return addList;
    }

    private String getPrefix(String caseId) {

        String visitPrefix = "";
        long noOfRounds = 0;
        switch (caseId) {
            case "1":
                visitPrefix = "A";
                noOfRounds = visitsRepository.countByVisitType(caseId);
                break;

            case "2":
                visitPrefix = "B";
                noOfRounds = visitsRepository.countByVisitType(caseId);
                break;

            case "3":
                visitPrefix = "C";

                break;

            case "4":
                visitPrefix = "D";

                break;

            case "5":
                visitPrefix = "E";

                break;

            case "6":
                visitPrefix = "F";

                break;

            case "7":
                visitPrefix = "G";

                break;

            case "8":
                visitPrefix = "H";

                break;

            case "9":
                visitPrefix = "I";

                break;

            case "10":
                visitPrefix = "J";

                break;

        }

        visitPrefix = visitPrefix + noOfRounds;
        return visitPrefix;
    }

    private String visitRounds(String memberId) {


        StringBuilder appendRounds = new StringBuilder();


        List<Visits> checkRounds = visitsRepository.findAllByMemberId(memberId);
        HashSet<String> visitType = new HashSet<>();
        String data = "";
        for (Visits verifyVisit : checkRounds) {
            if (visitType.add(verifyVisit.getVisitType())) {
                data = verifyVisit.getVisitType();
                appendRounds.append(getPrefix(data)).append(appendRounds.append(","));
            }
        }


        return appendRounds.toString();
    }


    @Override
    public List<FemaleMembersDTO> getHouseholdsFemaleDetails(String centerId) {

        List<FamilyMember> findFemales = familyMemberRepository.findAllByGenderAndCenterId(centerId);
        List<FemaleMembersDTO> addList = new ArrayList<>();

        for (FamilyMember checkAge : findFemales) {
            int getYear = 0;
            String husbandName = "", houseNo = "", familyId = "";

            long millis = checkAge.getDob();

            Date date = new Date(millis);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            String dateOfDob = df.format(date);
            String[] dateParts = dateOfDob.split("-");
            getYear = Integer.parseInt(dateParts[2]);
            int currentYear = Year.now().getValue();
            int getDiff = currentYear - getYear;

            if (getDiff >= 20 && getDiff <= 60) {

                // center Name
                List<Family> checkHouseDetails = familyRepository.findAllByFamilyId(checkAge.getFamilyId());

                for (Family getDetails : checkHouseDetails) {
                    houseNo = getDetails.getHouseNo();
                    familyId = getDetails.getFamilyId();
                }

                husbandName = checkAge.getFatherName();

                FemaleMembersDTO addMember = FemaleMembersDTO.builder()
                        .name(checkAge.getName() == null ? "" : checkAge.getName())
                        .centerName(commonMethodsService.findCenterName(centerId))
                        .husbandName(husbandName)
                        .familyId(familyId)
                        .houseNo(houseNo)
                        .memberId(checkAge.getId())
                        .profilePic(checkAge.getPhoto() == null ? "" : checkAge.getPhoto())
                        .dob(df.format(checkAge.getDob()))
                        .build();

                addList.add(addMember);
            }
        }

        return addList;
    }


    @Override
    public List<HouseVisitDTO> getHouseVisitListing(String centerId) {

        List<Visits> checkRounds = visitsRepository.findAllByCenterId(centerId);
        List<HouseVisitDTO> addInList = new ArrayList<>();
        HashSet<String> memberId = new HashSet<>();

        for (Visits visitsDetails : checkRounds) {
            log.error("id "+visitsDetails.getMemberId());
            FamilyMember checkMembers = familyMemberRepository.findById(visitsDetails.getMemberId()).get();

            String husband_FatherName = "", houseNo = "";

            long millis = checkMembers.getDob();
            Date date = new Date(millis);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            husband_FatherName = checkMembers.getFatherName();

            List<Family> checkHouseDetails = familyRepository.findAllByFamilyId(checkMembers.getFamilyId());

            for (Family findFamily : checkHouseDetails) {
                houseNo = findFamily.getHouseNo();
            }

            if (memberId.add(visitsDetails.getMemberId())) {
                HouseVisitDTO addSingle = HouseVisitDTO.builder()
                        .name(checkMembers.getName() == null ? "" : checkMembers.getName())
                        .dob(df.format(date))
                        .husbandName(husband_FatherName == null ? "" : husband_FatherName)
                        .houseNo(houseNo)
                        .memberId(checkMembers.getId())
                        .centerName(commonMethodsService.findCenterName(centerId))
                        .profilePic(checkMembers.getPhoto() == null ? "" : checkMembers.getPhoto())
                        .visits(visitRounds(visitsDetails.getMemberId()))
                        .build();

                addInList.add(addSingle);
            }

        }


        return addInList;
    }

    private List<VisitArray> visitArrayList(String memberId, String visitType) {

        List<Visits> checkVisitsFor = visitsRepository.findAllByMemberIdAndVisitType(memberId, visitType);
        List<VisitArray> addInList = new ArrayList<>();

        for (Visits findDetails : checkVisitsFor) {

            long millis = findDetails.getVisitDateTime();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
            Date date = new Date(millis);

            VisitArray visitArray = VisitArray.builder()
                    .date(df.format(date))
                    .visitFor(findDetails.getVisitFor())
                    .visitRound(findDetails.getVisitRound())
                    .build();

            addInList.add(visitArray);
        }

        return addInList;
    }

    @Override
    public List<MemberVisits> getMemberVisitDetailsLatest(String memberId, String centerId, String centerName) {


        List<MemberVisits> addInList = new ArrayList<>();
        MemberVisits memberVisits = new MemberVisits();
        HashSet<String> uniqueMember = new HashSet<>();
        int count = 0;

        for (int i = 1; i <= 10; i++) {
            List<Visits> findMember = visitsRepository.findAllByMemberIdAndCenterIdAndVisitType(memberId, centerId, String.valueOf(i));
            if (findMember.size() > 0) {
                for (Visits checkDetails : findMember) {

                    memberVisits = MemberVisits.builder()
                            .visitType(checkDetails.getVisitType())
                            .visitArray(visitArrayList(checkDetails.getMemberId(), checkDetails.getVisitType()))
                            .build();

                }

            } else {
                memberVisits = MemberVisits.builder()
                        .visitType(String.valueOf(i))
                        .visitArray(Collections.emptyList())
                        .build();
            }
            addInList.add(memberVisits);
        }


        return addInList;
    }

    private void updateDeliveryHouseVisitSchedule(BabiesBirth babiesBirth, Visits visits) {

        // First Update Delivery Row
        List<HouseVisitSchedule> findDeliveryDetails = houseVisitScheduleRepository.findAllByDeliveryTypeAndVisitRound("DELIVERY", babiesBirth.getMotherMemberId(),visits.getVisitRound());
        long dueDate = 0;
        for (HouseVisitSchedule hs : findDeliveryDetails) {
            dueDate = hs.getDueDate();
        }

        if (findDeliveryDetails.size() > 0) {
            for (HouseVisitSchedule hs : findDeliveryDetails) {
                hs.setVisitDate(visits.getVisitDateTime());
                hs.setComments(visits.getDescription());
                hs.setLatitude(visits.getLatitude());
                hs.setLongitude(visits.getLongitude());
                hs.setMemberId(babiesBirth.getChildId());
                houseVisitScheduleRepository.save(hs);
            }

        } else {
            houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                    .memberId(babiesBirth.getChildId())
                    .visitRound(visits.getVisitRound())
                    .visitDate(visits.getVisitDateTime())
                    .centerId(visits.getCenterId())
                    .centerName(visits.getCenterName())
                    .comments(visits.getDescription())
                    .latitude(visits.getLatitude())
                    .longitude(visits.getLongitude())
                    .visitType("DELIVERY")
                    .visitName("DAY_OF_DELIVERY")
                    .dueDate(dueDate)
                    .build());
        }


        // Second Make Entry for House Visit For 2 Years

        // Within 1-7 Days of Birth


        if (babiesBirth.getDob() > 0) {
            int round=1,period = 0;

            LocalDate localDate = Instant.ofEpochMilli(babiesBirth.getDob()).atZone(ZoneId.systemDefault()).toLocalDate();


            houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                    .memberId(babiesBirth.getChildId())
                    .visitDate(0)
                    .visitRound("1")
                    .comments("")
                    .latitude("")
                    .longitude("")
                    .centerId(visits.getCenterId())
                    .centerName(visits.getCenterName())
                    .visitType("BIRTH_VISIT_1")
                    .visitName("TILL_1_7_DAYS")
                    .dueDate(localDate.plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                    .memberId(babiesBirth.getChildId())
                    .visitDate(0)
                    .visitRound("2")
                    .comments("")
                    .latitude("")
                    .longitude("")
                    .centerId(visits.getCenterId())
                    .centerName(visits.getCenterName())
                    .visitType("BIRTH_VISIT_1")
                    .visitName("TILL_1_7_DAYS")
                    .dueDate(localDate.plusDays(7).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            while (round < 3) {
                houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                        .memberId(babiesBirth.getChildId())
                        .visitDate(0)
                        .visitRound(String.valueOf(round))
                        .comments("")
                        .latitude("")
                        .longitude("")
                        .centerId(visits.getCenterId())
                        .centerName(visits.getCenterName())
                        .visitType("BIRTH_VISIT_2")
                        .visitName("TILL_8_30_DAYS")
                        .dueDate(localDate.plusDays(10 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build());

                houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                        .memberId(babiesBirth.getChildId())
                        .visitDate(0)
                        .visitRound(String.valueOf(round))
                        .comments("")
                        .latitude("")
                        .longitude("")
                        .centerId(visits.getCenterId())
                        .centerName(visits.getCenterName())
                        .visitType("BIRTH_VISIT_3")
                        .visitName("TILL_1_5_MONTHS")
                        .dueDate(localDate.plusDays(130 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build());

                houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                        .memberId(babiesBirth.getChildId())
                        .visitDate(0)
                        .visitRound(String.valueOf(round))
                        .comments("")
                        .latitude("")
                        .longitude("")
                        .centerId(visits.getCenterId())
                        .centerName(visits.getCenterName())
                        .visitType("BIRTH_VISIT_4")
                        .visitName("TILL_6_8_MONTHS")
                        .dueDate(localDate.plusDays(220 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build());

                houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                        .memberId(babiesBirth.getChildId())
                        .visitDate(0)
                        .visitRound(String.valueOf(round))
                        .comments("")
                        .latitude("")
                        .longitude("")
                        .centerId(visits.getCenterId())
                        .centerName(visits.getCenterName())
                        .visitType("BIRTH_VISIT_5")
                        .visitName("TILL_9_11_MONTHS")
                        .dueDate(localDate.plusDays(310 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build());

                houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                        .memberId(babiesBirth.getChildId())
                        .visitDate(0)
                        .visitRound(String.valueOf(round))
                        .comments("")
                        .latitude("")
                        .longitude("")
                        .centerId(visits.getCenterId())
                        .centerName(visits.getCenterName())
                        .visitType("BIRTH_VISIT_6")
                        .visitName("TILL_12_17_MONTHS")
                        .dueDate(localDate.plusDays(490 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build());

                houseVisitScheduleRepository.save(HouseVisitSchedule.builder()
                        .memberId(babiesBirth.getChildId())
                        .visitDate(0)
                        .visitRound(String.valueOf(round))
                        .comments("")
                        .latitude("")
                        .longitude("")
                        .centerId(visits.getCenterId())
                        .centerName(visits.getCenterName())
                        .visitType("BIRTH_VISIT_7")
                        .visitName("TILL_18_24_DAYS")
                        .dueDate(localDate.plusDays(700 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .build());
                round++;
                period += 20;
            }
        }


    }


    private void updateVaccinationVisitSchedule(BabiesBirth babiesBirth) {

        if (babiesBirth.getDob() > 0) {
            LocalDate localDate = Instant.ofEpochMilli(babiesBirth.getDob()).atZone(ZoneId.systemDefault()).toLocalDate();

            // On Day Of Birth

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("1")
                    .vaccinationName("Polio OPB")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(babiesBirth.getDob())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("22")
                    .vaccinationName("Hip B0")
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .vaccinationDate(0)
                    .dueDate(babiesBirth.getDob())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("8")
                    .vaccinationName("BCG")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(babiesBirth.getDob())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("9")
                    .vaccinationName("DPT 1")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(babiesBirth.getDob())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("5")
                    .vaccinationName("Hip B1")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(babiesBirth.getDob())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("2")
                    .vaccinationName("Polio 1")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(babiesBirth.getDob())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());


            // After 5 Months Of Birth


            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("10")
                    .vaccinationName("DPT 2")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(localDate.plusDays(150).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("6")
                    .vaccinationName("Hip B2")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(localDate.plusDays(150).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("3")
                    .vaccinationName("Polio 2")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(localDate.plusDays(150).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());


            // After 10 Months Of Birth

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("11")
                    .vaccinationName("DPT 3")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(localDate.plusDays(300).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("7")
                    .vaccinationName("Hip B3")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(localDate.plusDays(300).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                    .code("4")
                    .vaccinationName("Polio 3")
                    .vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(babiesBirth.getCenterName())
                    .dueDate(localDate.plusDays(300).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId())
                    .build());
        }

    }

    @Override
    public List<BirthPlaceDTO> saveBirthDetails(BirthPlaceDTO birthDetails, String centerId, String centerName) throws ParseException {
        List<BirthPlaceDTO> addInList = new ArrayList<>();
        String headCategory = "";

        // Find Family Details

        FamilyMember searchFamilyId = familyMemberRepository.findById(birthDetails.getMotherMemberId()).get();
        List<Family> findHod = familyRepository.findAllByFamilyId(searchFamilyId.getFamilyId());
        for (Family getFamilyId : findHod) {
            headCategory = getFamilyId.getCategory();
        }
        List<FamilyMember> findHead = familyMemberRepository.findAllByFamilyId(searchFamilyId.getFamilyId(), Sort.by(Sort.Direction.ASC, "createdDate"));
        String headName = "";
        for (FamilyMember headDetails : findHead) {
            if (headDetails.getRelationWithOwner().equalsIgnoreCase("0")) {
                headName = headDetails.getName();
                break;
            } else {
                headName = headDetails.getName();
            }
        }

        // Date to Millis

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse(birthDetails.getDob());
        long mills = date.getTime();

        Date currentMonth = new Date();
        String[] splitMonth = df.format(currentMonth).split("-");
        String getMonth = splitMonth[1].replace("0", "");


        // Save in Family Member Table

        FamilyMember addMember = FamilyMember.builder()
                .familyId(searchFamilyId.getFamilyId())
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .category(searchFamilyId.getCategory().length() <= 0 ? headCategory : searchFamilyId.getCategory())
                .centerId(centerId)
                .motherName(searchFamilyId.getName())
                .fatherName(headName)
                .maritalStatus("2")
                .dateOfMortality("")
                .dateOfLeaving("")
                .dateOfArrival("")
                .handicapType("")
                .photo("")
                .memberCode("")
                .residentArea("")
                .recordForMonth(getMonth)
                .mobileNumber(searchFamilyId.getMobileNumber())
                .stateCode(searchFamilyId.getStateCode())
                .centerName(commonMethodsService.findCenterName(centerId))
                .relationWithOwner(birthDetails.getRelationWithOwner() == null ? "" : birthDetails.getRelationWithOwner())
                .gender(birthDetails.getGender())
                .dob(mills)
                .build();
        familyMemberRepository.save(addMember);

        // Save in Birth Table

        BabiesBirth saveDetails = BabiesBirth.builder()
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .childId(addMember.getId())
                .birthPlace(birthDetails.getBirthPlace() == null ? "" : birthDetails.getBirthPlace())
                .birthType(birthDetails.getBirthType() == null ? "" : birthDetails.getBirthType())
                .familyId(searchFamilyId.getFamilyId())
                .dob(mills)
                .srNo(birthDetails.getSrNo() == null ? "" : birthDetails.getSrNo())
                .motherMemberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .gender(birthDetails.getGender() == null ? "" : birthDetails.getGender())
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight())
                .firstWeight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .build();
        babiesBirthRepository.save(saveDetails);

        // Save in Weight Table

        WeightTracking saveRecord = WeightTracking.builder()
                .familyId(searchFamilyId.getFamilyId() == null ? "" : searchFamilyId.getFamilyId())
                .childId(saveDetails.getChildId() == null ? "" : saveDetails.getChildId())
                .date(mills)
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .weight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight())
                .build();

        weightTrackingRepository.save(saveRecord);

        // Save in Visit
        Date visitDate = new Date();

        DateFormat visitDf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String convertToString = visitDf.format(new Date());
        Date visitTime = visitDf.parse(convertToString);
        long timestamp = visitTime.getTime();


        Visits updateRecord = Visits.builder()
                .visitFor(birthDetails.getVisitFor())
                .familyId(searchFamilyId.getFamilyId())
                .visitType(birthDetails.getVisitType())
                .visitRound(birthDetails.getVisitRound())
                .memberId(addMember.getId() == null ? "" : addMember.getId())
                .centerName(commonMethodsService.findCenterName(centerId))
                .centerId(centerId)
                .childDob(mills)
                .category(searchFamilyId.getCategory().length() <= 0 ? headCategory : searchFamilyId.getCategory())
                .description("")
                .longitude(birthDetails.getLongitude() == null ? "" : birthDetails.getLongitude())
                .latitude(birthDetails.getLatitude() == null ? "" : birthDetails.getLatitude())
                .visitDateTime(timestamp)
                .build();

        visitsRepository.save(updateRecord);


        // Update in PregnantAndDelivery Table

        PregnantAndDelivery pdd = pregnantAndDeliveryRepository.findTopOneByMotherMemberId(birthDetails.getMotherMemberId());

        if (pdd != null) {
            pdd.setChildName(birthDetails.getName());
            pdd.setChildGender(birthDetails.getGender());
            pdd.setDateOfDelivery(mills);
            pregnantAndDeliveryRepository.save(pdd);
        }


        // Update House visit & Vaccination Schedule Details
        updateDeliveryHouseVisitSchedule(saveDetails, updateRecord);
        updateVaccinationVisitSchedule(saveDetails);

        BirthPlaceDTO singleEntry = BirthPlaceDTO.builder()
                .id(addMember.getId())
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .relationWithOwner(birthDetails.getRelationWithOwner() == null ? "" : birthDetails.getRelationWithOwner())
                .dob(birthDetails.getDob())
                .birthPlace(birthDetails.getBirthPlace() == null ? "" : birthDetails.getBirthPlace())
                .birthType(birthDetails.getBirthType() == null ? "" : birthDetails.getBirthType())
                .familyId(searchFamilyId.getFamilyId() == null ? "" : searchFamilyId.getFamilyId())
                .motherMemberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .gender(birthDetails.getGender() == null ? "" : birthDetails.getGender())
                .centerId(centerId)
                .srNo(birthDetails.getSrNo()== null ? "" : birthDetails.getSrNo())
                .centerName(commonMethodsService.findCenterName(centerId))
                .visitFor(birthDetails.getVisitFor())
                .visitType(birthDetails.getVisitType())
                .firstWeight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight())
                .build();
        addInList.add(singleEntry);

        return addInList;
    }

    @Override
    public SaveVaccinationDTO saveVaccinationDetails(SaveVaccinationDTO saveVaccinationDTO, String centerId, String centerName) {

        String familyId = saveVaccinationDTO.getFamilyId() == null ? "" : saveVaccinationDTO.getFamilyId();
        String motherName = saveVaccinationDTO.getMotherName() == null ? "" : saveVaccinationDTO.getMotherName();
        String childId = saveVaccinationDTO.getChildId() == null ? "" : saveVaccinationDTO.getChildId();
        String vaccinationName = saveVaccinationDTO.getVaccinationName() == null ? "" : saveVaccinationDTO.getVaccinationName();
        String visitFor = saveVaccinationDTO.getVisitFor() == null ? "" : saveVaccinationDTO.getVisitFor();
        String vaccinationCode = saveVaccinationDTO.getVaccinationCode() == null ? "" : saveVaccinationDTO.getVaccinationCode();
        String visitType = saveVaccinationDTO.getVisitType() == null ? "" : saveVaccinationDTO.getVisitType();
        String desc = saveVaccinationDTO.getDescription() == null ? "" : saveVaccinationDTO.getDescription();
        String visitRound = saveVaccinationDTO.getVisitRound() == null ? "" : saveVaccinationDTO.getVisitRound();
        String latitude = saveVaccinationDTO.getLatitude() == null ? "" : saveVaccinationDTO.getLatitude();
        String longitude = saveVaccinationDTO.getLongitude() == null ? "" : saveVaccinationDTO.getLongitude();
        String dob = saveVaccinationDTO.getDob() == null ? "" : saveVaccinationDTO.getDob();
        // Current Date
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        long mills = date.getTime();

        String visitCat = "";
        List<Family> findCat = familyRepository.findAllByFamilyId(familyId);

        for (Family cat : findCat) {
            visitCat = cat.getCategory();
        }


        // Save in Vaccination
        Vaccination saveRecord = Vaccination.builder()
                .familyId(familyId)
                .date(mills)
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .childId(childId)
                .vaccinationCode(vaccinationCode)
                .build();
        vaccinationRepository.save(saveRecord);

        // Save in Visits
//        Visits saveVaccinationVisit = Visits.builder()
//                .visitType(visitType)
//                .visitFor(visitFor)
//                .memberId(childId)
//                .category(visitCat)
//                .familyId(familyId)
//                .centerName(centerName)
//                .childDob(new Date(dob).getTime())
//                .visitDateTime(mills)
//                .description(desc)
//                .latitude(latitude)
//                .longitude(longitude)
//                .visitRound(visitRound)
//                .build();
//        visitsRepository.save(saveVaccinationVisit);

        return SaveVaccinationDTO.builder()
                .familyId(familyId)
                .date(df.format(mills))
                .motherName(motherName)
                .childId(childId)
                .dob(dob)
                .description(desc)
                .vaccinationName(vaccinationName)
                .visitType(visitType)
                .visitFor(visitFor)
                .familyId(familyId)
                .latitude(latitude)
                .longitude(longitude)
                .visitRound(visitRound)
                .build();
    }


    @Override
    public DashboardFamilyData getDashboardFamilyData(DashboardFilter dashboardFilter) throws ParseException {


        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
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


        // Beneficiary Children
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        // Beneficiary dharti
        LocalDateTime dhartiDate = LocalDateTime.now().minusMonths(6);
        ZonedDateTime dhartiZdt = ZonedDateTime.of(dhartiDate, ZoneId.systemDefault());
        long convertToMills2 = dhartiZdt.toInstant().toEpochMilli();

        List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository.findAllByPregnancyCriteria(startTime.getTime(), endTime.getTime(), dashboardFilter.getCenterId().trim());


        List<PregnantAndDelivery> dhartiWomen = pregnantAndDeliveryRepository.findAllBeneficiaryDharti(startTime.getTime(), endTime.getTime(), dashboardFilter.getCenterId().trim(), convertToMills2);

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<FamilyMember> fm = familyMemberRepository.findAllBeneficiaryChildren(startTime, endTime, dashboardFilter.getCenterId().trim(), convertToMills);
        return DashboardFamilyData.builder()
                .nursingMothers(dhartiWomen.size())
                .pregnantWomen(pdd.size())
                .totalBeneficiary(dhartiWomen.size() + pdd.size() + fm.size())
                .children(fm.size())
                .build();
    }

    @Override
    public List<TotalChildrenData> getTotalChildrenData(DashboardFilter dashboardFilter) throws ParseException {

        List<TotalChildrenData> childrenData = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
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

        LocalDateTime sixthDate = LocalDateTime.now().minusYears(6);
        ZonedDateTime sixthZdt = ZonedDateTime.of(sixthDate, ZoneId.systemDefault());
        long sixthMills = sixthZdt.toInstant().toEpochMilli();

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<FamilyMember> findChildrenRecords = familyMemberRepository.findAllByChildrenCriteria(startTime, endTime, sixthMills);

        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        for (FamilyMember fm : findChildrenRecords) {
            Family findFamily = familyRepository.findByFamilyId(fm.getFamilyId());
            String minority = findFamily.getIsMinority() == null ? "" : findFamily.getIsMinority();
            childrenData.add(TotalChildrenData.builder()
                    .caste(fm.getCategory() == null ? "" : fm.getCategory())
                    .gender(fm.getGender() == null ? "" : fm.getGender())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .isMinority(minority)
                    .name(fm.getName() == null ? "" : fm.getName())
                    .centerId(fm.getCenterId() == null ? "" : fm.getCenterId())
                    .centerName(fm.getCenterName() == null ? "" : fm.getCenterName())
                    .build());
        }
        return childrenData;
    }

//    @Override
//    public List<PregnancyData> getPregnancyData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
//
//
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        List<PregnancyData> addInList = new ArrayList<>();
//        Date startTime = null, endTime = null;
//
//        if (dashboardFilter.getStartDate().trim().length() > 0) {
//            startTime = df.parse(dashboardFilter.getStartDate().trim());
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
//        List<PregnantAndDelivery> findPdd = pregnantAndDeliveryRepository.findAllByPregnancyCriteria(startTime, endTime);
//        for (PregnantAndDelivery cat : findPdd) {
//
//            PregnancyData singleEntry = PregnancyData.builder()
//                    .lastMissedPeriodDate(df.format(new Date(cat.getLastMissedPeriodDate())))
//                    .motherId(cat.getMotherMemberId())
//                    .motherName(cat.getMotherName())
//                    .startDate(df.format(startTime))
//                    .endDate(df.format(endTime))
//                    .build();
//            addInList.add(singleEntry);
//        }
//        return addInList;
//    }

    private String calDuration(long missedPeriod){
        long currentMillis = new Date().getTime();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        LocalDate startTime = Instant.ofEpochMilli(currentMillis).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endTime = Instant.ofEpochMilli(missedPeriod).atZone(ZoneId.systemDefault()).toLocalDate();
        Period diff = Period.between(startTime,endTime);
        log.error("gap "+Math.abs(diff.getMonths()));
       return String.valueOf(Math.abs(diff.getMonths()));
    }

    @Override
    public List<PregnantWomenDetails> getPregnantWomenDetails(DashboardFilter dashboardFilter) throws ParseException {

        List<PregnantWomenDetails> addInList = new ArrayList<>();
        HashSet<String> uniqueMemberId = new HashSet<>();
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

        List<PregnantAndDelivery> findPregnancyData = pregnantAndDeliveryRepository.findAllByPregnancyCriteria(startTime.getTime(), endTime.getTime(), dashboardFilter.getCenterId().trim());

        try {
            for (PregnantAndDelivery visits : findPregnancyData) {

                if (uniqueMemberId.add(visits.getMotherMemberId())) {
                    List<FamilyMember> searchNames = familyMemberRepository.findAllByIdAndNameSearch(visits.getMotherMemberId(), dashboardFilter.getSearch().trim());

                    if (searchNames.size() > 0) {
                        for (FamilyMember searchResults : searchNames) {
                            Family households = familyRepository.findByFamilyId(searchResults.getFamilyId());

                            PregnantWomenDetails addSingle = PregnantWomenDetails.builder()
                                    .name(searchResults.getName())
                                    .motherId(searchResults.getId())
                                    .centerId(searchResults.getCenterId())
                                    .lastMissedPeriodDate(df.format(visits.getLastMissedPeriodDate()))
                                    .husbandName(searchResults.getFatherName())
                                    .dob(df.format(searchResults.getDob()))
                                    .category(searchResults.getCategory())
                                    .minority(households.getIsMinority())
                                    .religion(households.getReligion())
                                    .duration(calDuration(visits.getLastMissedPeriodDate()))
                                    .build();

                            addInList.add(addSingle);
                        }
                    }
                }
            }

        } catch (NullPointerException ignored) {

        }
        return addInList;
    }

    @Override
    public List<DeliveryDTO> getDeliveryData(DashboardFilter dashboardFilter) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<DeliveryDTO> addInList = new ArrayList<>();

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

        List<BabiesBirth> birthList = babiesBirthRepository.findAllByMonth(startTime, endTime,dashboardFilter.getCenterId().trim());

        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        for (BabiesBirth bb : birthList) {
            DeliveryDTO singleEntry = DeliveryDTO.builder()
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .centerId(bb.getCenterId())
                    .childId(bb.getChildId() == null ? "" : bb.getChildId())
                    .birthType(bb.getBirthType() == null ? "" : bb.getBirthType())
                    .birthPlace(bb.getBirthPlace() == null ? "" : bb.getBirthPlace())
                    .motherId(bb.getMotherMemberId() == null ? "" : bb.getMotherMemberId())
                    .build();
            addInList.add(singleEntry);
        }

        return addInList;
    }

    @Override
    public List<VaccinationRecordsDTO> getVaccinationData(DashboardFilter dashboardFilter) throws ParseException {

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
        addOneDay.add(Calendar.DATE,1);
        endTime = addOneDay.getTime();

        List<VaccinationRecordsDTO> addInList = new ArrayList<>();
        List<Visits> vaccinationList = visitsRepository.findAllByVaccinationCriteria(startTime, endTime,dashboardFilter.getCenterId().trim());

        addOneDay.add(Calendar.DATE,-1);
        endTime = addOneDay.getTime();

        for (Visits details : vaccinationList) {
            VaccinationRecordsDTO singleEntry = VaccinationRecordsDTO.builder()
                    .vaccinationCode(details.getVisitType())
                    .centerId(details.getCenterId())
                    .startDate(df.format(startTime))
                    .endDate(df.format(endTime))
                    .centerName(details.getCenterName())
                    .memberId(details.getMemberId())
                    .build();

            addInList.add(singleEntry);
        }

        return addInList;
    }

    @Override
    public HouseholdsDTO updateHouseHold(HouseholdsDTO householdsDTO) {

        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = df.parse(householdsDTO.getHeadDob());
            long mills = date.getTime();
            // Update in Family

            Family findHousehold = familyRepository.findById(householdsDTO.getId()).get();
            findHousehold.setCategory(householdsDTO.getCategory());
            findHousehold.setHouseNo(householdsDTO.getHouseNo());
            findHousehold.setIcdsService(householdsDTO.getIcdsService());
            findHousehold.setIsMinority(householdsDTO.getIsMinority());
            findHousehold.setReligion(householdsDTO.getReligion());
            familyRepository.save(findHousehold);

            // Update in Family Member

            List<FamilyMember> familyDetails = familyMemberRepository.findAllByFamilyId(findHousehold.getFamilyId(), Sort.by(Sort.Direction.DESC, "createdDate"));


            for (FamilyMember fd : familyDetails) {
                fd.setCategory(householdsDTO.getCategory());

                // Update In Anganwadi Children

                List<AnganwadiChildren> ac = anganwadiChildrenRepository.findAllByChildId(fd.getId());

                for (AnganwadiChildren child : ac) {
                    child.setMinority(householdsDTO.getIsMinority());
                    child.setCategory(householdsDTO.getCategory());
                    anganwadiChildrenRepository.save(child);
                }
                familyMemberRepository.save(fd);

                // Update In Pregnant & Delivery

                List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository.findByFamilyId(fd.getFamilyId());

                for (PregnantAndDelivery updatePdd : pdd) {

                    updatePdd.setCategory(householdsDTO.getCategory());
                    updatePdd.setReligion(householdsDTO.getReligion());
                    updatePdd.setHouseNumber(householdsDTO.getHouseNo());

                    pregnantAndDeliveryRepository.save(updatePdd);
                }
            }

            FamilyMember findHead = familyMemberRepository.findByHead(findHousehold.getFamilyId());


            findHead.setName(householdsDTO.getHeadName());
            findHead.setMobileNumber(householdsDTO.getMobileNumber());
            findHead.setGender(householdsDTO.getHeadGender());
            findHead.setDob(mills);
            findHead.setIdType(householdsDTO.getUniqueIdType());
            findHead.setIdNumber(householdsDTO.getUniqueId());
            findHead.setPhoto(householdsDTO.getHeadPic());


            return HouseholdsDTO.builder()
                    .id(findHousehold.getId())
                    .centerName(findHousehold.getCenterName())
                    .uniqueCode(findHead.getIdType())
                    .uniqueId(findHead.getIdNumber())
                    .headName(findHead.getName())
                    .headDob(householdsDTO.getHeadDob())
                    .totalMembers("")
                    .headPic(findHead.getPhoto())
                    .centerId("")
                    .headGender(findHead.getGender())
                    .houseNo(findHousehold.getHouseNo())
                    .mobileNumber(findHead.getMobileNumber())
                    .uniqueIdType(findHead.getIdType())
                    .category(findHousehold.getCategory())
                    .religion(findHousehold.getReligion())
                    .isMinority(findHousehold.getIsMinority())
                    .icdsService(findHousehold.getIcdsService())
                    .build();

        } catch (Exception e) {
            throw new CustomException("Household Not Found");
        }

    }

    @Override
    public FamilyMemberDTO updateHouseHoldMember(FamilyMemberDTO familyMemberDTO) {

        try {

            FamilyMember familyMember = familyMemberRepository.findById(familyMemberDTO.getId()).get();


            Family findFamilyId = familyRepository.findByFamilyId(familyMember.getFamilyId());

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = df.parse(familyMemberDTO.getDob());
            long mills = date.getTime();

            // Updating In Family Member

            familyMember.setName(familyMemberDTO.getName());
            familyMember.setPhoto(familyMemberDTO.getPhoto());
            familyMember.setCategory(findFamilyId.getCategory());
            familyMember.setMotherName(familyMemberDTO.getMotherName());
            familyMember.setFatherName(familyMemberDTO.getFatherName());
            familyMember.setMobileNumber(familyMemberDTO.getMobileNumber());
            familyMember.setStateCode(familyMemberDTO.getStateCode());
            familyMember.setIdType(familyMemberDTO.getIdType());
            familyMember.setIdNumber(familyMemberDTO.getIdNumber());
            familyMember.setRelationWithOwner(familyMemberDTO.getRelationWithOwner());
            familyMember.setGender(familyMemberDTO.getGender());
            familyMember.setDob(mills);
            familyMember.setMaritalStatus(familyMemberDTO.getMaritalStatus());
            familyMember.setMemberCode(familyMemberDTO.getMemberCode());
            familyMember.setHandicap(familyMemberDTO.getHandicap());
            familyMember.setHandicapType(familyMemberDTO.getHandicapType());
            familyMember.setResidentArea(familyMemberDTO.getResidentArea());
            familyMember.setDateOfArrival(familyMemberDTO.getDateOfArrival());
            familyMember.setDateOfLeaving(familyMemberDTO.getDateOfLeaving());
            familyMember.setDateOfMortality(familyMemberDTO.getDateOfMortality());

            familyMemberRepository.save(familyMember);

            // Updating in Anganwadi Children, If Exists


            List<AnganwadiChildren> ac = anganwadiChildrenRepository.findAllByChildIdAndRegisteredTrue(familyMemberDTO.getId());

            if (ac.size() > 0) {

                for (AnganwadiChildren children : ac) {
                    children.setName(familyMemberDTO.getName());
                    children.setGender(familyMemberDTO.getGender());
                    children.setDob(familyMemberDTO.getDob());
                    children.setFatherName(familyMemberDTO.getFatherName());
                    children.setMotherName(familyMemberDTO.getMotherName());
                    children.setMobileNumber(familyMemberDTO.getMobileNumber());

                    anganwadiChildrenRepository.save(children);
                }
            }


            return FamilyMemberDTO.builder()
                    .id(familyMember.getId())
                    .category(familyMember.getCategory())
                    .name(familyMemberDTO.getName())
                    .relationWithOwner(familyMemberDTO.getRelationWithOwner())
                    .category(familyMember.getCategory())
                    .gender(familyMemberDTO.getGender())
                    .centerName(familyMember.getCenterName())
                    .memberCode(familyMemberDTO.getMemberCode())
                    .familyId(familyMember.getFamilyId())
                    .mobileNumber(familyMemberDTO.getMobileNumber() == null ? "" : familyMemberDTO.getMobileNumber())
                    .idType(familyMemberDTO.getIdType() == null ? "" : familyMemberDTO.getIdType())
                    .idNumber(familyMemberDTO.getIdNumber() == null ? "" : familyMemberDTO.getIdNumber())
                    .dob(familyMemberDTO.getDob())
                    .handicap(familyMemberDTO.getHandicap())
                    .maritalStatus(familyMemberDTO.getMaritalStatus())
                    .stateCode(familyMemberDTO.getStateCode())
                    .handicapType(familyMemberDTO.getHandicapType())
                    .motherName(familyMemberDTO.getMotherName())
                    .fatherName(familyMemberDTO.getFatherName())
                    .memberCode(familyMemberDTO.getMemberCode())
                    .residentArea(familyMemberDTO.getResidentArea())
                    .dateOfArrival(familyMemberDTO.getDateOfArrival())
                    .dateOfLeaving(familyMemberDTO.getDateOfLeaving())
                    .dateOfMortality(familyMemberDTO.getDateOfMortality())
                    .photo(familyMemberDTO.getPhoto())
                    .build();

        } catch (Exception e) {
            throw new CustomException("Member Not Found");
        }
    }

    @Override
    public HouseholdsDTO getHouseholdById(String id) {

        try {

            Family family = familyRepository.findById(id).get();
            FamilyMember familyMember = familyMemberRepository.findByHead(family.getFamilyId());

            long getMills = familyMember.getDob();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);

            return HouseholdsDTO.builder()
                    .id(family.getId())
                    .centerName(family.getCenterName())
                    .uniqueCode("")
                    .uniqueId(familyMember.getIdNumber())
                    .headName(familyMember.getName())
                    .headDob(df.format(date))
                    .totalMembers("")
                    .headPic(familyMember.getPhoto())
                    .centerId("")
                    .headGender(familyMember.getGender())
                    .houseNo(family.getHouseNo())
                    .mobileNumber(familyMember.getMobileNumber())
                    .uniqueIdType(familyMember.getIdType())
                    .category(family.getCategory())
                    .religion(family.getReligion())
                    .isMinority(family.getIsMinority())
                    .icdsService(family.getIcdsService())
                    .build();

        } catch (Exception e) {
            throw new CustomException("Household Not Found");
        }
    }

    @Override
    public List<PerVaccineRecord> getVaccinationByChildId(String childId) {

        List<Visits> findRecords = visitsRepository.findAllByVisitForCriteria(childId, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<PerVaccineRecord> addInSingle = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        for (Visits lists : findRecords) {
            Date date = new Date(lists.getVisitDateTime());
            PerVaccineRecord singleList = PerVaccineRecord.builder()
                    .vaccinationCode(lists.getVisitFor())
                    .vaccinationName("")
                    .date(df.format(date))
                    .build();
            addInSingle.add(singleList);
        }

        return addInSingle;
    }

    @Override
    public HouseholdsDTO deleteHouseHold(String familyId, String id) {

        try {

            User verifyUser = userRepository.findById(id).get();
            if (verifyUser.getId().length() > 0 && verifyUser.getRole().equals(ApplicationConstants.USER_ADMIN)) {
                Family findFamily = familyRepository.findByFamilyId(familyId);
                String primaryId = findFamily.getId() == null ? "" : findFamily.getId();
                String centerName = findFamily.getCenterName() == null ? "" : findFamily.getCenterName();
                String centerId = findFamily.getCenterId() == null ? "" : findFamily.getCenterId();
                String category = findFamily.getCategory() == null ? "" : findFamily.getCategory();
                String religion = findFamily.getReligion() == null ? "" : findFamily.getReligion();
                String isMinority = findFamily.getIsMinority() == null ? "" : findFamily.getIsMinority();
                String icdsService = findFamily.getIcdsService() == null ? "" : findFamily.getIcdsService();

                findFamily.setDeleted(true);
                familyRepository.save(findFamily);
                boolean deletedStatus = findFamily.isDeleted();


                List<FamilyMember> findMembers = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.DESC, "createdDate"));
                String headName = "", headPic = "", headGender = "", headMobile = "";
                Date dob = new Date();

                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                long totalMembers = findMembers.size();

                if (findMembers.size() > 0) {

                    for (FamilyMember deleteMember : findMembers) {
                        deleteMember.setDeleted(true);
                        familyMemberRepository.save(deleteMember);

                        removeFromAnganwadiChildren(deleteMember.getId());
                        removeFromAttendance(deleteMember.getId());
                    }

                    for (FamilyMember findHod : findMembers) {

                        if (findHod.getRelationWithOwner().trim().equals("0")) {
                            headName = findHod.getName();
                            dob = new Date(findHod.getDob());
                            headPic = findHod.getPhoto();
                            headGender = findHod.getGender();
                            headMobile = findHod.getMobileNumber();
                            break;
                        } else {
                            headName = findHod.getName();
                            dob = new Date(findHod.getDob());
                            headPic = findHod.getPhoto();
                            headGender = findHod.getGender();
                            headMobile = findHod.getMobileNumber();
                        }

                    }

                    for (FamilyMember finalStep : findMembers) {
                        familyMemberRepository.deleteById(finalStep.getId());
                    }
                }

                familyRepository.deleteById(primaryId);


                return HouseholdsDTO.builder()
                        .id(primaryId)
                        .centerName(centerName)
                        .uniqueCode("")
                        .uniqueId("")
                        .headName(headName)
                        .headDob(df.format(dob))
                        .totalMembers(String.valueOf(totalMembers))
                        .headPic(headPic)
                        .centerId(centerId)
                        .headGender(headGender)
                        .houseNo(findFamily.getHouseNo())
                        .mobileNumber(headMobile)
                        .uniqueIdType("")
                        .deleted(deletedStatus)
                        .category(category)
                        .religion(religion)
                        .isMinority(isMinority)
                        .icdsService(icdsService)
                        .build();


            } else {
                throw new CustomException("Un-Authorized Access");
            }

        } catch (NullPointerException e) {
            throw new CustomException("Family Not Found or Already Deleted, Please Check Again");
        }
    }

    private void removeFromAnganwadiChildren(String primaryId) {

        List<AnganwadiChildren> removeChild = anganwadiChildrenRepository.findAllByChildId(primaryId);

        if (removeChild.size() > 0) {
            anganwadiChildrenRepository.deleteAllByChildId(primaryId);
        }

    }

    private void removeFromAttendance(String primaryId) {

        List<Attendance> removeAttendance = attendanceRepository.findAllByChildId(primaryId);
        if (removeAttendance.size() > 0) {
            attendanceRepository.deleteAllByChildId(primaryId);
        }
    }

    private void removeFromBabiesBirth(String primaryId) {

        if (babiesBirthRepository.findByChildId(primaryId) != null) {
            BabiesBirth removeBirthRecords = babiesBirthRepository.findByChildId(primaryId);
            babiesBirthRepository.deleteById(removeBirthRecords.getId());
        }
    }

    private void removeFromWeightTracking(String primaryId) {

        List<WeightTracking> wt = weightTrackingRepository.findByChildId(primaryId, Sort.by(Sort.Direction.DESC, "createdDate"));
        if (wt.size() > 0) {
            weightTrackingRepository.deleteByChildId(primaryId);
        }
    }

    private void removeFromVisits(String primaryId) {
        List<Visits> visits = visitsRepository.findAllByMemberId(primaryId);
        if (visits.size() > 0) {
            visitsRepository.deleteByMemberId(primaryId);
        }
    }

    @Override
    public FamilyMemberDTO deleteFamilyMembers(String memberId, String id) {

        try {
            User verifyUser = userRepository.findById(id).get();
            if (verifyUser.getId().length() > 0 && verifyUser.getRole().equals(ApplicationConstants.USER_ADMIN)) {


                FamilyMember checkMember = familyMemberRepository.findById(memberId).get();
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                String primaryId = checkMember.getId();
                String category = checkMember.getCategory() == null ? "" : checkMember.getCategory();
                String name = checkMember.getName() == null ? "" : checkMember.getName();
                String relationWithOwner = checkMember.getRelationWithOwner() == null ? "" : checkMember.getRelationWithOwner();
                String gender = checkMember.getGender() == null ? "" : checkMember.getGender();
                String centerName = checkMember.getCenterName() == null ? "" : checkMember.getCenterName();
                String memberCode = checkMember.getMemberCode() == null ? "" : checkMember.getMemberCode();
                String familyId = checkMember.getFamilyId() == null ? "" : checkMember.getFamilyId();
                String mobileNo = checkMember.getMobileNumber() == null ? "" : checkMember.getMobileNumber();
                String idType = checkMember.getIdType() == null ? "" : checkMember.getIdType();
                String idNumber = checkMember.getIdNumber() == null ? "" : checkMember.getIdNumber();
                String dob = df.format(new Date(checkMember.getDob()));
                String handicap = checkMember.getHandicap() == null ? "" : checkMember.getHandicap();
                String maritalStatus = checkMember.getMaritalStatus() == null ? "" : checkMember.getMaritalStatus();
                String stateCode = checkMember.getStateCode() == null ? "" : checkMember.getStateCode();
                String handicapType = checkMember.getHandicapType() == null ? "" : checkMember.getHandicapType();
                String motherName = checkMember.getMotherName() == null ? "" : checkMember.getMotherName();
                String fatherName = checkMember.getFatherName() == null ? "" : checkMember.getFatherName();
                String residentArea = checkMember.getResidentArea() == null ? "" : checkMember.getResidentArea();
                String dateOfArrival = checkMember.getDateOfArrival() == null ? "" : checkMember.getDateOfArrival();
                String dateOfLeaving = checkMember.getDateOfLeaving() == null ? "" : checkMember.getDateOfLeaving();
                String dateOfMortality = checkMember.getDateOfMortality() == null ? "" : checkMember.getDateOfMortality();
                String photo = checkMember.getPhoto() == null ? "" : checkMember.getPhoto();

                familyMemberRepository.deleteById(primaryId);
                removeFromAnganwadiChildren(primaryId);
                removeFromAttendance(primaryId);
                removeFromBabiesBirth(primaryId);
                removeFromWeightTracking(primaryId);
                removeFromVisits(primaryId);

                return FamilyMemberDTO.builder()
                        .id(primaryId)
                        .category(category)
                        .name(name)
                        .relationWithOwner(relationWithOwner)
                        .gender(gender)
                        .centerName(centerName)
                        .memberCode(memberCode)
                        .familyId(familyId)
                        .mobileNumber(mobileNo)
                        .idType(idType)
                        .idNumber(idType)
                        .dob(dob)
                        .handicap(handicap)
                        .maritalStatus(maritalStatus)
                        .stateCode(stateCode)
                        .handicapType(handicapType)
                        .motherName(motherName)
                        .fatherName(fatherName)
                        .residentArea(residentArea)
                        .dateOfArrival(dateOfArrival)
                        .dateOfLeaving(dateOfLeaving)
                        .dateOfMortality(dateOfMortality)
                        .photo(photo)
                        .build();

            } else {
                throw new CustomException("Un-Authorized Access");
            }

        } catch (NullPointerException | NoSuchElementException e) {
            throw new CustomException("Member Not Found or Already Deleted, Please Check Again");
        }
    }

    @Override
    public HouseholdWomenDetails getHouseholdWomenDetails(String centerId, String centerName) {

        // Count Pregnant Women
        List<PregnantAndDelivery> findPW = pregnantAndDeliveryRepository.findAllByCenterIdAndDateOfDelivery(centerId, Sort.by(Sort.Direction.DESC, "createdDate"));
        HashSet<String> uniqueWomen = new HashSet<>();

        for (PregnantAndDelivery pd : findPW) {
            uniqueWomen.add(pd.getMotherMemberId().trim());
        }

        // Count New Born Children

        LocalDateTime date = LocalDateTime.now().minusMonths(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        List<FamilyMember> findChild = familyMemberRepository.findAllByDobCriteria(convertToMills, centerName);


        return HouseholdWomenDetails.builder()
                .pregnantWomen(uniqueWomen.size())
                .newBornBabies(findChild.size())
                .build();
    }

    private void updatePregnancyHouseVisitSchedule(PregnantAndDelivery pd) {

        LocalDate localDate = Instant.ofEpochMilli(pd.getLastMissedPeriodDate()).atZone(ZoneId.systemDefault()).toLocalDate();

        int round_1 = 0,round_2=0,round_3=0, period = 0;
        while (round_1 < 2 && round_2<2 && round_3<2) {
            HouseVisitSchedule addVisit_1 = HouseVisitSchedule.builder()
                    .visitType("PREGNANCY")
                    .visitRound(String.valueOf(++round_1))
                    .comments("")
                    .centerId(pd.getCenterId())
                    .centerName(pd.getCenterName())
                    .latitude("")
                    .longitude("")
                    .visitName("TILL_04_06_MONTHS")
                    .memberId(pd.getMotherMemberId())
                    .visitDate(0)
                    .dueDate(localDate.plusDays(160 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build();

            HouseVisitSchedule addVisit_2 = HouseVisitSchedule.builder()
                    .visitType("PREGNANCY")
                    .visitRound(String.valueOf(++round_2))
                    .comments("")
                    .centerId(pd.getCenterId())
                    .centerName(pd.getCenterName())
                    .latitude("")
                    .longitude("")
                    .visitName("TILL_07_09_MONTHS")
                    .memberId(pd.getMotherMemberId())
                    .visitDate(0)
                    .dueDate(localDate.plusDays(260 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build();

            HouseVisitSchedule addVisit_3 = HouseVisitSchedule.builder()
                    .visitType("DELIVERY")
                    .visitRound(String.valueOf(++round_3))
                    .comments("")
                    .centerId(pd.getCenterId())
                    .centerName(pd.getCenterName())
                    .latitude("")
                    .longitude("")
                    .visitName("DAY_OF_DELIVERY")
                    .memberId(pd.getMotherMemberId())
                    .visitDate(0)
                    .dueDate(localDate.plusDays(260 + period).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build();


            houseVisitScheduleRepository.save(addVisit_1);
            houseVisitScheduleRepository.save(addVisit_2);
            houseVisitScheduleRepository.save(addVisit_3);

            period += 20;
        }
    }

    @Override
    public PregnantAndDeliveryDTO registerPregnantWomen(PregnantAndDeliveryDTO pregnantAndDeliveryDTO, String centerId) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        long missedPeriodDate = df.parse(pregnantAndDeliveryDTO.getLastMissedPeriodDate()).getTime();

        Date currentTime = new Date();
        String formatToString = df.format(currentTime.getTime());
        Date formatToTime = df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        FamilyMember fm = familyMemberRepository.findById(pregnantAndDeliveryDTO.getMotherMemberId()).get();
        Family family = familyRepository.findByFamilyId(fm.getFamilyId());

        PregnantAndDelivery pd = PregnantAndDelivery.builder()
                .familyId(pregnantAndDeliveryDTO.getFamilyId() == null ? "" : pregnantAndDeliveryDTO.getFamilyId())
                .motherMemberId(pregnantAndDeliveryDTO.getMotherMemberId() == null ? "" : pregnantAndDeliveryDTO.getMotherMemberId())
                .motherName(fm.getName() == null ? "" : fm.getName())
                .dob(fm.getDob())
                .husbandName(fm.getFatherName() == null ? "" : fm.getFatherName())
                .childName(pregnantAndDeliveryDTO.getChildName() == null ? "" : pregnantAndDeliveryDTO.getChildName())
                .childGender(pregnantAndDeliveryDTO.getChildGender() == null ? "" : pregnantAndDeliveryDTO.getChildGender())
                .category(fm.getCategory() == null ? "" : fm.getCategory())
                .religion(family.getReligion() == null ? "" : family.getReligion())
                .houseNumber(family.getHouseNo() == null ? "" : family.getHouseNo())
                .profilePic(fm.getPhoto() == null ? "" : fm.getPhoto())
                .regDate(timestamp)
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .noOfChild(pregnantAndDeliveryDTO.getNoOfChild())
                .lastMissedPeriodDate(missedPeriodDate)
                .dateOfDelivery(0)
                .build();

        pregnantAndDeliveryRepository.save(pd);

        updatePregnancyHouseVisitSchedule(pd);

        return PregnantAndDeliveryDTO.builder()
                .id(pd.getId())
                .familyId(pregnantAndDeliveryDTO.getFamilyId() == null ? "" : pregnantAndDeliveryDTO.getFamilyId())
                .motherMemberId(pregnantAndDeliveryDTO.getMotherMemberId() == null ? "" : pregnantAndDeliveryDTO.getMotherMemberId())
                .motherName(fm.getName() == null ? "" : fm.getName())
                .dob(df.format(new Date(fm.getDob())))
                .husbandName(fm.getFatherName() == null ? "" : fm.getFatherName())
                .profilePic(fm.getPhoto() == null ? "" : fm.getPhoto())
                .childName(pregnantAndDeliveryDTO.getChildName() == null ? "" : pregnantAndDeliveryDTO.getChildName())
                .childGender(pregnantAndDeliveryDTO.getChildGender() == null ? "" : pregnantAndDeliveryDTO.getChildGender())
                .category(fm.getCategory() == null ? "" : fm.getCategory())
                .religion(family.getReligion() == null ? "" : family.getReligion())
                .houseNumber(family.getHouseNo() == null ? "" : family.getHouseNo())
                .regDate(df.format(formatToTime))
                .centerId(centerId)
                .isDeleted(pd.isDeleted())
                .centerName(commonMethodsService.findCenterName(centerId))
                .noOfChild(pregnantAndDeliveryDTO.getNoOfChild())
                .lastMissedPeriodDate(df.format(missedPeriodDate))
                .dateOfDelivery(pregnantAndDeliveryDTO.getDateOfDelivery() == null ? "" : pregnantAndDeliveryDTO.getDateOfDelivery())
                .build();
    }

    @Override
    public List<PregnantAndDeliveryDTO> getAllPregnantWomenDetails(String centerId) {
        List<PregnantAndDelivery> findPD = pregnantAndDeliveryRepository.findAllByCenterIdAndDateOfDelivery(centerId, Sort.by(Sort.Direction.DESC, "createdDate"));
        List<PregnantAndDeliveryDTO> addInList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (findPD.size() > 0) {
            for (PregnantAndDelivery pd : findPD) {
                String dod = "";
                if (pd.getDateOfDelivery() > 0) {
                    dod = df.format(pd.getDateOfDelivery());
                }

                PregnantAndDeliveryDTO singleEntry = PregnantAndDeliveryDTO.builder()
                        .id(pd.getId())
                        .familyId(pd.getFamilyId() == null ? "" : pd.getFamilyId())
                        .centerId(pd.getCenterId() == null ? "" : pd.getCenterId())
                        .centerName(pd.getCenterName() == null ? "" : pd.getCenterName())
                        .regDate(df.format(pd.getRegDate()))
                        .noOfChild(pd.getNoOfChild())
                        .isDeleted(pd.isDeleted())
                        .motherMemberId(pd.getMotherMemberId() == null ? "" : pd.getMotherMemberId())
                        .motherName(pd.getMotherName() == null ? "" : pd.getMotherName())
                        .dob(df.format(new Date(pd.getDob())))
                        .husbandName(pd.getHusbandName() == null ? "" : pd.getHusbandName())
                        .profilePic(pd.getProfilePic() == null ? "" : pd.getProfilePic())
                        .childName(pd.getChildName() == null ? "" : pd.getChildName())
                        .childGender(pd.getChildGender() == null ? "" : pd.getChildGender())
                        .category(pd.getCategory() == null ? "" : pd.getCategory())
                        .religion(pd.getReligion() == null ? "" : pd.getReligion())
                        .houseNumber(pd.getHouseNumber() == null ? "" : pd.getHouseNumber())
                        .dateOfDelivery(dod)
                        .lastMissedPeriodDate(df.format(pd.getLastMissedPeriodDate()))
                        .build();
                addInList.add(singleEntry);
            }

        }
        return addInList;

    }

    @Override
    public PregnantAndDeliveryDTO updatePregnantWomenDetails(PregnantAndDeliveryDTO pregnantAndDeliveryDTO, String centerId) throws ParseException {
        if (StringUtils.isEmpty(pregnantAndDeliveryDTO.getId())) {
            throw new CustomException("Id Not Passed, Please Check");
        }

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        long dod = df.parse(pregnantAndDeliveryDTO.getDateOfDelivery()).getTime();
        long missedPeriodDate = df.parse(pregnantAndDeliveryDTO.getLastMissedPeriodDate()).getTime();

        try {
            PregnantAndDelivery findPD = pregnantAndDeliveryRepository.findById(pregnantAndDeliveryDTO.getId()).get();

            findPD.setChildName(pregnantAndDeliveryDTO.getChildName());
            findPD.setChildGender(pregnantAndDeliveryDTO.getChildGender());
            findPD.setNoOfChild(pregnantAndDeliveryDTO.getNoOfChild());
            findPD.setDateOfDelivery(dod);
            findPD.setLastMissedPeriodDate(missedPeriodDate);
            pregnantAndDeliveryRepository.save(findPD);


            return PregnantAndDeliveryDTO.builder()
                    .id(findPD.getId())
                    .familyId(findPD.getFamilyId() == null ? "" : findPD.getFamilyId())
                    .centerId(findPD.getCenterId() == null ? "" : findPD.getCenterId())
                    .centerName(findPD.getCenterName() == null ? "" : findPD.getCenterName())
                    .regDate(df.format(findPD.getRegDate()))
                    .noOfChild(findPD.getNoOfChild())
                    .motherMemberId(findPD.getMotherMemberId() == null ? "" : findPD.getMotherMemberId())
                    .motherName(findPD.getMotherName() == null ? "" : findPD.getMotherName())
                    .childName(findPD.getChildName() == null ? "" : findPD.getChildName())
                    .childGender(findPD.getChildGender() == null ? "" : findPD.getChildGender())
                    .category(findPD.getCategory() == null ? "" : findPD.getCategory())
                    .religion(findPD.getReligion() == null ? "" : findPD.getReligion())
                    .houseNumber(findPD.getHouseNumber() == null ? "" : findPD.getHouseNumber())
                    .dateOfDelivery(df.format(findPD.getDateOfDelivery()))
                    .isDeleted(findPD.isDeleted())
                    .lastMissedPeriodDate(df.format(findPD.getLastMissedPeriodDate()))
                    .build();

        } catch (NoSuchElementException | NullPointerException e) {
            throw new CustomException("Details Not Found, Please Check");
        }

    }

    @Override
    public PregnantAndDeliveryDTO deletePregnantWomenDetails(String id) {

        if (StringUtils.isEmpty(id)) {
            throw new CustomException("Id Not Passed, Please Check");
        }

        try {
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            PregnantAndDelivery findPD = pregnantAndDeliveryRepository.findById(id).get();

            findPD.setDeleted(true);

            PregnantAndDeliveryDTO deletePD = PregnantAndDeliveryDTO.builder()
                    .id(findPD.getId())
                    .familyId(findPD.getFamilyId() == null ? "" : findPD.getFamilyId())
                    .centerId(findPD.getCenterId() == null ? "" : findPD.getCenterId())
                    .centerName(findPD.getCenterName() == null ? "" : findPD.getCenterName())
                    .regDate(df.format(findPD.getRegDate()))
                    .noOfChild(findPD.getNoOfChild())
                    .isDeleted(findPD.isDeleted())
                    .motherMemberId(findPD.getMotherMemberId() == null ? "" : findPD.getMotherMemberId())
                    .dateOfDelivery(df.format(findPD.getDateOfDelivery()))
                    .lastMissedPeriodDate(df.format(findPD.getLastMissedPeriodDate()))
                    .build();

            pregnantAndDeliveryRepository.deleteById(id);

            return deletePD;


        } catch (NullPointerException | NoSuchElementException e) {
            throw new CustomException("Details Not Found or Already Deleted, Please Check");
        }


    }

    @Override
    public List<WomenListByPeriodDateDTO> getWomenListByPeriodDate(String centerId) {

        List<FamilyMember> findWomen = familyMemberRepository.findAllByGenderAndCenterId(centerId);
        HashSet<String> uniqueWomenList = new HashSet<>();
        List<WomenListByPeriodDateDTO> addInList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (findWomen.size() > 0) {

            for (FamilyMember womenList : findWomen) {

                List<PregnantAndDelivery> findPD = pregnantAndDeliveryRepository.findAllByMotherMemberId(womenList.getId(), Sort.by(Sort.Direction.DESC, "lastMissedPeriodDate"));

                // Fetching From PregnantAndDelivery

                for (PregnantAndDelivery pd : findPD) {
                    if (uniqueWomenList.add(pd.getMotherMemberId())) {
                        WomenListByPeriodDateDTO singePDList = WomenListByPeriodDateDTO.builder()
                                .id(pd.getId())
                                .familyId(pd.getFamilyId() == null ? "" : pd.getFamilyId())
                                .centerId(pd.getCenterId() == null ? "" : pd.getCenterId())
                                .centerName(pd.getCenterName() == null ? "" : pd.getCenterName())
                                .regDate(df.format(pd.getRegDate()))
                                .noOfChild(pd.getNoOfChild())
                                .memberId(pd.getMotherMemberId() == null ? "" : pd.getMotherMemberId())
                                .name(pd.getMotherName() == null ? "" : pd.getMotherName())
                                .profilePic(pd.getProfilePic()== null ? "" : pd.getProfilePic())
                                .husbandName(pd.getHusbandName()== null ? "" : pd.getHusbandName())
                                .dob(df.format(new Date(pd.getDob())))
                                .childName(pd.getChildName() == null ? "" : pd.getChildName())
                                .childGender(pd.getChildGender() == null ? "" : pd.getChildGender())
                                .category(pd.getCategory() == null ? "" : pd.getCategory())
                                .religion(pd.getReligion() == null ? "" : pd.getReligion())
                                .houseNo(pd.getHouseNumber() == null ? "" : pd.getHouseNumber())
                                .dateOfDelivery(df.format(pd.getDateOfDelivery()))
                                .isDeleted(pd.isDeleted())
                                .lastMissedPeriodDate(df.format(pd.getLastMissedPeriodDate()))
                                .build();

                        addInList.add(singePDList);

                    }
                }

                // Fetching From Family Member

                if (uniqueWomenList.add(womenList.getId())) {
                    Family findFamilyDetails = familyRepository.findByFamilyId(womenList.getFamilyId());

                    WomenListByPeriodDateDTO singleWomenList = WomenListByPeriodDateDTO.builder()
                            .id(womenList.getId())
                            .familyId(womenList.getFamilyId() == null ? "" : womenList.getFamilyId())
                            .centerId(womenList.getCenterId() == null ? "" : womenList.getCenterId())
                            .centerName(womenList.getCenterName() == null ? "" : womenList.getCenterName())
                            .regDate("")
                            .noOfChild(0)
                            .memberId(womenList.getId() == null ? "" : womenList.getId())
                            .name(womenList.getMotherName() == null ? "" : womenList.getMotherName())
                            .profilePic(womenList.getPhoto()== null ? "" :womenList.getPhoto())
                            .husbandName(womenList.getFatherName()== null ? "" :womenList.getFatherName())
                            .dob(df.format(new Date(womenList.getDob())))
                            .childName("")
                            .childGender("")
                            .category(womenList.getCategory() == null ? "" : womenList.getCategory())
                            .religion(findFamilyDetails.getReligion() == null ? "" : findFamilyDetails.getReligion())
                            .houseNo(findFamilyDetails.getHouseNo() == null ? "" : findFamilyDetails.getHouseNo())
                            .dateOfDelivery("")
                            .isDeleted(womenList.isDeleted())
                            .lastMissedPeriodDate("")
                            .build();

                    addInList.add(singleWomenList);

                }

            }

        }
        return addInList;
    }

    @Override
    public List<NewBornChildDTO> getNewBornChildRecords(String centerName) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String birthType = "", birthPlace = "", motherMeemberId = "", motherPhoto = "",srNo="";
        LocalDateTime date = LocalDateTime.now().minusMonths(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        List<FamilyMember> findChild = familyMemberRepository.findAllByDobCriteria(convertToMills, centerName);
        List<NewBornChildDTO> addInList = new ArrayList<>();

        try {
            if (findChild.size() > 0) {
                for (FamilyMember bb : findChild) {

                    List<BabiesBirth> birthList = babiesBirthRepository.findAllByChildId(bb.getId());

                    for (BabiesBirth lists : birthList) {
                        birthPlace = lists.getBirthPlace();
                        birthType = lists.getBirthType();
                        srNo = lists.getSrNo();
                        motherMeemberId = lists.getMotherMemberId();
                    }

                    List<FamilyMember> findMotherDetails = familyMemberRepository.findByFamilyIdAndName(bb.getFamilyId().trim(), bb.getMotherName().trim());

                    for (FamilyMember fm : findMotherDetails) {
                        motherPhoto = fm.getPhoto().trim();
                    }

                    Family familyDetails = familyRepository.findByFamilyId(bb.getFamilyId());

                    NewBornChildDTO singleEntry = NewBornChildDTO.builder()
                            .id(bb.getId())
                            .name(bb.getName() == null ? "" : bb.getName())
                            .motherPhoto(motherPhoto)
                            .motherName(bb.getMotherName() == null ? "" : bb.getMotherName())
                            .fatherName(bb.getFatherName() == null ? "":bb.getFatherName())
                            .houseNumber(familyDetails.getHouseNo() == null ? "" : familyDetails.getHouseNo())
                            .relationWithOwner(bb.getRelationWithOwner() == null ? "" : bb.getRelationWithOwner())
                            .dob(df.format(bb.getDob()))
                            .srNo(srNo)
                            .birthPlace(birthPlace)
                            .birthType(birthType)
                            .familyId(bb.getFamilyId() == null ? "" : bb.getFamilyId())
                            .motherMemberId(motherMeemberId)
                            .gender(bb.getGender() == null ? "" : bb.getGender())
                            .centerId(bb.getCenterId() == null ? "" : bb.getCenterId())
                            .centerName(bb.getCenterName())
                            .visitFor("")
                            .visitType("")
                            .firstWeight("")
                            .visitRound("")
                            .height("")
                            .build();
                    addInList.add(singleEntry);
                }

            }
        } catch (Exception e) {

        }

        return addInList;
    }

    @Override
    public NewBornChildDTO updateNewBornChildRecords(BirthPlaceDTO birthPlaceDTO) {

        if (StringUtils.isEmpty(birthPlaceDTO.getId())) {
            throw new CustomException("Id Not Passed, Please Check");
        }
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        try {
            HashSet<String > uniqueWeight = new HashSet<>();
            BabiesBirth bb = babiesBirthRepository.findByIdAndDeletedIsFalse(birthPlaceDTO.getId());
            Date date = df.parse(birthPlaceDTO.getDob());
            long miils = date.getTime();


            // Updating in Birth Table

            bb.setBirthPlace(birthPlaceDTO.getBirthPlace());
            bb.setBirthType(birthPlaceDTO.getBirthType());
            bb.setGender(birthPlaceDTO.getGender());
            bb.setHeight(birthPlaceDTO.getHeight());
            bb.setSrNo(birthPlaceDTO.getSrNo());
            bb.setFirstWeight(birthPlaceDTO.getFirstWeight());
            bb.setName(birthPlaceDTO.getName());
            bb.setDob(miils);

            babiesBirthRepository.save(bb);

            // Updating in Family Member Table

            FamilyMember findChild = familyMemberRepository.findById(bb.getChildId()).get();

            findChild.setName(birthPlaceDTO.getName());
            findChild.setDob(miils);
            findChild.setRelationWithOwner(birthPlaceDTO.getRelationWithOwner());
            findChild.setGender(birthPlaceDTO.getGender());

            familyMemberRepository.save(findChild);

            // Saving in Weight Tracking Table

            List<WeightTracking> wt = weightTrackingRepository.findByChildId(bb.getChildId(),Sort.by(Sort.Direction.ASC, "createdDate"));

            for(WeightTracking tracking :  wt){
                if(uniqueWeight.add(tracking.getChildId())) {
                    tracking.setHeight(birthPlaceDTO.getHeight());
                    tracking.setWeight(birthPlaceDTO.getFirstWeight());
                    weightTrackingRepository.save(tracking);
                }
            }


            FamilyMember findMotherDetails = familyMemberRepository.findById(bb.getMotherMemberId()).get();
            Family family = familyRepository.findByFamilyId(bb.getFamilyId());

            return NewBornChildDTO.builder()
                    .id(bb.getId())
                    .name(bb.getName() == null ? "" : bb.getName())
                    .motherPhoto(findMotherDetails.getPhoto())
                    .motherName(findMotherDetails.getName() == null ? "" : findMotherDetails.getName())
                    .houseNumber(family.getHouseNo() == null ? "" : family.getHouseNo())
                    .relationWithOwner(findChild.getRelationWithOwner() == null ? "" : findChild.getRelationWithOwner())
                    .dob(df.format(bb.getDob()))
                    .srNo(birthPlaceDTO.getSrNo())
                    .birthPlace(bb.getBirthPlace())
                    .birthType(bb.getBirthType())
                    .familyId(bb.getFamilyId() == null ? "" : bb.getFamilyId())
                    .motherMemberId(bb.getMotherMemberId())
                    .gender(bb.getGender() == null ? "" : bb.getGender())
                    .centerId(bb.getCenterId() == null ? "" : bb.getCenterId())
                    .centerName(bb.getCenterName())
                    .visitFor("")
                    .visitType("")
                    .firstWeight("")
                    .visitRound("")
                    .height("")
                    .build();


        } catch (NullPointerException | NoSuchElementException | ParseException e) {
            throw new CustomException("Child Not Found.");
        }



    }

    @Override
    public List<PregnantAndDeliveryDTO> getDhatriDetails(String centerId) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        LocalDateTime date = LocalDateTime.now().minusMonths(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();
        List<PregnantAndDeliveryDTO> addInList = new ArrayList<>();

        List<PregnantAndDelivery> findPD = pregnantAndDeliveryRepository.findAllByDeliveryCriteria(centerId, convertToMills, Sort.by(Sort.Direction.DESC, "dateOfDelivery"));

        for (PregnantAndDelivery pd : findPD) {

            PregnantAndDeliveryDTO singleEntry = PregnantAndDeliveryDTO.builder()
                    .id(pd.getId())
                    .familyId(pd.getFamilyId() == null ? "" : pd.getFamilyId())
                    .centerId(pd.getCenterId() == null ? "" : pd.getCenterId())
                    .centerName(pd.getCenterName() == null ? "" : pd.getCenterName())
                    .regDate(df.format(pd.getRegDate()))
                    .noOfChild(pd.getNoOfChild())
                    .isDeleted(pd.isDeleted())
                    .motherMemberId(pd.getMotherMemberId() == null ? "" : pd.getMotherMemberId())
                    .motherName(pd.getMotherName() == null ? "" : pd.getMotherName())
                    .dob(df.format(new Date(pd.getDob())))
                    .husbandName(pd.getHusbandName() == null ? "" : pd.getHusbandName())
                    .profilePic(pd.getProfilePic() == null ? "" : pd.getProfilePic())
                    .childName(pd.getChildName() == null ? "" : pd.getChildName())
                    .childGender(pd.getChildGender() == null ? "" : pd.getChildGender())
                    .category(pd.getCategory() == null ? "" : pd.getCategory())
                    .religion(pd.getReligion() == null ? "" : pd.getReligion())
                    .houseNumber(pd.getHouseNumber() == null ? "" : pd.getHouseNumber())
                    .dateOfDelivery(df.format(pd.getDateOfDelivery()))
                    .lastMissedPeriodDate(df.format(pd.getLastMissedPeriodDate()))
                    .build();
            addInList.add(singleEntry);
        }
        return addInList;
    }

    @Override
    public List<FamilyChildrenDetails> getAllChildrenDetails(String centerName) {

        LocalDateTime date = LocalDateTime.now().minusYears(7);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();
        List<FamilyChildrenDetails> addInList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        List<FamilyMember> findChildren = familyMemberRepository.findAllFamilyChildrenByCenterId(centerName, convertToMills, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (FamilyMember fm : findChildren) {

            Family findFamily = familyRepository.findByFamilyId(fm.getFamilyId());

            FamilyChildrenDetails singleChild = FamilyChildrenDetails.builder()
                    .name(fm.getName() == null ? "" : fm.getName())
                    .dob(df.format(fm.getDob()))
                    .photo(fm.getPhoto() == null ? "" : fm.getPhoto())
                    .gender(fm.getGender() == null ? "" : fm.getGender())
                    .motherName(fm.getMotherName() == null ? "" : fm.getMotherName())
                    .fatherName(fm.getFatherName() == null ? "" : fm.getFatherName())
                    .houseNo(findFamily.getHouseNo() == null ? "" : findFamily.getHouseNo())
                    .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                    .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                    .build();
            addInList.add(singleChild);
        }

        return addInList;
    }

    @Override
    public DeleteBornChildDTO deleteNewBornChildRecords(String id) {

        try {
            String name = "", relationWithOwner = "", dob = "", birthPlace = "", birthType = "", familyId = "", gender = "", centerId = "", firstWeight = "", height = "", centerName = "";
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            DeleteBornChildDTO deleteRecord = new DeleteBornChildDTO();
            BabiesBirth findChild = new BabiesBirth();

            FamilyMember findMember = new FamilyMember();

            if (familyMemberRepository.findById(id).isPresent()) {
                findMember = familyMemberRepository.findById(id).get();
                name = findMember.getName() == null ? "" : findMember.getName();
                dob = df.format(findMember.getDob());
                relationWithOwner = findMember.getRelationWithOwner() == null ? "" : findMember.getRelationWithOwner();
                familyId = findMember.getFamilyId() == null ? "" : findMember.getFamilyId();
                gender = findMember.getGender() == null ? "" : findMember.getGender();
                centerId = findMember.getCenterId() == null ? "" : findMember.getCenterId();
                centerName = findMember.getCenterName()== null ? "" : findMember.getCenterName();
                familyMemberRepository.deleteById(id);
            }

            if (babiesBirthRepository.findById(id).isPresent()) {
                findChild = babiesBirthRepository.findById(id).get();
                birthPlace = findChild.getBirthPlace()== null ? "" :findChild.getBirthPlace();
                birthType = findChild.getBirthType()== null ? "" :findChild.getBirthType();
                firstWeight = findChild.getFirstWeight() == null ? "" : findChild.getFirstWeight();
                height = findChild.getHeight() == null ? "" : findChild.getHeight();

                babiesBirthRepository.deleteById(findChild.getId());
            }

            deleteRecord = DeleteBornChildDTO.builder()
                    .id(id)
                    .name(name)
                    .relationWithOwner(relationWithOwner)
                    .dob(dob)
                    .birthPlace(birthPlace)
                    .birthType(birthType)
                    .familyId(familyId)
                    .gender(gender)
                    .centerId(centerId)
                    .firstWeight(firstWeight)
                    .height(height)
                    .deleted(true)
                    .centerName(centerName)
                    .build();


            List<WeightTracking> checkInWeightTable = weightTrackingRepository.findAllByChildId(findMember.getId(), Sort.by(Sort.Direction.DESC, "createdDate"));

            if (checkInWeightTable.size() > 0) {
                weightTrackingRepository.deleteAllByChildId(findChild.getChildId());
            }
            return deleteRecord;

        } catch (NullPointerException | NoSuchElementException e) {
            throw new CustomException("Child Not Found");
        }
    }

    @Override
    public List<BeneficiaryList> getAllBeneficiaryList(String centerId) {
        List<FamilyMember> findFemales = new ArrayList<>();
        List<FamilyMember> findChildren = new ArrayList<>();

        if (centerId != null && centerId.trim().length() > 0) {

            findFemales = familyMemberRepository.findAllGenderByCenterId("2", centerId.trim());

            LocalDateTime date = LocalDateTime.now().minusYears(7);
            ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
            long convertToMills = zdt.toInstant().toEpochMilli();

            findChildren = familyMemberRepository.findAllChildrenUnder7YearsByCenterId(convertToMills, centerId.trim());
        } else {
            findFemales = familyMemberRepository.findAllByGender("2");

            LocalDateTime date = LocalDateTime.now().minusYears(7);
            ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
            long convertToMills = zdt.toInstant().toEpochMilli();

            findChildren = familyMemberRepository.findAllByChildrenUnder7Years(convertToMills);
        }


        List<BeneficiaryList> addInList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        for (FamilyMember familyMember : findFemales) {

            List<Visits> checkInVisits = visitsRepository.findAllByLadiesBeneficiaryCriteria(familyMember.getId());
            Family findHouseholds = familyRepository.findByFamilyId(familyMember.getFamilyId());

            if (checkInVisits.size() > 0) {

                BeneficiaryList addSingle = BeneficiaryList.builder()
                        .category(familyMember.getCategory() == null ? "" : familyMember.getCategory())
                        .id(familyMember.getId() == null ? "" : familyMember.getId())
                        .name(familyMember.getName() == null ? "" : familyMember.getName())
                        .dob(df.format(new Date(familyMember.getDob())))
                        .centerName(familyMember.getCenterName() == null ? "" : familyMember.getCenterName())
                        .uniqueCode(familyMember.getMemberCode() == null ? "" : familyMember.getMemberCode())
                        .houseNo(findHouseholds.getHouseNo() == null ? "" : findHouseholds.getHouseNo())
                        .centerId(familyMember.getCenterId() == null ? "" : familyMember.getCenterId())
                        .uniqueIdType(familyMember.getIdType() == null ? "" : familyMember.getIdType())
                        .uniqueId(familyMember.getIdNumber() == null ? "" : familyMember.getIdNumber())
                        .religion(findHouseholds.getReligion() == null ? "" : findHouseholds.getReligion())
                        .mobileNumber(familyMember.getMobileNumber() == null ? "" : familyMember.getMobileNumber())
                        .isMinority(findHouseholds.getIsMinority() == null ? "" : findHouseholds.getIsMinority())
                        .icdsService(findHouseholds.getIcdsService() == null ? "" : findHouseholds.getIcdsService())
                        .totalMembers("")
                        .headName("")
                        .headGender("")
                        .headDob("")
                        .headPic("")
                        .build();

                addInList.add(addSingle);

            }

        }


        if (findChildren.size() > 0) {

            for (FamilyMember fc : findChildren) {
                Family findHouseholds = familyRepository.findByFamilyId(fc.getFamilyId());
                BeneficiaryList addSingle = BeneficiaryList.builder()
                        .category(fc.getCategory() == null ? "" : fc.getCategory())
                        .name(fc.getName() == null ? "" : fc.getName())
                        .id(fc.getId() == null ? "" : fc.getId())
                        .dob(df.format(new Date(fc.getDob())))
                        .centerName(fc.getCenterName() == null ? "" : fc.getCenterName())
                        .uniqueCode(fc.getMemberCode() == null ? "" : fc.getMemberCode())
                        .houseNo(findHouseholds.getHouseNo() == null ? "" : findHouseholds.getHouseNo())
                        .centerId(fc.getCenterId() == null ? "" : fc.getCenterId())
                        .uniqueIdType(fc.getIdType() == null ? "" : fc.getIdType())
                        .uniqueId(fc.getIdNumber() == null ? "" : fc.getIdNumber())
                        .religion(findHouseholds.getReligion() == null ? "" : findHouseholds.getReligion())
                        .mobileNumber(fc.getMobileNumber() == null ? "" : fc.getMobileNumber())
                        .isMinority(findHouseholds.getIsMinority() == null ? "" : findHouseholds.getIsMinority())
                        .icdsService(findHouseholds.getIcdsService() == null ? "" : findHouseholds.getIcdsService())
                        .totalMembers("")
                        .headName("")
                        .headGender("")
                        .headDob("")
                        .headPic("")
                        .build();

                addInList.add(addSingle);

            }
        }

        return addInList;
    }


    @Override
    public List<MemberVisits> getMemberVisitDetails(String memberId, String centerName) {

        List<Visits> findMember = visitsRepository.findAllByMemberIdAndCenterName(memberId, centerName);
        List<MemberVisits> addInList = new ArrayList<>();

        HashSet<String> uniqueMember = new HashSet<>();
        for (Visits checkDetails : findMember) {

            if (uniqueMember.add(checkDetails.getVisitType())) {
                MemberVisits memberVisits = MemberVisits.builder()
                        .visitType(checkDetails.getVisitType())
                        .visitArray(visitArrayList(checkDetails.getMemberId(), checkDetails.getVisitType()))
                        .build();

                addInList.add(memberVisits);

            }
        }

        return addInList;
    }

}
