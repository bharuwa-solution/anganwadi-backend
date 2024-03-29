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
    private final BloodTestTrackingRepository bloodTestTrackingRepository;
    private final VaccinationNameRepository vaccinationNameRepository;
    private final StockDistributionRepository stockDistributionRepository;

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository, ModelMapper modelMapper,
                             FamilyMemberRepository familyMemberRepository, VisitsRepository visitsRepository,
                             WeightTrackingRepository weightTrackingRepository, VaccinationRepository vaccinationRepository,
                             PregnantAndDeliveryRepository pregnantAndDeliveryRepository, BabiesBirthRepository babiesBirthRepository,
                             AnganwadiChildrenRepository anganwadiChildrenRepository,
                             AnganwadiCenterRepository anganwadiCenterRepository, UserRepository userRepository,
                             AttendanceRepository attendanceRepository, CommonMethodsService commonMethodsService,
                             VaccinationScheduleRepository vaccinationScheduleRepository,
                             HouseVisitScheduleRepository houseVisitScheduleRepository,
                             BloodTestTrackingRepository bloodTestTrackingRepository,
                             VaccinationNameRepository vaccinationNameRepository, StockDistributionRepository stockDistributionRepository
    ) {
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
        this.bloodTestTrackingRepository = bloodTestTrackingRepository;
        this.vaccinationNameRepository = vaccinationNameRepository;
        this.stockDistributionRepository = stockDistributionRepository;
    }

    public static long getSixYearsAgoMillis(long currentTimeMillis) {
        // Convert the provided time in milliseconds to a LocalDate
        LocalDate currentDate = Instant.ofEpochMilli(currentTimeMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Calculate the date 6 months ago
        LocalDate sixYearsAgoDate = currentDate.minusYears(6);

        // Convert the resulting date back to milliseconds
        return sixYearsAgoDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public static long getSixMonthsAgoMillis(long currentTimeMillis) {
        // Convert the provided time in milliseconds to a LocalDate
        LocalDate currentDate = Instant.ofEpochMilli(currentTimeMillis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Calculate the date 6 months ago
        LocalDate sixMonthsAgoDate = currentDate.minusMonths(6);

        // Convert the resulting date back to milliseconds
        return sixMonthsAgoDate
                .atStartOfDay(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }

    public int totalHouseholdsMale(String familyId) {
        int totalMale = 0;
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date parseTime = df.parse(convertToString);

        List<FamilyMember> checkFamily = familyMemberRepository.findAllByFamilyId(familyId,
                Sort.by(Sort.Direction.DESC, "createdDate"));
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

        List<FamilyMember> checkFamily = familyMemberRepository.findAllByFamilyId(familyId,
                Sort.by(Sort.Direction.DESC, "createdDate"));
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

        List<FamilyMember> checkFamily = familyMemberRepository.findAllByFamilyId(familyId,
                Sort.by(Sort.Direction.DESC, "createdDate"));
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
        List<Family> familyList = familyRepository.findAllByCenterIdAndIsActiveTrueAndDeletedFalse(centerId,
                Sort.by(Sort.Direction.DESC, "createdDate"));

        // Get Head of Family Details

        for (Family getHouseholds : familyList) {
            String headName = "", dob = "", pic = "", gender = "", headId = "";
            int male = 0, female = 0, children = 0;
            if (!getHouseholds.isDeleted()) {

                List<FamilyMember> findHeadDetails = familyMemberRepository.findAllByFamilyId(
                        getHouseholds.getFamilyId().trim(), Sort.by(Sort.Direction.DESC, "createdDate"));
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
                householdsHeadList householdsDTO = householdsHeadList.builder().id(getHouseholds.getId())
                        .headName(headName).headDob(dob).houseNo(getHouseholds.getHouseNo())
                        .familyId(getHouseholds.getFamilyId()).religion(getHouseholds.getReligion()).headGender(gender)
                        .totalMale(totalHouseholdsMale(getHouseholds.getFamilyId()))
                        .totalFemale(totalHouseholdsFemale(getHouseholds.getFamilyId()))
                        .totalChildren(totalHouseholdsChildren(getHouseholds.getFamilyId()))
                        .category(getHouseholds.getCategory()).totalMembers(String.valueOf(countMembers)).headPic(pic)
                        .build();
                addInList.add(householdsDTO);
            }
        }

        return addInList;
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
    public HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO, String centerId)
            throws ParseException {

        String name = householdsDTO.getHeadName() == null ? "" : householdsDTO.getHeadName();
        String headDob = householdsDTO.getHeadDob() == null ? "" : householdsDTO.getHeadDob();
        String centerID = centerId == null ? "" : centerId;
        String centerNames = commonMethodsService.findCenterName(centerID);
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

        // commonMethodsService.findCenterName(centerID);

        long currentTime = System.currentTimeMillis();
        String familyId = ApplicationConstants.familyId + currentTime;

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse(householdsDTO.getHeadDob());
        long mills = date.getTime();

        Date currentMonth = new Date();
        String[] splitMonth = df.format(currentMonth).split("-");
        String getMonth = splitMonth[1].replace("0", "");

        Family saveFamily = Family.builder().houseNo(houseNo).familyId(familyId).centerName(centerNames)
                .centerId(centerID).category(category).religion(religion).isMinority(isMinority)
                .icdsService(icdsService).build();
        familyRepository.save(saveFamily);

        FamilyMember saveInMember = FamilyMember.builder().name(name).dob(mills).centerId(centerID)
                .centerName(centerNames).maritalStatus("1").familyId(familyId).idNumber(uniqueId).idType(uniqueIdType)
                .dateOfMortality("").dateOfLeaving("").dateOfArrival("").motherName("").fatherName("").handicapType("")
                .handicap("").memberCode("").residentArea("").stateCode("").recordForMonth(getMonth)
                .mobileNumber(mobileNo).category(category).photo(headPic).relationWithOwner("0").gender(headGender)
                .build();

        familyMemberRepository.save(saveInMember);

        return HouseholdsDTO.builder().id(saveInMember.getId()).headName(name).centerName(centerNames).headDob(headDob)
                .uniqueCode(uniqueCode).uniqueId(uniqueId).totalMembers("").headPic(headPic).centerId(centerID)
                .headGender(headGender).houseNo(houseNo).mobileNumber(mobileNo).uniqueIdType(uniqueIdType)
                .uniqueId(uniqueId).category(category).religion(religion).isMinority(isMinority)
                .icdsService(icdsService).build();
    }

    @Override
    public FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO, String centerId)
            throws ParseException {

        String name = familyMemberDTO.getName() == null ? "" : familyMemberDTO.getName();
        String relationShip = familyMemberDTO.getRelationWithOwner() == null ? ""
                : familyMemberDTO.getRelationWithOwner();
        String gender = familyMemberDTO.getGender() == null ? "" : familyMemberDTO.getGender();
        String dob = familyMemberDTO.getDob() == null ? "" : familyMemberDTO.getDob();
//		log.error("dob from frontend: " + dob);
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


//		log.info("death Date " + deathDate);
//		log.info("centerName " + centerName);

        if (deathDate.length() > 0) {
            throw new CustomException("Can't Add Death Date While Creating New Members");
        }

        commonMethodsService.findCenterName(centerId);

        Date date = ApplicationConstants.df.parse(familyMemberDTO.getDob());
        long mills = date.getTime();

        log.error("date in millis is :  " + mills);
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
        String[] splitMonth = ApplicationConstants.df.format(currentMonth).split("-");
        String getMonth = splitMonth[1].replace("0", "");

        FamilyMember saveMember = FamilyMember.builder().name(name).relationWithOwner(relationShip).gender(gender)
                .familyId(familyMemberDTO.getFamilyId())
                .mobileNumber(familyMemberDTO.getMobileNumber() == null ? "" : familyMemberDTO.getMobileNumber())
                .idType(familyMemberDTO.getIdType() == null ? "" : familyMemberDTO.getIdType())
                .idNumber(familyMemberDTO.getIdNumber() == null ? "" : familyMemberDTO.getIdNumber()).dob(mills)
                .recordForMonth(getMonth).centerId(centerId).centerName(commonMethodsService.findCenterName(centerId)).motherName(motherName)
                .fatherName(fatherName).memberCode(memberCode).memberCode(code)
                .category(category.equals("") ? headCategory : category).maritalStatus(martialStatus)
                .stateCode(stateCode).handicap(handicap).handicapType(handicapType).residentArea(area)
                .dateOfArrival(arrivalDate).dateOfLeaving(leavingDate)
                .dateOfMortality(deathDate).photo(photo)
                .isRegistered(isRegistered).build();

//		if (deathDate.length() > 0) {
//			saveMember.setDeleted(true);
//			saveMember.setActive(false);
//		}

        familyMemberRepository.save(saveMember);

        Date formatDate = new Date(mills);

        return FamilyMemberDTO.builder().id(saveMember.getId()).name(name).relationWithOwner(relationShip)
                .gender(gender).centerName(commonMethodsService.findCenterName(centerId)).memberCode(code).familyId(familyMemberDTO.getFamilyId())
                .mobileNumber(familyMemberDTO.getMobileNumber() == null ? "" : familyMemberDTO.getMobileNumber())
                .idType(familyMemberDTO.getIdType() == null ? "" : familyMemberDTO.getIdType())
                .idNumber(familyMemberDTO.getIdNumber() == null ? "" : familyMemberDTO.getIdNumber())
                .dob(ApplicationConstants.df.format(formatDate)).maritalStatus(martialStatus).stateCode(stateCode).handicap(handicap)
                .handicapType(handicapType).motherName(motherName).fatherName(fatherName).memberCode(memberCode)
                .residentArea(area).dateOfArrival(arrivalDate).dateOfLeaving(leavingDate)
                .dateOfMortality(deathDate)
                .photo(photo).isRegistered(isRegistered).build();
    }

//    private void updateAfterBirthSchedule(Visits visits) {
//
//        List<HouseVisitSchedule> hs = houseVisitScheduleRepository.findAllByMemberIdAndVisitType(visits.getMemberId(),visits.getVisitType());
//        List<HouseVisitSchedule> findMember = new ArrayList<>();
//
//        if (hs.size() > 0 && Integer.parseInt(visits.getVisitRound()) < 3) {
//            switch (visits.getVisitType()) {
//
//                case "4":
//                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_1", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//                case "5":
//                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_2", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//                case "6":
//                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_3", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//                case "7":
//                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_4", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//                case "8":
//                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_5", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//                case "9":
//                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_6", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//                case "10":
//                    findMember = houseVisitScheduleRepository.findAllByVisitTypeAndMemberIdAndVisitRound("BIRTH_VISIT_7", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//            }
//        } else {
//            HouseVisitSchedule addVisit_1 = HouseVisitSchedule.builder()
//                    .visitType(selectVisitType(visits.getVisitFor()))
//                    .visitRound(visits.getVisitRound())
//                    .comments(visits.getDescription())
//                    .latitude(visits.getLatitude())
//                    .centerName(visits.getCenterName())
//                    .centerId(visits.getCenterId())
//                    .longitude(visits.getLongitude())
//                    .visitName(selectVisitName(visits.getVisitFor()))
//                    .memberId(visits.getMemberId())
//                    .visitDate(visits.getVisitDateTime())
//                    .dueDate(visits.getVisitDateTime())
//                    .build();
//
//            houseVisitScheduleRepository.save(addVisit_1);
//        }
//    }

//    private void updateBeforeBirthSchedule(Visits visits) {
//        List<HouseVisitSchedule> hs = houseVisitScheduleRepository.findAllByMemberId(visits.getMemberId());
//        List<HouseVisitSchedule> findMember = new ArrayList<>();
//
//        if (hs.size() > 0 && Integer.parseInt(visits.getVisitRound()) < 3) {
//
//            switch (visits.getVisitType()) {
//
//                case "1":
//                    findMember = houseVisitScheduleRepository.findAllByVisitNameAndMemberIdAndVisitRound("TILL_04_06_MONTHS", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//
//                case "2":
//                    findMember = houseVisitScheduleRepository.findAllByVisitNameAndMemberIdAndVisitRound("TILL_07_09_MONTHS", visits.getMemberId(), visits.getVisitRound());
//                    if (findMember.size() > 0) {
//                        for (HouseVisitSchedule updateVisits : findMember) {
//                            updateVisits.setVisitDate(visits.getVisitDateTime());
//                            updateVisits.setLongitude(visits.getLongitude());
//                            updateVisits.setLatitude(visits.getLatitude());
//                            updateVisits.setComments(visits.getDescription());
//                            houseVisitScheduleRepository.save(updateVisits);
//                        }
//                    }
//                    break;
//            }
//
//        } else {
//            HouseVisitSchedule addVisit_1 = HouseVisitSchedule.builder()
//                    .visitType(selectVisitType(visits.getVisitFor()))
//                    .visitRound(visits.getVisitRound())
//                    .comments(visits.getDescription())
//                    .latitude(visits.getLatitude())
//                    .longitude(visits.getLongitude())
//                    .centerName(visits.getCenterName())
//                    .centerId(visits.getCenterId())
//                    .visitName(selectVisitName(visits.getVisitFor()))
//                    .memberId(visits.getMemberId())
//                    .visitDate(visits.getVisitDateTime())
//                    .dueDate(visits.getVisitDateTime())
//                    .build();
//
//            houseVisitScheduleRepository.save(addVisit_1);
//        }
//    }

    @Override
    public List<FamilyMemberDTO> getFamilyMembers(String familyId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }

        Family checkDeleted = familyRepository.findByFamilyId(familyId);

        if (checkDeleted.isDeleted() && !checkDeleted.isActive()) {
            throw new CustomException("The Family Is Deleted, Please Check With Support Team, to Re-activate It!!");
        }

        List<FamilyMemberDTO> addInList = new ArrayList<>();
        List<FamilyMember> getMembers = familyMemberRepository.findAllByFamilyId(familyId,
                Sort.by(Sort.Direction.ASC, "createdDate"));

        for (FamilyMember passDetails : getMembers) {

            long getMills = passDetails.getDob();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);

            FamilyMemberDTO singleList = FamilyMemberDTO.builder().id(passDetails.getId())
                    .name(passDetails.getName() == null ? "" : passDetails.getName())
                    .photo(passDetails.getPhoto() == null ? "" : passDetails.getPhoto())
                    .category(passDetails.getCategory() == null ? "" : passDetails.getCategory())
                    .familyId(passDetails.getFamilyId() == null ? "" : passDetails.getFamilyId())
                    .mobileNumber(passDetails.getMobileNumber() == null ? "" : passDetails.getMobileNumber())
                    .relationWithOwner(
                            passDetails.getRelationWithOwner() == null ? "" : passDetails.getRelationWithOwner())
                    .idType(passDetails.getIdType() == null ? "" : passDetails.getIdType())
                    .memberCode(passDetails.getMemberCode() == null ? "" : passDetails.getMemberCode())
                    .motherName(passDetails.getMotherName() == null ? "" : passDetails.getMotherName())
                    .fatherName(passDetails.getFatherName() == null ? "" : passDetails.getFatherName())
                    .idNumber(passDetails.getIdNumber() == null ? "" : passDetails.getIdNumber())
                    .gender(passDetails.getGender() == null ? "" : passDetails.getGender()).dob(df.format(date))
                    .centerName(commonMethodsService.findCenterName(passDetails.getCenterId()))
                    .category(passDetails.getCategory()) // join
                    .idType(passDetails.getIdType()).idNumber(passDetails.getIdNumber())
                    .maritalStatus(passDetails.getMaritalStatus() == null ? "" : passDetails.getMaritalStatus())
                    .stateCode(passDetails.getStateCode() == null ? "" : passDetails.getStateCode())
                    .handicap(passDetails.getHandicap() == null ? "" : passDetails.getHandicap())
                    .handicapType(passDetails.getHandicapType() == null ? "" : passDetails.getHandicapType())
                    .residentArea(passDetails.getResidentArea() == null ? "" : passDetails.getResidentArea())
                    .dateOfArrival(passDetails.getDateOfArrival() == null ? "" : passDetails.getDateOfArrival())
                    .dateOfLeaving(passDetails.getDateOfLeaving() == null ? "" : passDetails.getDateOfLeaving())
                    .dateOfMortality(passDetails.getDateOfMortality() == null ? "" : passDetails.getDateOfMortality())
                    .build();
            addInList.add(singleList);
        }
        return addInList;
    }

    private void UpdateVaccinationTable(Visits visits) {
        List<VaccinationSchedule> scheduleList = vaccinationScheduleRepository
                .findAllByMemberIdAndCode(visits.getMemberId().trim(), visits.getVisitFor().trim());
        if (scheduleList.size() > 0) {
            for (VaccinationSchedule vs : scheduleList) {
                vs.setVaccinationDate(visits.getVisitDateTime());
                vaccinationScheduleRepository.save(vs);
            }
        }
    }

    @Override
    public VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO, String centerId, String centerName)
            throws ParseException {

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
                .familyId(findCat.getFamilyId() == null ? "" : findCat.getFamilyId()).centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .visitType(visitsDetailsDTO.getVisitType() == null ? "" : visitsDetailsDTO.getVisitType())
                .visitFor(visitsDetailsDTO.getVisitFor() == null ? "" : visitsDetailsDTO.getVisitFor())
                .description(visitsDetailsDTO.getDescription() == null ? "" : visitsDetailsDTO.getDescription())
                .visitRound(visitsDetailsDTO.getVisitRound() == null ? "" : visitsDetailsDTO.getVisitRound())
                .latitude(visitsDetailsDTO.getLatitude() == null ? "" : visitsDetailsDTO.getLatitude())
                .longitude(visitsDetailsDTO.getLongitude() == null ? "" : visitsDetailsDTO.getLongitude())
                .visitDateTime(millis).category(findCat.getCategory() == null ? "" : findCat.getCategory())
                .childDob(findCat.getDob()).build();
        visitsRepository.save(saveVisitDetails);

        // Save in Vaccination Table
        vaccinationRepository
                .save(Vaccination.builder().familyId(findCat.getFamilyId() == null ? "" : findCat.getFamilyId())
                        .childId(visitsDetailsDTO.getMemberId() == null ? "" : visitsDetailsDTO.getMemberId())
                        .centerId(centerId).centerName(centerName)
                        .vaccinationCode(visitsDetailsDTO.getVisitFor() == null ? "" : visitsDetailsDTO.getVisitFor())
                        .date(visitsDetailsDTO.getVaccinationDate()).build());

        // Update Vaccination & House Schedule Table

        if (Integer.parseInt(visitsDetailsDTO.getVisitType()) > 3) {
//
//             Update in House visit Schedule
//           updateAfterBirthSchedule(saveVisitDetails);
//
//             Update in Vaccination visit Schedule
            UpdateVaccinationTable(saveVisitDetails);
        }
//        if (Integer.parseInt(visitsDetailsDTO.getVisitType()) > 0 && Integer.parseInt(visitsDetailsDTO.getVisitType()) < 3) {
//
//           updateBeforeBirthSchedule(saveVisitDetails);
//
//
//        }

        return modelMapper.map(saveVisitDetails, VisitsDetailsDTO.class);
    }

    @Override
    public WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO, String centerId)
            throws ParseException {
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
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId()).centerId(centerId)
                .date(mills).centerName(commonMethodsService.findCenterName(centerId))
                .height(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .weight(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight()).build();

        weightTrackingRepository.save(saveRecord);

        Date dob = new Date(findDetails.getDob());

        return WeightRecordsDTO.builder().familyId(findDetails.getFamilyId() == null ? "" : findDetails.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .name(findDetails.getName() == null ? "" : findDetails.getName())
                .gender(findDetails.getGender() == null ? "" : findDetails.getGender())
                .motherName(findDetails.getMotherName() == null ? "" : findDetails.getMotherName()).dob(df.format(dob))
                .houseNo("").photo(findDetails.getPhoto() == null ? "" : weightRecordsDTO.getPhoto())
                .date(df.format(date)).centerName(commonMethodsService.findCenterName(centerId))
                .height(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .weight(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight()).build();
    }

    @Override
    public WeightRecordsDTO saveWeightRecordsCloned(WeightRecordsDTO weightRecordsDTO, String centerId)
            throws ParseException {

        Date date = new Date();
        String convertToString = ApplicationConstants.df.format(date);
        Date convertToDate = ApplicationConstants.df.parse(convertToString);
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
                .date(mills).centerName(commonMethodsService.findCenterName(centerId))
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight().replaceAll(" ", ""))
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight().replaceAll(" ", ""))
                .build();

        weightTrackingRepository.save(saveRecord);

        Date dob = new Date(findDetails.getDob());

        return WeightRecordsDTO.builder().familyId(findDetails.getFamilyId() == null ? "" : findDetails.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .name(findDetails.getName() == null ? "" : findDetails.getName())
                .gender(findDetails.getGender() == null ? "" : findDetails.getGender())
                .motherName(findDetails.getMotherName() == null ? "" : findDetails.getMotherName()).dob(ApplicationConstants.df.format(dob))
                .houseNo("").photo(findDetails.getPhoto() == null ? "" : weightRecordsDTO.getPhoto())
                .date(ApplicationConstants.df.format(date)).centerName(commonMethodsService.findCenterName(centerId))
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight().replaceAll(" ", ""))
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight().replaceAll(" ", ""))
                .build();
    }

    @Override
    public List<HouseholdRelCatData> getReligionCategoryData(DashboardFilter dashboardFilter) throws ParseException {


        List<HouseholdRelCatData> addInList = new ArrayList<>();
        Set<String> uniquePdd = new HashSet<>();
        Set<String> uniqueDharti = new HashSet<>();
        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = ApplicationConstants.df.parse(dashboardFilter.getStartDate().trim());

        } else {
            startTime = ApplicationConstants.df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = ApplicationConstants.df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth());
        }

        // Beneficiary Children
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        /*
         * LocalDateTime dateFor3Years = LocalDateTime.now().minusYears(3);
         * ZonedDateTime zdt2 = ZonedDateTime.of(dateFor3Years,ZoneId.systemDefault());
         * long date3MonthsBack = zdt2.toInstant().toEpochMilli();
         */

        // Beneficiary dharti
        LocalDateTime dhartiDate = LocalDateTime.now().minusMonths(6);
        ZonedDateTime dhartiZdt = ZonedDateTime.of(dhartiDate, ZoneId.systemDefault());
        long convertToMills2 = dhartiZdt.toInstant().toEpochMilli();

        List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository.findAllByPregnancyCriteria(startTime.getTime(),
                endTime.getTime(), dashboardFilter.getCenterId().trim());


        List<PregnantAndDelivery> dhartiWomen = pregnantAndDeliveryRepository.findAllBeneficiaryDharti(
                startTime.getTime(), endTime.getTime(), dashboardFilter.getCenterId().trim(), convertToMills2);


        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<FamilyMember> findChildren = familyMemberRepository.findAllBeneficiaryChildren(startTime, endTime,
                dashboardFilter.getCenterId().trim(), convertToMills);


        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        // Adding Children
        for (FamilyMember fm : findChildren) {
            Family findFamily = familyRepository.findByFamilyId(fm.getFamilyId());

            HouseholdRelCatData singleEntry_1 = HouseholdRelCatData.builder()
                    .category(fm.getCategory() == null ? "" : fm.getCategory())
                    .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                    .name(fm.getName() == null ? "" : fm.getName())
                    .dob(ApplicationConstants.df.format(new Date(fm.getDob())))
                    .centerId(fm.getCenterId() == null ? "" : fm.getCenterId())
                    .centerName(fm.getCenterName() == null ? "" : fm.getCenterName())
                    .gender(fm.getGender() == null ? "" : fm.getGender())
                    .startDate(ApplicationConstants.df.format(startTime))
                    .endDate(ApplicationConstants.df.format(endTime)).build();

            addInList.add(singleEntry_1);

        }

        // Adding Dharti
        for (PregnantAndDelivery dharti : dhartiWomen) {
            if (uniqueDharti.add(dharti.getMotherMemberId())) {
                Family findFamily = familyRepository.findByFamilyId(dharti.getFamilyId());
                FamilyMember member = familyMemberRepository.findById(dharti.getMotherMemberId()).get();

                HouseholdRelCatData singleEntry_2 = HouseholdRelCatData.builder()
                        .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                        .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                        .name(member.getName() == null ? "" : member.getName()).dob(ApplicationConstants.df.format(new Date(member.getDob())))
                        .centerId(member.getCenterId() == null ? "" : member.getCenterId())
                        .centerName(member.getCenterName() == null ? "" : member.getCenterName())
                        .gender(member.getGender() == null ? "" : member.getGender()).startDate(ApplicationConstants.df.format(startTime))
                        .endDate(ApplicationConstants.df.format(endTime)).build();

                addInList.add(singleEntry_2);

            }
        }

        // Adding Pregnant
        for (PregnantAndDelivery pregnant : pdd) {
            if (pregnant.getRegDate() <= endTime.getTime() && uniquePdd.add(pregnant.getMotherMemberId().trim())) {
                Family findFamily = familyRepository.findByFamilyId(pregnant.getFamilyId());
//				log.error("id : "+pregnant.getMotherMemberId());
                FamilyMember member = familyMemberRepository.findById(pregnant.getMotherMemberId()).get();

                HouseholdRelCatData singleEntry_3 = HouseholdRelCatData.builder()
                        .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                        .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                        .name(member.getName() == null ? "" : member.getName())
                        .dob(ApplicationConstants.df.format(new Date(member.getDob())))
                        .centerId(member.getCenterId() == null ? "" : member.getCenterId())
                        .centerName(member.getCenterName() == null ? "" : member.getCenterName())
                        .gender(member.getGender() == null ? "" : member.getGender())
                        .startDate(ApplicationConstants.df.format(startTime))
                        .endDate(ApplicationConstants.df.format(endTime)).build();

                addInList.add(singleEntry_3);
            }
        }

        log.error("findChildren :" + findChildren.size());
        log.error("pdd :" + uniquePdd.size());
        log.error("dhartiWomen :" + uniqueDharti.size());
        log.error("addInList Size " + addInList.size());

        return addInList;
    }

    private List<VaccinationList> getVaccinationList(long dates, String id,
                                                     List<VaccinationSchedule> vaccinationSchedule) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<VaccinationList> addVaccinationList = new ArrayList<>();

        for (VaccinationSchedule vss : vaccinationSchedule) {
            if (vss.getDueDate() == dates && vss.getMemberId().equals(id)) {
                String vaccinationDate = "";
                if (vss.getVaccinationDate() > 0) {
                    vaccinationDate = df.format(vss.getVaccinationDate());
                }

                VaccinationList singleVaccination = VaccinationList.builder().name(vss.getVaccinationName())
                        .code(vss.getCode()).dueDate(df.format(vss.getDueDate())).vaccinatedDate(vaccinationDate)
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

        if (houseVisit.size() > 0) {
            for (HouseVisitSchedule hv : houseVisit) {

                if (hv.getDueDate() == dates && hv.getVisitType().equals("3")) {
                    List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository
                            .findAllByMotherMemberId(hv.getMemberId(), Sort.by(Sort.Direction.ASC, "createdDate"));
                    for (PregnantAndDelivery lmpDates : pdd) {
                        LMPDate = lmpDates.getLastMissedPeriodDate();
                        actualDelivery = lmpDates.getDateOfDelivery();
                    }
                    delivery = DeliveryList.builder()
                            .LMPDate(df.format(LMPDate).length() == 0 ? "" : df.format(LMPDate))
                            .exceptedDeliveryDate(df.format(hv.getDueDate()))
                            .actualDeliveryDate(df.format(actualDelivery)).build();
                } else {
                    delivery = DeliveryList.builder().LMPDate("").exceptedDeliveryDate("").actualDeliveryDate("")
                            .build();
                }

            }
        } else {
            delivery = DeliveryList.builder().LMPDate("").exceptedDeliveryDate("").actualDeliveryDate("").build();
        }

        return delivery;
    }

    private List<HouseVisitRounds> getRounds(String id, String visitType) {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<HouseVisitRounds> addVisitRounds = new ArrayList<>();
        Set<String> uniqueVisitType = new TreeSet<>();

        List<Visits> findRounds = visitsRepository.findAllByMemberIdAndVisitType(id, visitType);
        for (Visits checkRounds : findRounds) {
            if (uniqueVisitType.add(checkRounds.getVisitType().trim())) {
                HouseVisitRounds addSingleRound = HouseVisitRounds.builder()
                        .visitDate(df.format(checkRounds.getVisitDateTime()))
                        .comments(checkRounds.getDescription() == null ? "" : checkRounds.getDescription())
                        .latitude(checkRounds.getLatitude() == null ? "" : checkRounds.getLatitude())
                        .longitude(checkRounds.getLongitude() == null ? "" : checkRounds.getLongitude()).build();

                addVisitRounds.add(addSingleRound);
            }

        }

        return addVisitRounds;
    }

    private List<HouseVisitsList> getHouseVisitList(long dates, String id, List<HouseVisitSchedule> houseVisit) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        List<HouseVisitsList> addHouseList = new ArrayList<>();
        Set<String> uniqueType = new TreeSet<>();

        for (HouseVisitSchedule hvv : houseVisit) {
            if (hvv.getDueDate() == dates && hvv.getMemberId().equals(id)) {
                String visitDate = "";
                if (uniqueType.add(hvv.getVisitType().trim())) {
                    addHouseList.add(HouseVisitsList.builder().visitType(hvv.getVisitType()).title(hvv.getVisitName())
                            .round(getRounds(id, hvv.getVisitType())).dueDate(df.format(hvv.getDueDate())).build());
                }
            }
        }
        return addHouseList;
    }

    private List<MemberDetails> getMembersDetails(long dates, List<VaccinationSchedule> vs,
                                                  List<HouseVisitSchedule> hs) {
        List<MemberDetails> addInList = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
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

                MemberDetails member = MemberDetails.builder().id(findMember.getId())
                        .dob(df.format(findMember.getDob()))
                        .fatherHusbandName(findMember.getFatherName() == null ? "" : findMember.getFatherName())
                        .motherName(findMember.getMotherName() == null ? "" : findMember.getMotherName())
                        .name(findMember.getName() == null ? "" : findMember.getName())
                        .centerName(commonMethodsService.findCenterName(findMember.getCenterId()))
                        .visits(visitRounds(findMember.getId())).category(findFamily.getCategory())
                        .religion(findFamily.getReligion())
                        .profilePic(findMember.getPhoto() == null ? "" : findMember.getPhoto())
                        .houseNo(findFamily.getHouseNo()).gender(findMember.getGender())
                        .vaccination(getVaccinationList(dates, id, vs)).delivery(getDeliveryList(dates, hs))
                        .houseVisits(getHouseVisitList(dates, id, hs)).build();
                addInList.add(member);
            }

        }

        return addInList;
    }

    @Override
    public List<VaccinationScheduleDTO> getVaccinationSchedule(@RequestBody DashboardFilter dashboardFilter)
            throws ParseException {
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

        List<VaccinationSchedule> vaccinationScheduleList = vaccinationScheduleRepository.findAllByDateRange(startTime,
                endTime, dashboardFilter.getCenterId());
        List<HouseVisitSchedule> houseVisitScheduleList = houseVisitScheduleRepository.findAllByDateRange(startTime,
                endTime, dashboardFilter.getCenterId());

        for (VaccinationSchedule vs : vaccinationScheduleList) {
            uniqueDate.add(vs.getDueDate());
        }

        for (HouseVisitSchedule hs : houseVisitScheduleList) {
            uniqueDate.add(hs.getDueDate());
        }

        for (long sd : uniqueDate) {
            VaccinationScheduleDTO addSingle = VaccinationScheduleDTO.builder().date(df.format(sd))
                    .members(getMembersDetails(sd, vaccinationScheduleList, houseVisitScheduleList)).build();

            addInList.add(addSingle);
        }
        return addInList;
    }

    private List<VaccinationList> getVaccinationListByMemberId(String id,
                                                               List<VaccinationSchedule> vaccinationScheduleList) {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        List<VaccinationList> addVaccinationList = new ArrayList<>();

        for (VaccinationSchedule vss : vaccinationScheduleList) {
            if (vss.getMemberId().equals(id)) {
                String vaccinationDate = "";
                if (vss.getVaccinationDate() > 0) {
                    vaccinationDate = df.format(vss.getVaccinationDate());
                }

                VaccinationList singleVaccination = VaccinationList.builder().name(vss.getVaccinationName())
                        .code(vss.getCode()).dueDate(df.format(vss.getDueDate())).vaccinatedDate(vaccinationDate)
                        .build();
                addVaccinationList.add(singleVaccination);
            }
        }

        return addVaccinationList;
    }

    private DeliveryList getDeliveryListByMemberId(String sd, List<HouseVisitSchedule> houseVisitScheduleList) {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        long LMPDate = 0, actualDelivery = 0;
        DeliveryList delivery = new DeliveryList();

        if (houseVisitScheduleList.size() > 0) {
            for (HouseVisitSchedule hv : houseVisitScheduleList) {

                if (hv.getVisitType().equals("3")) {
                    List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository
                            .findAllByMotherMemberId(hv.getMemberId(), Sort.by(Sort.Direction.ASC, "createdDate"));
                    for (PregnantAndDelivery lmpDates : pdd) {
                        LMPDate = lmpDates.getLastMissedPeriodDate();
                        actualDelivery = lmpDates.getDateOfDelivery();
                    }
                    delivery = DeliveryList.builder()
                            .LMPDate(df.format(LMPDate).length() == 0 ? "" : df.format(LMPDate))
                            .exceptedDeliveryDate(df.format(hv.getDueDate()))
                            .actualDeliveryDate(df.format(actualDelivery)).build();
                } else {
                    delivery = DeliveryList.builder().LMPDate("").exceptedDeliveryDate("").actualDeliveryDate("")
                            .build();
                }

            }
        } else {
            delivery = DeliveryList.builder().LMPDate("").exceptedDeliveryDate("").actualDeliveryDate("").build();
        }

        return delivery;
    }

    private List<HouseVisitsList> getHouseVisitListByMemberId(String sd,
                                                              List<HouseVisitSchedule> houseVisitScheduleList) {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        List<HouseVisitsList> addHouseList = new ArrayList<>();
        Set<String> uniqueType = new TreeSet<>();

        for (HouseVisitSchedule hvv : houseVisitScheduleList) {
            if (hvv.getMemberId().equals(sd)) {
                String visitDate = "";
                if (uniqueType.add(hvv.getVisitType().trim())) {
                    addHouseList.add(HouseVisitsList.builder().visitType(hvv.getVisitType()).title(hvv.getVisitName())
                            .round(getRounds(sd, hvv.getVisitType())).dueDate(df.format(hvv.getDueDate())).build());
                }
            }

        }

        return addHouseList;
    }

    private RecentVisits getRecentVisit(String memberId) {

        List<Visits> visits = visitsRepository.findAllByMotherIdOrMemberId(memberId, Sort.by(Sort.Direction.DESC, "createdDate"));

        Set<String> recent = new HashSet<>();
        RecentVisits singleEntry = new RecentVisits();

        if (visits.size() > 0) {
            for (Visits v : visits) {
                if (recent.add(v.getMemberId())) {
                    singleEntry = RecentVisits.builder()
                            .visitFor(v.getVisitFor() == null ? "" : v.getVisitFor())
                            .date(ApplicationConstants.df.format(v.getVisitDateTime()))
                            .visitCategory(v.getVisitCategory() == null ? "" : v.getVisitCategory())
                            .visitType(v.getVisitType() == null ? "" : v.getVisitType())
                            .build();
                }
            }
        } else {
            singleEntry = RecentVisits.builder()
                    .visitFor(ApplicationConstants.EmptyStringValue)
                    .date(ApplicationConstants.EmptyStringValue)
                    .visitCategory(ApplicationConstants.EmptyStringValue)
                    .visitType(ApplicationConstants.EmptyStringValue)
                    .build();
        }
        return singleEntry;
    }

    @Override
    public List<MemberDetails> getVisitScheduler(DashboardFilter dashboardFilter) throws ParseException {
        List<MemberDetails> addInList = new ArrayList<>();

        Set<String> uniqueDate = new TreeSet<>();

        long startTime = 0, endTime = 0;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = ApplicationConstants.df.parse(dashboardFilter.getStartDate().trim()).getTime();

        } else {
            startTime = ApplicationConstants.df.parse(commonMethodsService.startDateOfMonth()).getTime();
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = ApplicationConstants.df.parse(dashboardFilter.getEndDate().trim()).getTime();
        } else {
            endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth()).getTime();
        }

        List<VaccinationSchedule> vaccinationScheduleList = vaccinationScheduleRepository.findAllByDateRange(startTime,
                endTime, dashboardFilter.getCenterId());
        List<HouseVisitSchedule> houseVisitScheduleList = houseVisitScheduleRepository.findAllByDateRange(startTime,
                endTime, dashboardFilter.getCenterId());

//        for (VaccinationSchedule vs : vaccinationScheduleList) {
//            uniqueDate.add(vs.getMemberId());
//        }

        for (HouseVisitSchedule hs : houseVisitScheduleList) {
            uniqueDate.add(hs.getMemberId());
        }

        for (String sd : uniqueDate) {

            FamilyMember memberDetails = commonMethodsService.findMember(sd);
            Family familyDetails = commonMethodsService.findFamily(memberDetails.getFamilyId());

            MemberDetails addSingle = MemberDetails.builder().id(memberDetails.getId())
                    .dob(commonMethodsService.dateChangeToString(memberDetails.getDob()))
                    .motherName(memberDetails.getMotherName()).fatherHusbandName(memberDetails.getFatherName())
                    .name(memberDetails.getName()).visits(visitRounds(sd)).centerName(memberDetails.getCenterName())
                    .houseNo(familyDetails.getHouseNo()).category(familyDetails.getCategory())
                    .religion(familyDetails.getReligion()).gender(memberDetails.getGender())
                    .profilePic(memberDetails.getPhoto())
                    .recentVisits(getRecentVisit(sd))
                    .vaccination(getVaccinationListByMemberId(sd, vaccinationScheduleList))
                    .delivery(getDeliveryListByMemberId(sd, houseVisitScheduleList))
                    .houseVisits(getHouseVisitListByMemberId(sd, houseVisitScheduleList)).build();

            addInList.add(addSingle);
        }
        return addInList;

    }

    private void saveBloodTestSection(VisitsDetailsDTOTemp bloodTestCases, String centerId,
                                      long currentDate, FamilyMember findFamily, String childId) throws ParseException {

        for (BloodTestCasesDTO cases : bloodTestCases.getBloodTest()) {
            bloodTestTrackingRepository.save(BloodTestTracking.builder().
                    date(currentDate)
                    .result(cases.getResult())
                    .visitRound(bloodTestCases.getVisitRound())
                    .visitType(bloodTestCases.getVisitType())
                    .motherId(findFamily.getId())
                    .familyId(findFamily.getFamilyId())
                    .memberId(childId)
                    .centerId(centerId)
                    .testCode(cases.getTestCode()).build());

            BloodTestCasesDTO singleBlood = modelMapper.map(cases, BloodTestCasesDTO.class);

        }
        saveVisitsSection(bloodTestCases, centerId, currentDate, findFamily, childId);
    }

    private void saveWeightsSection(VisitsDetailsDTOTemp weightRecords, String centerId, long currentDate,
                                    FamilyMember findFamily, String childId) {

        weightTrackingRepository.save(WeightTracking.builder()
                .familyId(findFamily.getFamilyId())
                .childId(childId)
                .motherId(findFamily.getId())
                .centerId(centerId)
                .visitRound(weightRecords.getVisitRound())
                .visitType(weightRecords.getVisitType())
                .centerName(commonMethodsService.findCenterName(centerId))
                .date(currentDate)
                .weight(weightRecords.getWeight())
                .height(weightRecords.getHeight()).build());

        saveVisitsSection(weightRecords, centerId, currentDate, findFamily, childId);
    }

    private void saveVisitsSection(VisitsDetailsDTOTemp visitDetails, String centerId, long currentDate,
                                   FamilyMember findFamily, String childId) {

        visitsRepository.save(Visits.builder()
                .familyId(findFamily.getFamilyId())
                .motherId(findFamily.getId())
                .memberId(childId)
                .visitCategory(visitDetails.getVisitCategory())
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .visitType(visitDetails.getVisitType())
                .visitFor(visitDetails.getVisitFor())
                .visitRound(visitDetails.getVisitRound())
                .description(visitDetails.getDescription())
                .visitDateTime(currentDate)
                .longitude(visitDetails.getLongitude())
                .latitude(visitDetails.getLatitude()).build());
    }

    private void saveVaccinationSection(VisitsDetailsDTOTemp visitDetails, String centerId, long currentDate,
                                        FamilyMember findFamily, String childId) {

        vaccinationRepository.save(Vaccination.builder()
                .familyId(findFamily.getFamilyId())
                .motherId(findFamily.getId())
                .childId(childId)
                .visitRound(visitDetails.getVisitRound())
                .visitType(visitDetails.getVisitType())
                .centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .vaccinationCode(visitDetails.getVisitFor())
                .date(currentDate).build());

        saveVisitsSection(visitDetails, centerId, currentDate, findFamily, childId);
    }

    private String findChildId(String childId) {
        List<BabiesBirth> checkMember = babiesBirthRepository.findAllByChildId(childId);
        String id = "";

        for (BabiesBirth visits : checkMember) {
            if (visits.getChildId().trim().length() > 0) {
                id = visits.getChildId();
                break;
            }
        }
        return id;
    }

    @Override
    public VisitsDetailsDTOTemp saveVisitsDetailsTemp(VisitsDetailsDTOTemp visitsDetailsDTOTemp, String centerId)
            throws ParseException {
        String visitType = visitsDetailsDTOTemp.getVisitType();
        String fetchMotherId = "", childId = "";

        // Fetch MotherId & childId

        fetchMotherId = getVisitMotherId(visitsDetailsDTOTemp.getMemberId());

        childId = findChildId(visitsDetailsDTOTemp.getMemberId());

        if (Arrays.asList(ApplicationConstants.noOfHouseVisits).contains(visitType.trim())) {
            if (visitsDetailsDTOTemp.getVisitCategory().length() > 0) {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

                long currentDate = df.parse(df.format(new Date())).getTime();

                if (!familyMemberRepository.findById(visitsDetailsDTOTemp.getMemberId()).isPresent()) {
                    throw new CustomException("Member Id Not Passed");
                }

                FamilyMember findMember = familyMemberRepository.findById(fetchMotherId).get();

                switch (visitsDetailsDTOTemp.getVisitCategory().trim()) {
                    case "1":
                        saveBloodTestSection(visitsDetailsDTOTemp, centerId, currentDate, findMember, childId);
                        break;

                    case "2":
                        saveWeightsSection(visitsDetailsDTOTemp, centerId, currentDate, findMember, childId);
                        break;

                    case "3":
                        saveVaccinationSection(visitsDetailsDTOTemp, centerId, currentDate, findMember, childId);
                        break;

                    case "4":
                        saveVisitsSection(visitsDetailsDTOTemp, centerId, currentDate, findMember, childId);
                        break;
                }

            } else {
                throw new CustomException("Visit Category Not Passed, Please Check");
            }
        } else {
            throw new CustomException("Visit Type does not exist, Connect with support team..");
        }
        return visitsDetailsDTOTemp;
    }

    private void checkPrematureVisits(String motherId, long babyDob) {
        // Deleting Future Mother's Visits, If Pre-Mature Delivery

        if (babyDob > 0) {
            List<HouseVisitSchedule> visitDetails = houseVisitScheduleRepository.findPrematureDelivery(motherId,
                    babyDob);
            if (visitDetails.size() > 0) {
                log.error("motherId " + motherId);
                log.error("Dob " + babyDob);
                for (HouseVisitSchedule visits : visitDetails) {
                    houseVisitScheduleRepository.deleteByMemberIdAndDueDate(motherId, visits.getDueDate());
                }
            }
        }
    }

    private void checkPrematureVaccination(String motherId, long babyDob) {
        // Deleting Future Mother's Visits, If Pre-Mature Delivery

        if (babyDob > 0) {
            List<VaccinationSchedule> visitDetails = vaccinationScheduleRepository.findPrematureDelivery(motherId,
                    babyDob);
            if (visitDetails.size() > 0) {
                log.error("motherId " + motherId);
                log.error("Dob " + babyDob);
                for (VaccinationSchedule visits : visitDetails) {
                    vaccinationScheduleRepository.deleteByMemberIdAndDueDate(motherId, visits.getDueDate());
                }
            }
        }
    }

    private void autoUpdateBirthVaccination(List<BabiesBirth> membersList) {

        Set<String> uniqueMember = new HashSet<>();
        for (BabiesBirth members : membersList) {
//			if (Arrays.stream(ApplicationConstants.ignoreCenters).noneMatch(members.getCenterId().trim()::equals)) {

            if (uniqueMember.add(members.getMotherMemberId())) {
                LocalDate localDate = Instant.ofEpochMilli(members.getDob()).atZone(ZoneId.systemDefault()).toLocalDate();

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-4")
                        .vaccinationName("OPV-0")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-5")
                        .vaccinationName("HEPATITIS-B")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(members.getDob())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-6")
                        .vaccinationName("B.C.G.")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(members.getDob())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-6")
                        .vaccinationName("B.C.G.")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-7")
                        .vaccinationName("OPV-1,2&3")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());


                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-7")
                        .vaccinationName("OPV-1,2&3")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-7")
                        .vaccinationName("OPV-1,2&3")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-8")
                        .vaccinationName("PENTAVALENT-1,2&3")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-8")
                        .vaccinationName("PENTAVALENT-1,2&3")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-8")
                        .vaccinationName("PENTAVALENT-1,2&3")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());


                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-9")
                        .vaccinationName("ROTAVIRUS")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-9")
                        .vaccinationName("ROTAVIRUS")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-9")
                        .vaccinationName("ROTAVIRUS")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-10")
                        .vaccinationName("FIPV")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-10")
                        .vaccinationName("FIPV")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-11")
                        .vaccinationName("PCV")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-11")
                        .vaccinationName("PCV")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-12")
                        .vaccinationName("MEASLES_RUBELLA_FIRST_DOSE")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-13")
                        .vaccinationName("PCV_BOOSTER")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-14")
                        .vaccinationName("VITAMIN_A_FIRST_DOSE")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-15")
                        .vaccinationName("DPT_BOOSTER_1")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());


                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-16")
                        .vaccinationName("MEASLES_RUBELLA_SECOND_DOSE")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-17")
                        .vaccinationName("OPV_BOOSTER")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-18")
                        .vaccinationName("VITAMIN_A")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(540).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-19")
                        .vaccinationName("DPT_BOOSTER_2")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(2190).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());

                vaccinationScheduleRepository.save(VaccinationSchedule.builder()
                        .code("V-20")
                        .vaccinationName("TD")
                        .vaccinationDate(0)
                        .centerId(members.getCenterId())
                        .centerName(commonMethodsService.findCenterName(members.getCenterId()))
                        .dueDate(localDate.plusDays(5840).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(members.getFamilyId())
                        .memberId(members.getChildId())
                        .build());


            }
//			}
        }
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
    public String autoUpdateCalendar() {

//        List<PregnantAndDelivery> findPdd = pregnantAndDeliveryRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));
        List<BabiesBirth> findBB = babiesBirthRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

        if (findBB.size() > 0) {
//             below is the temp Methods- can be deleted later
//            autoUpdatePregnantVisits(findPdd);
//            autoUpdatePregnancyVaccination(findPdd);
//            autoUpdateBirthVisits(findBB);
            autoUpdateBirthVaccination(findBB);
            return "data Updated";

        } else {
            return "No data Available to Update";
        }

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

    @Override
    public List<DhartiData> getDhartiWomenDetails(DashboardFilter dashboardFilter) throws ParseException {

        List<DhartiData> addInList = new ArrayList<>();
        HashSet<String> uniqueMemberId = new HashSet<>();

        long startTime = 0, endTime = 0;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = ApplicationConstants.df.parse(dashboardFilter.getStartDate().trim()).getTime();

        } else {
            startTime = ApplicationConstants.df.parse(commonMethodsService.startDateOfMonth()).getTime();
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = ApplicationConstants.df.parse(dashboardFilter.getEndDate().trim()).getTime();
        } else {
            endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth()).getTime();
        }

        // Beneficiary dharti
        Instant instant = Instant.ofEpochMilli(endTime);
        LocalDateTime dhartiDate = instant.atZone(ZoneId.systemDefault()).toLocalDateTime().minusMonths(6);
        ZonedDateTime dhartiZdt = ZonedDateTime.of(dhartiDate, ZoneId.systemDefault());
        long convertToMills2 = dhartiZdt.toInstant().toEpochMilli();

        List<PregnantAndDelivery> findPregnancyData = pregnantAndDeliveryRepository.findAllBeneficiaryDharti(startTime,
                endTime, dashboardFilter.getCenterId().trim(), convertToMills2);

        try {
            for (PregnantAndDelivery visits : findPregnancyData) {

                if (uniqueMemberId.add(visits.getMotherMemberId())) {
                    List<FamilyMember> searchNames = familyMemberRepository
                            .findAllByIdAndNameSearch(visits.getMotherMemberId(), dashboardFilter.getSearch().trim());

                    if (searchNames.size() > 0) {
                        for (FamilyMember searchResults : searchNames) {
                            Family households = familyRepository.findByFamilyId(searchResults.getFamilyId());

                            DhartiData addSingle = DhartiData.builder().name(searchResults.getName())
                                    .centerId(searchResults.getCenterId()).husbandName(searchResults.getFatherName())
                                    .dob(ApplicationConstants.df.format(searchResults.getDob())).motherId(visits.getMotherMemberId())
                                    .lastMissedPeriodDate(ApplicationConstants.df.format(visits.getLastMissedPeriodDate()))
                                    .category(searchResults.getCategory()).minority(households.getIsMinority())
                                    .religion(households.getReligion())
                                    .dateOfDelivery(ApplicationConstants.df.format(visits.getDateOfDelivery()))
                                    .startDate(dashboardFilter.getStartDate()).endDate(dashboardFilter.getEndDate())
                                    .duration("").build();

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

        List<WeightTracking> findChildRecords = weightTrackingRepository.findAllByChildId(childId,
                Sort.by(Sort.Direction.ASC, "createdDate"));
        FamilyMember findChildDetails = familyMemberRepository.findById(childId).get();
        List<WeightRecordsDTO> addList = new ArrayList<>();

        for (WeightTracking passRecords : findChildRecords) {

            long millis = passRecords.getDate();

            Date date = new Date(millis);

            WeightRecordsDTO getRecords = WeightRecordsDTO.builder()
                    .familyId(passRecords.getFamilyId() == null ? "" : passRecords.getFamilyId())
                    .childId(passRecords.getChildId() == null ? "" : passRecords.getChildId())
                    .name(findChildDetails.getName()).gender(findChildDetails.getGender()).houseNo("")
                    .motherName(findChildDetails.getMotherName()).dob(ApplicationConstants.df.format(findChildDetails.getDob()))
                    .photo(findChildDetails.getPhoto()).date(ApplicationConstants.df.format(date)).centerName(passRecords.getCenterName())
                    .weight(passRecords.getWeight() == null ? "" : passRecords.getWeight().replaceAll(" ", ""))
                    .height(passRecords.getHeight() == null ? "" : passRecords.getHeight().replaceAll(" ", "")).build();

            addList.add(getRecords);
        }
        return addList;
    }

    @Override
    public List<WeightRecordsDTO> getAllChildWeightRecords(String centerId) {

        if (StringUtils.isEmpty(centerId.trim())) {
            throw new CustomException("center Id Is Missed, Please Check!!");
        }

        LocalDateTime minus6Years = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(minus6Years, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        HashSet<String> uniqueChildId = new HashSet<>();
        List<WeightRecordsDTO> addInList = new ArrayList<>();
        List<FamilyMember> findAllChildRecords = familyMemberRepository.findAllByCenterIdAndDob(centerId,
                convertToMills, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (FamilyMember tracking : findAllChildRecords) {

            List<WeightTracking> findChildDetails = weightTrackingRepository.findAllByChildIdLimit(tracking.getId(),
                    Sort.by(Sort.Direction.DESC, "createdDate"));

            String weight = "", height = "", weightDate = "";

            Family findFamilyDetails = familyRepository.findByFamilyId(tracking.getFamilyId());

            for (WeightTracking wt : findChildDetails) {
                weight = wt.getWeight() == null ? "" : wt.getWeight();
                height = wt.getHeight() == null ? "" : wt.getHeight();
                Date date = new Date(wt.getDate());
                weightDate = ApplicationConstants.df.format(date);
            }

            if (uniqueChildId.add(tracking.getId())) {
                WeightRecordsDTO singleEntry = WeightRecordsDTO.builder()
                        .familyId(tracking.getFamilyId() == null ? "" : tracking.getFamilyId())
                        .childId(tracking.getId() == null ? "" : tracking.getId())
                        .name(tracking.getName())
                        .houseNo(findFamilyDetails.getHouseNo() == null ? "" : findFamilyDetails.getHouseNo())
                        .gender(tracking.getGender())
                        .motherName(tracking.getMotherName())
                        .dob(ApplicationConstants.df.format(tracking.getDob()))
                        .photo(tracking.getPhoto() == null ? "" : tracking.getPhoto())
                        .date(weightDate)
                        .centerName(tracking.getCenterName())
                        .weight(weight)
                        .height(height)
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
        String output = "";
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);

        output = "01-0" + inputMonth + "-" + year;

//        log.error("startDate " + output);
        return output;
    }

    private String MPRMonthEndDate(String endMonth) throws ParseException {
        String inputMonth = endMonth == null ? "" : endMonth;
        inputMonth = commonMethodsService.getEndDateOfMonth(inputMonth);

        return inputMonth;
    }

    @Override
    public MPRDTO getMPRRecords(String month, String duration, String category, String centerId)
            throws ParseException {
        month = month == null ? "" : month;
        category = category == null ? "" : category;

        MPRDTO mprCounts = new MPRDTO();

        if (centerId != null) {
            Date startTime = null, endTime = null;
            // Millis to Date
            startTime = new Date(ApplicationConstants.startTimeInMillis);
//            log.error("startTime " + startTime);


            if (!month.isEmpty()) {
                endTime = ApplicationConstants.df.parse(MPRMonthEndDate(month));
            } else {
                endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth());
            }

//            log.error("End Time : " + endTime);

            mprCounts.setMale(familyMemberRepository.countByCenterIdAndGenderAndCategoryAndCreatedDate(centerId, "1", category, startTime, endTime));
            mprCounts.setFemale(familyMemberRepository.countByCenterIdAndGenderAndCategoryAndCreatedDate(centerId, "2", category, startTime, endTime));
            mprCounts.setMortality(familyMemberRepository.countByAndCenterId(centerId, category, startTime, endTime));
            mprCounts.setBirth(familyMemberRepository.countByDob(centerId, category, getSixYearsAgoMillis(endTime.getTime()), endTime.getTime()));
            mprCounts.setDharti(pregnantAndDeliveryRepository.countByCenterIdAndCategoryAndDateOfDelivery(centerId, category, getSixMonthsAgoMillis(System.currentTimeMillis()), System.currentTimeMillis()));
            mprCounts.setPregnant(pregnantAndDeliveryRepository.countByCenterIdAndCategoryAndLastMissedPeriodDate(centerId, category, endTime.getTime()));


        } else {
            System.out.println("@@@@@@@@ Center Id not Passed or Center Id passed is  : " + centerId);
            throw new CustomException("Please connect with Support Team. ");
        }
        return mprCounts;

    }

    @Override
    public FamilyMemberCounts getMembersByFamilyId(String familyId) {

        List<FamilyMember> checkMembers = familyMemberRepository.findAllByFamilyId(familyId,
                Sort.by(Sort.Direction.ASC, "createdDate"));
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

        return FamilyMemberCounts.builder().male(male).female(female).children(children).build();
    }

    @Override
    public List<GetVaccinationDTO> getVaccinationRecords(String vaccineCode, String centerId) {

        List<GetVaccinationDTO> addList = new ArrayList<>();
//		List<Visits> vaccinationList = new ArrayList<>();
        HashSet<String> uniqueMemberId = new HashSet<>();
        String code = vaccineCode == null ? "" : vaccineCode;

        List<Vaccination> vaccinationList = vaccinationRepository.findAllByCenterIdAndVaccinationCode(centerId, code);

//		if (code.trim().length() > 0) {
//			vaccinationList = visitsRepository.findAllByVisitForSearchCriteria(vaccineCode, centerId,
//					Sort.by(Sort.Direction.DESC, "createdDate"));
//		} else {
//			vaccinationList = visitsRepository.findAllByVisitForAndCenterId(centerId,
//					Sort.by(Sort.Direction.DESC, "createdDate"));
//		}

        if (vaccinationList.size() > 0) {
            for (Vaccination vaccDetails : vaccinationList) {

                if (familyMemberRepository.findById(vaccDetails.getChildId()).isPresent()) {
                    FamilyMember fmd = familyMemberRepository.findById(vaccDetails.getChildId()).get();

                    Family findHouse = familyRepository.findByFamilyId(fmd.getFamilyId());
                    long getMills = fmd.getDob();

                    Date date = new Date(getMills);

                    if (uniqueMemberId.add(fmd.getId())) {
                        GetVaccinationDTO addSingle = GetVaccinationDTO.builder().name(fmd.getName())
                                .gender(fmd.getGender()).childId(vaccDetails.getChildId()).centerId(centerId)
                                .centerName(commonMethodsService.findCenterName(centerId))
                                .houseNo(findHouse.getHouseNo()).vaccinationCode(vaccDetails.getVaccinationCode())
                                .vaccinationName(commonMethodsService.getVaccineName(vaccDetails.getVaccinationCode()))
                                .age(ApplicationConstants.df.format(date)).photo(fmd.getPhoto())
                                .motherName(fmd.getMotherName()).build();
                        addList.add(addSingle);
                    }
                }
            }
        }
        return addList;

    }

    @Override
    public List<HouseholdsChildren> getAllHouseholdsChildren(String centerId) throws ParseException {

        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());

//        Date parseTime = df.parse(convertToString);

        List<HouseholdsChildren> addList = new ArrayList<>();

        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Time " + convertToMills);

        LocalDateTime dateLess3Yrs = LocalDateTime.now().minusYears(3);
        ZonedDateTime zoneDate = ZonedDateTime.of(dateLess3Yrs, ZoneId.systemDefault());
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        long timeLess3Years = zoneDate.toInstant().toEpochMilli();

        List<FamilyMember> findAllChildren = familyMemberRepository.findAllByDobAndCenterId(convertToMills, centerId,
                timeLess3Years);
//        log.error("List of children " + findAllChildren);
        String gender = "";

        if (findAllChildren.size() > 0) {
            String minority = "", category = "";
            for (FamilyMember member : findAllChildren) {

//				if (member.getDob() >= timeLess3Years) {
//					continue;
//				}

                List<AnganwadiChildren> findNonRegistered = anganwadiChildrenRepository
                        .findAllByChildIdAndRegisteredTrueAndDeletedFalse(member.getId());

                if (findNonRegistered.size() <= 0) {
                    Date getMillis = new Date(member.getDob());
                    List<Family> findFamilyDetails = familyRepository.findAllByFamilyId(member.getFamilyId());
                    for (Family findDetails : findFamilyDetails) {
                        minority = findDetails.getIsMinority();
                        category = findDetails.getCategory();
                    }

                    HouseholdsChildren singeEntry = HouseholdsChildren.builder().childName(member.getName())
                            .motherName("")
                            .fatherName("")
                            .familyId(member.getFamilyId())
                            .childId(member.getId())
                            .dob(ApplicationConstants.df.format(getMillis))
                            .motherName(member.getMotherName())
                            .fatherName(member.getFatherName())
                            .memberCode(member.getMemberCode())
                            .gender(member.getGender())
                            .mobileNumber(member.getMobileNumber())
                            .category(category)
                            .minority(minority)
                            .handicap(member.getHandicapType())
                            .photoUrl(member.getPhoto()).build();

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

    private Boolean validateBeforeAdding(PregnantAndDelivery womenDetails) {

        boolean valid = false;
        PregnantAndDeliveryDTO pddDto = modelMapper.map(womenDetails, PregnantAndDeliveryDTO.class);

        if (womenDetails.getLastMissedPeriodDate() == 0 && womenDetails.getDateOfDelivery() == 0 && checkPregnantWithInYear(pddDto, womenDetails.getLastMissedPeriodDate())) {
            valid = true;
        }
        return valid;
    }

    @Override
    public List<FemaleMembersDTO> getHouseholdsFemaleDetails(String centerId) {

        List<FamilyMember> findFemales = familyMemberRepository.findAllByGenderAndCenterId(centerId, true, false);
        List<FemaleMembersDTO> addList = new ArrayList<>();

        for (FamilyMember checkAge : findFemales) {

            List<PregnantAndDelivery> checkPregnantWomen = pregnantAndDeliveryRepository.findAllByMotherMemberId(checkAge.getId(), Sort.by(Sort.Direction.ASC, "createdDate"));

            int getYear = 0;
            String husbandName = "", houseNo = "", familyId = "";

            long millis = checkAge.getDob();
            Date date = new Date(millis);

            String dateOfDob = ApplicationConstants.df.format(date);
            String[] dateParts = dateOfDob.split("-");
            getYear = Integer.parseInt(dateParts[2]);
            int currentYear = Year.now().getValue();
            int getDiff = currentYear - getYear;


            // Adding Women, Not in a Pregnant & Delivery Table

            if (getDiff >= 20 && getDiff <= 60 && checkPregnantWomen.size() <= 0) {

                // center Name
                List<Family> checkHouseDetails = familyRepository.findAllByFamilyId(checkAge.getFamilyId());

                for (Family getDetails : checkHouseDetails) {
                    houseNo = getDetails.getHouseNo();
                    familyId = getDetails.getFamilyId();
                }

                husbandName = checkAge.getFatherName();

                FemaleMembersDTO addMember = FemaleMembersDTO.builder()
                        .name(checkAge.getName() == null ? "" : checkAge.getName())
                        .centerName(commonMethodsService.findCenterName(centerId)).husbandName(husbandName)
                        .familyId(familyId).houseNo(houseNo).memberId(checkAge.getId())
                        .profilePic(checkAge.getPhoto() == null ? "" : checkAge.getPhoto())
                        .dob(ApplicationConstants.df.format(checkAge.getDob())).build();

                addList.add(addMember);
            }

            // Adding Women, Exists in a Pregnant & Delivery Table After Filtering
            for (PregnantAndDelivery pdd : checkPregnantWomen) {

                if (getDiff >= 20 && getDiff <= 60 && validateBeforeAdding(pdd)) {

                    // center Name
                    List<Family> checkHouseDetails = familyRepository.findAllByFamilyId(checkAge.getFamilyId());

                    for (Family getDetails : checkHouseDetails) {
                        houseNo = getDetails.getHouseNo();
                        familyId = getDetails.getFamilyId();
                    }

                    husbandName = checkAge.getFatherName();

                    FemaleMembersDTO addMember = FemaleMembersDTO.builder()
                            .name(checkAge.getName() == null ? "" : checkAge.getName())
                            .centerName(commonMethodsService.findCenterName(centerId)).husbandName(husbandName)
                            .familyId(familyId).houseNo(houseNo).memberId(checkAge.getId())
                            .profilePic(checkAge.getPhoto() == null ? "" : checkAge.getPhoto())
                            .dob(ApplicationConstants.df.format(checkAge.getDob())).build();

                    addList.add(addMember);
                }
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
            log.error("id " + visitsDetails.getMemberId());
            if (familyMemberRepository.findById(visitsDetails.getMemberId()).isPresent()) {
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
                            .name(checkMembers.getName() == null ? "" : checkMembers.getName()).dob(df.format(date))
                            .husbandName(husband_FatherName == null ? "" : husband_FatherName).houseNo(houseNo)
                            .memberId(checkMembers.getId()).centerName(commonMethodsService.findCenterName(centerId))
                            .profilePic(checkMembers.getPhoto() == null ? "" : checkMembers.getPhoto())
                            .visits(visitRounds(visitsDetails.getMemberId())).build();

                    addInList.add(addSingle);

                }
            }
        }

        return addInList;
    }

    private List<VisitArray> visitArrayList(String currentMemberId, String visitType) {

        List<Visits> checkVisitsFor = visitsRepository.findAllByMotherIdAndVisitType(currentMemberId, visitType);
        List<VisitArray> addInList = new ArrayList<>();

        for (Visits findDetails : checkVisitsFor) {

            long millis = findDetails.getVisitDateTime();
            Date date = new Date(millis);
            VisitArray visitArray = new VisitArray();


            if (findDetails.getVisitCategory() != null) {
                switch (findDetails.getVisitCategory().trim()) {

                    case "1":
                        visitArray = VisitArray.builder().date(ApplicationConstants.df.format(date))
                                .visitCategory(findDetails.getVisitCategory() == null ? "" : findDetails.getVisitCategory())
                                .visitFor(findDetails.getVisitFor() == null ? "" : findDetails.getVisitFor())
                                .visitRound(findDetails.getVisitRound() == null ? "" : findDetails.getVisitRound())
                                .description(findDetails.getDescription() == null ? "" : findDetails.getDescription())
                                .bloodTestArray(bloodTestArrayList(findDetails, currentMemberId))
                                .weightTrackArray(Collections.emptyList())
                                .vaccinationArray(Collections.emptyList()).build();
                        break;

                    case "2":
                        visitArray = VisitArray.builder().date(ApplicationConstants.df.format(date))
                                .visitCategory(findDetails.getVisitCategory() == null ? "" : findDetails.getVisitCategory())
                                .visitFor(findDetails.getVisitFor() == null ? "" : findDetails.getVisitFor())
                                .visitRound(findDetails.getVisitRound() == null ? "" : findDetails.getVisitRound())
                                .description(findDetails.getDescription() == null ? "" : findDetails.getDescription())
                                .bloodTestArray(Collections.emptyList())
                                .weightTrackArray(weightArrayList(findDetails))
                                .vaccinationArray(Collections.emptyList()).build();
                        break;

                    case "3":
                        visitArray = VisitArray.builder().date(ApplicationConstants.df.format(date))
                                .visitCategory(findDetails.getVisitCategory() == null ? "" : findDetails.getVisitCategory())
                                .visitFor(findDetails.getVisitFor() == null ? "" : findDetails.getVisitFor())
                                .visitRound(findDetails.getVisitRound() == null ? "" : findDetails.getVisitRound())
                                .description(findDetails.getDescription() == null ? "" : findDetails.getDescription())
                                .bloodTestArray(Collections.emptyList()).weightTrackArray(Collections.emptyList())
                                .vaccinationArray(vaccinationArrayList(findDetails)).build();
                        break;

                    case "4":
                        visitArray = VisitArray.builder()
                                .date(ApplicationConstants.df.format(date))
                                .visitCategory(findDetails.getVisitCategory() == null ? "" : findDetails.getVisitCategory())
                                .visitFor(findDetails.getVisitFor() == null ? "" : findDetails.getVisitFor())
                                .visitRound(findDetails.getVisitRound() == null ? "" : findDetails.getVisitRound())
                                .description(findDetails.getDescription() == null ? "" : findDetails.getDescription())
                                .bloodTestArray(Collections.emptyList())
                                .weightTrackArray(Collections.emptyList())
                                .vaccinationArray(Collections.emptyList())
                                .build();
                        break;

                    default:
                        visitArray = VisitArray.builder().date(ApplicationConstants.df.format(date)).visitCategory("")
                                .visitFor("").visitRound("").description("").bloodTestArray(Collections.emptyList())
                                .weightTrackArray(Collections.emptyList()).vaccinationArray(Collections.emptyList())
                                .build();
                }

                addInList.add(visitArray);
            }

        }
        return addInList;
    }

    private String getVisitMotherId(String id) {
        String motherId = id;

        List<BabiesBirth> checkMother = babiesBirthRepository.findAllByChildId(id);
        if (checkMother.size() > 0) {
            for (BabiesBirth visits : checkMother) {
                motherId = visits.getMotherMemberId();
            }
        }
        return motherId;
    }

    @Override
    public List<MemberVisits> getMemberVisitDetailsLatest(String memberId, String centerId) {

        List<MemberVisits> addInList = new ArrayList<>();
        MemberVisits memberVisits = new MemberVisits();
        HashSet<String> uniqueMember = new HashSet<>();
        String fetchMemberId = "";

        for (int i = 1; i <= 10; i++) {

            // find Mother Id
            fetchMemberId = getVisitMotherId(memberId);

            List<Visits> findMember = visitsRepository.findAllByMotherIdAndCenterIdAndVisitType(fetchMemberId, centerId, String.valueOf(i));

            if (findMember.size() > 0) {
                for (Visits checkDetails : findMember) {
                    memberVisits = MemberVisits.builder()
                            .visitType(checkDetails.getVisitType())
                            .visitArray(visitArrayList(fetchMemberId, checkDetails.getVisitType()))
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
        List<HouseVisitSchedule> findDeliveryDetails = houseVisitScheduleRepository
                .findAllByMemberId(visits.getMemberId());

        // Within 1-7 Days of Birth

        if (findDeliveryDetails.size() <= 0 && babiesBirth.getDob() > 0) {

            LocalDate localDate = Instant.ofEpochMilli(babiesBirth.getDob()).atZone(ZoneId.systemDefault())
                    .toLocalDate();

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("4")
                    .visitName("TILL_1_7_DAYS")
                    .dueDate(localDate.plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("5")
                    .visitName("TILL_8_30_DAYS")
                    .dueDate(localDate.plusDays(29).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            // Visit TILL_1_5_MONTHS

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("6")
                    .visitName("TILL_1_5_MONTHS")
                    .dueDate(localDate.plusDays(31).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("6")
                    .visitName("TILL_1_5_MONTHS")
                    .dueDate(localDate.plusDays(59).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("6")
                    .visitName("TILL_1_5_MONTHS")
                    .dueDate(localDate.plusDays(89).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("6")
                    .visitName("TILL_1_5_MONTHS")
                    .dueDate(localDate.plusDays(119).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("6")
                    .visitName("TILL_1_5_MONTHS")
                    .dueDate(localDate.plusDays(149).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            // Visit TILL_6_8_MONTHS

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("7")
                    .visitName("TILL_6_8_MONTHS")
                    .dueDate(localDate.plusDays(179).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("7")
                    .visitName("TILL_6_8_MONTHS")
                    .dueDate(localDate.plusDays(209).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("7")
                    .visitName("TILL_6_8_MONTHS")
                    .dueDate(localDate.plusDays(239).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            // Visit TILL_9_11_MONTHS

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("8")
                    .visitName("TILL_9_11_MONTHS")
                    .dueDate(localDate.plusDays(269).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("8")
                    .visitName("TILL_9_11_MONTHS")
                    .dueDate(localDate.plusDays(299).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("8")
                    .visitName("TILL_9_11_MONTHS")
                    .dueDate(localDate.plusDays(329).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            // Visit TILL_12_17_MONTHS

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("9")
                    .visitName("TILL_12_17_MONTHS")
                    .dueDate(localDate.plusDays(364).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("9")
                    .visitName("TILL_12_17_MONTHS")
                    .dueDate(localDate.plusDays(389).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("9")
                    .visitName("TILL_12_17_MONTHS")
                    .dueDate(localDate.plusDays(419).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("9")
                    .visitName("TILL_12_17_MONTHS")
                    .dueDate(localDate.plusDays(449).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("9")
                    .visitName("TILL_12_17_MONTHS")
                    .dueDate(localDate.plusDays(479).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("9")
                    .visitName("TILL_12_17_MONTHS")
                    .dueDate(localDate.plusDays(509).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            // Visit TILL_18_24_MONTHS

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("10")
                    .visitName("TILL_18_24_MONTHS")
                    .dueDate(localDate.plusDays(539).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("10")
                    .visitName("TILL_18_24_MONTHS")
                    .dueDate(localDate.plusDays(569).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("10")
                    .visitName("TILL_18_24_MONTHS")
                    .dueDate(localDate.plusDays(599).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("10")
                    .visitName("TILL_18_24_MONTHS")
                    .dueDate(localDate.plusDays(629).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("10")
                    .visitName("TILL_18_24_MONTHS")
                    .dueDate(localDate.plusDays(659).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("10")
                    .visitName("TILL_18_24_MONTHS")
                    .dueDate(localDate.plusDays(689).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

            houseVisitScheduleRepository.save(HouseVisitSchedule.builder().memberId(babiesBirth.getChildId())
                    .centerId(visits.getCenterId()).centerName(visits.getCenterName()).visitType("10")
                    .visitName("TILL_18_24_MONTHS")
                    .dueDate(localDate.plusDays(719).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .build());

        }
    }

//    @Override
//    public SaveVaccinationDTO saveVaccinationDetails(SaveVaccinationDTO saveVaccinationDTO, String centerId,
//                                                     String centerName) {
//
//        String familyId = saveVaccinationDTO.getFamilyId() == null ? "" : saveVaccinationDTO.getFamilyId();
//        String motherName = saveVaccinationDTO.getMotherName() == null ? "" : saveVaccinationDTO.getMotherName();
//        String childId = saveVaccinationDTO.getChildId() == null ? "" : saveVaccinationDTO.getChildId();
//        String vaccinationName = saveVaccinationDTO.getVaccinationName() == null ? ""
//                : saveVaccinationDTO.getVaccinationName();
//        String visitFor = saveVaccinationDTO.getVisitFor() == null ? "" : saveVaccinationDTO.getVisitFor();
//        String vaccinationCode = saveVaccinationDTO.getVaccinationCode() == null ? ""
//                : saveVaccinationDTO.getVaccinationCode();
//        String visitType = saveVaccinationDTO.getVisitType() == null ? "" : saveVaccinationDTO.getVisitType();
//        String desc = saveVaccinationDTO.getDescription() == null ? "" : saveVaccinationDTO.getDescription();
//        String visitRound = saveVaccinationDTO.getVisitRound() == null ? "" : saveVaccinationDTO.getVisitRound();
//        String latitude = saveVaccinationDTO.getLatitude() == null ? "" : saveVaccinationDTO.getLatitude();
//        String longitude = saveVaccinationDTO.getLongitude() == null ? "" : saveVaccinationDTO.getLongitude();
//        String dob = saveVaccinationDTO.getDob() == null ? "" : saveVaccinationDTO.getDob();
//        // Current Date
//        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date date = new Date();
//        long mills = date.getTime();
//
//        String visitCat = "";
//        List<Family> findCat = familyRepository.findAllByFamilyId(familyId);
//
//        for (Family cat : findCat) {
//            visitCat = cat.getCategory();
//        }
//
//        // Save in Vaccination
//        Vaccination saveRecord = Vaccination.builder().familyId(familyId).date(mills).centerId(centerId)
//                .centerName(commonMethodsService.findCenterName(centerId)).childId(childId)
//                .vaccinationCode(vaccinationCode).build();
//        vaccinationRepository.save(saveRecord);
//
//        // Save in Visits
////        Visits saveVaccinationVisit = Visits.builder()
////                .visitType(visitType)
////                .visitFor(visitFor)
////                .memberId(childId)
////                .category(visitCat)
////                .familyId(familyId)
////                .centerName(centerName)
////                .childDob(new Date(dob).getTime())
////                .visitDateTime(mills)
////                .description(desc)
////                .latitude(latitude)
////                .longitude(longitude)
////                .visitRound(visitRound)
////                .build();
////        visitsRepository.save(saveVaccinationVisit);
//
//        return SaveVaccinationDTO.builder().familyId(familyId).date(df.format(mills)).motherName(motherName)
//                .childId(childId).dob(dob).description(desc).vaccinationName(vaccinationName).visitType(visitType)
//                .visitFor(visitFor).familyId(familyId).latitude(latitude).longitude(longitude).visitRound(visitRound)
//                .build();
//    }

    private void updateVaccinationVisitSchedule(BabiesBirth babiesBirth) {

        if (babiesBirth.getDob() > 0) {
            LocalDate localDate = Instant.ofEpochMilli(babiesBirth.getDob()).atZone(ZoneId.systemDefault())
                    .toLocalDate();

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-4").vaccinationName("OPV-0")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(14).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-5").vaccinationName("HEPATITIS-B")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(babiesBirth.getDob()).familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-6").vaccinationName("B.C.G.")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(babiesBirth.getDob()).familyId(babiesBirth.getFamilyId())
                    .memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-6").vaccinationName("B.C.G.")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-7").vaccinationName("OPV-1,2&3")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-7").vaccinationName("OPV-1,2&3")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-7").vaccinationName("OPV-1,2&3")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-8")
                    .vaccinationName("PENTAVALENT-1,2&3").vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-8")
                    .vaccinationName("PENTAVALENT-1,2&3").vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-8")
                    .vaccinationName("PENTAVALENT-1,2&3").vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-9").vaccinationName("ROTAVIRUS")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-9").vaccinationName("ROTAVIRUS")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(70).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-9").vaccinationName("ROTAVIRUS")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-10").vaccinationName("FIPV")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-10").vaccinationName("FIPV")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-11").vaccinationName("PCV")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(42).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-11").vaccinationName("PCV")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(98).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-12")
                    .vaccinationName("MEASLES_RUBELLA_FIRST_DOSE").vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-13").vaccinationName("PCV_BOOSTER")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-14")
                    .vaccinationName("VITAMIN_A_FIRST_DOSE").vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-15")
                    .vaccinationName("DPT_BOOSTER_1").vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-16")
                    .vaccinationName("MEASLES_RUBELLA_SECOND_DOSE").vaccinationDate(0)
                    .centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-17").vaccinationName("OPV_BOOSTER")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-18").vaccinationName("VITAMIN_A")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(540).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-19")
                    .vaccinationName("DPT_BOOSTER_2").vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(2190).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

            vaccinationScheduleRepository.save(VaccinationSchedule.builder().code("V-20").vaccinationName("TD")
                    .vaccinationDate(0).centerId(babiesBirth.getCenterId())
                    .centerName(commonMethodsService.findCenterName(babiesBirth.getCenterId()))
                    .dueDate(localDate.plusDays(5840).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                    .familyId(babiesBirth.getFamilyId()).memberId(babiesBirth.getChildId()).build());

        }
    }

    @Override
    public List<BirthPlaceDTO> saveBirthDetails(BirthPlaceDTO birthDetails, String centerId) throws ParseException {
        List<BirthPlaceDTO> addInList = new ArrayList<>();
        String headCategory = "";

        // Find Family Details

        FamilyMember searchFamilyId = familyMemberRepository.findById(birthDetails.getMotherMemberId()).get();
        List<Family> findHod = familyRepository.findAllByFamilyId(searchFamilyId.getFamilyId());
        for (Family getFamilyId : findHod) {
            headCategory = getFamilyId.getCategory();
        }
        List<FamilyMember> findHead = familyMemberRepository.findAllByFamilyId(searchFamilyId.getFamilyId(),
                Sort.by(Sort.Direction.ASC, "createdDate"));
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


        Date date = ApplicationConstants.df.parse(birthDetails.getDob());
        long mills = date.getTime();

        Date currentMonth = new Date();
        String[] splitMonth = ApplicationConstants.df.format(currentMonth).split("-");
        String getMonth = splitMonth[1].replace("0", "");

        // Save in Family Member Table

        FamilyMember addMember = FamilyMember.builder().familyId(searchFamilyId.getFamilyId())
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .category(searchFamilyId.getCategory().length() <= 0 ? headCategory : searchFamilyId.getCategory())
                .centerId(centerId).motherName(searchFamilyId.getName())
                .fatherName(headName).maritalStatus("2")
                .dateOfMortality("").dateOfLeaving("")
                .dateOfArrival("")
                .handicapType("")
                .photo("")
                .memberCode("")
                .residentArea("")
                .memberCode("")
                .handicap("")
                .idType("")
                .idNumber("")
                .recordForMonth(getMonth)
                .mobileNumber(searchFamilyId.getMobileNumber())
                .stateCode(searchFamilyId.getStateCode())
                .centerName(commonMethodsService.findCenterName(centerId))
                .relationWithOwner(birthDetails.getRelationWithOwner() == null ? "" : birthDetails.getRelationWithOwner())
                .gender(birthDetails.getGender())
                .dob(mills).build();
        familyMemberRepository.save(addMember);

        // Save in Birth Table

        BabiesBirth saveDetails = BabiesBirth.builder()
                .name(birthDetails.getName() == null ? "" : birthDetails.getName()).childId(addMember.getId())
                .birthPlace(birthDetails.getBirthPlace() == null ? "" : birthDetails.getBirthPlace())
                .birthType(birthDetails.getBirthType() == null ? "" : birthDetails.getBirthType())
                .familyId(searchFamilyId.getFamilyId()).dob(mills)
                .srNo(birthDetails.getSrNo() == null ? "" : birthDetails.getSrNo())
                .motherMemberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .gender(birthDetails.getGender() == null ? "" : birthDetails.getGender()).centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight())
                .firstWeight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight()).build();
        babiesBirthRepository.save(saveDetails);

        // Save in Weight Table

        WeightTracking saveRecord = WeightTracking.builder()
                .familyId(searchFamilyId.getFamilyId() == null ? "" : searchFamilyId.getFamilyId())
                .motherId(birthDetails.getMotherMemberId())
                .visitType(birthDetails.getVisitType())
                .visitRound(birthDetails.getVisitRound())
                .childId(saveDetails.getChildId() == null ? "" : saveDetails.getChildId()).date(mills)
                .centerId(centerId).centerName(commonMethodsService.findCenterName(centerId))
                .weight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight()).build();

        weightTrackingRepository.save(saveRecord);

        // Save in Visit
        Date visitDate = new Date();

        DateFormat visitDf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String convertToString = visitDf.format(new Date());
        Date visitTime = visitDf.parse(convertToString);
        long timestamp = visitTime.getTime();

        Visits updateRecord = Visits.builder().visitFor(birthDetails.getVisitFor())
                .familyId(searchFamilyId.getFamilyId()).visitType(birthDetails.getVisitType())
                .motherId(birthDetails.getMotherMemberId())
                .visitCategory(ApplicationConstants.birth_visit_category)
                .visitRound(birthDetails.getVisitRound())
                .memberId(saveDetails.getChildId())
                .centerName(commonMethodsService.findCenterName(centerId)).centerId(centerId).childDob(mills)
                .category(searchFamilyId.getCategory().length() <= 0 ? headCategory : searchFamilyId.getCategory())
                .description("").longitude(birthDetails.getLongitude() == null ? "" : birthDetails.getLongitude())
                .latitude(birthDetails.getLatitude() == null ? "" : birthDetails.getLatitude()).visitDateTime(timestamp)
                .build();

        visitsRepository.save(updateRecord);

        // Update in PregnantAndDelivery Table

        PregnantAndDelivery pdd = pregnantAndDeliveryRepository
                .findTopOneByMotherMemberId(birthDetails.getMotherMemberId());

        if (pdd != null) {
            pdd.setChildName(birthDetails.getName());
            pdd.setChildGender(birthDetails.getGender());
            pdd.setDateOfDelivery(mills);
            pregnantAndDeliveryRepository.save(pdd);
        }

        // Update House visit & Vaccination Schedule Details
        updateDeliveryHouseVisitSchedule(saveDetails, updateRecord);
        updateVaccinationVisitSchedule(saveDetails);

        BirthPlaceDTO singleEntry = BirthPlaceDTO.builder().id(addMember.getId())
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .relationWithOwner(
                        birthDetails.getRelationWithOwner() == null ? "" : birthDetails.getRelationWithOwner())
                .dob(birthDetails.getDob())
                .birthPlace(birthDetails.getBirthPlace() == null ? "" : birthDetails.getBirthPlace())
                .birthType(birthDetails.getBirthType() == null ? "" : birthDetails.getBirthType())
                .familyId(searchFamilyId.getFamilyId() == null ? "" : searchFamilyId.getFamilyId())
                .motherMemberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .gender(birthDetails.getGender() == null ? "" : birthDetails.getGender()).centerId(centerId)
                .srNo(birthDetails.getSrNo() == null ? "" : birthDetails.getSrNo())
                .centerName(commonMethodsService.findCenterName(centerId)).visitFor(birthDetails.getVisitFor())
                .visitType(birthDetails.getVisitType())
                .firstWeight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight()).build();
        addInList.add(singleEntry);

        return addInList;
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

    @Override
    public DashboardFamilyData getDashboardFamilyData(DashboardFilter dashboardFilter) throws ParseException {

        Set<String> uniquePregnantWomen = new HashSet<>();
        Set<String> uniqueDhartiWomen = new HashSet<>();

//		Date startTime = null, endTime = null;

        // Currently Not Required, Need to Provide entire Data

//		if (dashboardFilter.getStartDate().trim().length() > 0) {
//			startTime = df.parse(dashboardFilter.getStartDate().trim());
//
//		} else {
//			startTime = df.parse(commonMethodsService.startDateOfMonth());
//		}
//
//		if (dashboardFilter.getEndDate().trim().length() > 0) {
//			endTime = df.parse(dashboardFilter.getEndDate().trim());
//		} else {
//			endTime = df.parse(commonMethodsService.endDateOfMonth());
//		}

        // Beneficiary Children
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        /*
         * LocalDateTime dateFor3Years = LocalDateTime.now().minusYears(3);
         * ZonedDateTime zdt2 = ZonedDateTime.of(dateFor3Years,ZoneId.systemDefault());
         * long date3MonthsBack = zdt2.toInstant().toEpochMilli();
         */
        // Beneficiary dharti
        LocalDateTime dhartiDate = LocalDateTime.now().minusMonths(6);
        ZonedDateTime dhartiZdt = ZonedDateTime.of(dhartiDate, ZoneId.systemDefault());
        long convertToMills2 = dhartiZdt.toInstant().toEpochMilli();

        List<PregnantAndDelivery> pdd = pregnantAndDeliveryRepository.findAllDashboardFamilyPregnancyCriteria(dashboardFilter.getCenterId().trim(), ApplicationConstants.ignoreCenters);

        for (PregnantAndDelivery delivery : pdd) {
            uniquePregnantWomen.add(delivery.getMotherMemberId());
        }


        List<PregnantAndDelivery> dhartiWomen = pregnantAndDeliveryRepository.findAllDashboardFamilyBeneficiaryDharti(
                dashboardFilter.getCenterId().trim(), convertToMills2, ApplicationConstants.ignoreCenters);

        for (PregnantAndDelivery dharti : dhartiWomen) {
            uniqueDhartiWomen.add(dharti.getMotherMemberId());
            log.error("MotherId : " + dharti.getMotherMemberId());
        }


        // As Above endTime Reason

//		Calendar addOneDay = Calendar.getInstance();
//		addOneDay.setTime(endTime);
//		addOneDay.add(Calendar.DATE, 1);
//		endTime = addOneDay.getTime();

        List<FamilyMember> fm = familyMemberRepository.findAllDashboardFamilyChildren(dashboardFilter.getCenterId().trim(), convertToMills, ApplicationConstants.ignoreCenters);


        return DashboardFamilyData.builder()
                .nursingMothers(uniqueDhartiWomen.size())
                .pregnantWomen(uniquePregnantWomen.size())
                .totalBeneficiary(uniqueDhartiWomen.size() + uniquePregnantWomen.size() + fm.size())
                .children(fm.size())
                .build();
    }

    @Override
    public List<TotalChildrenData> getTotalChildrenData(DashboardFilter dashboardFilter) throws ParseException {

        List<TotalChildrenData> childrenData = new ArrayList<>();


        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();
        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = ApplicationConstants.df.parse(dashboardFilter.getStartDate().trim());

        } else {
            startTime = ApplicationConstants.df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = ApplicationConstants.df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth());
        }

        LocalDateTime sixthDate = LocalDateTime.now().minusYears(6);
        ZonedDateTime sixthZdt = ZonedDateTime.of(sixthDate, ZoneId.systemDefault());
        long sixthMills = sixthZdt.toInstant().toEpochMilli();

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<FamilyMember> findChildrenRecords = familyMemberRepository.findAllByChildrenCriteria(startTime, endTime,
                sixthMills);

        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        for (FamilyMember fm : findChildrenRecords) {
            Family findFamily = familyRepository.findByFamilyId(fm.getFamilyId());
            String minority = findFamily.getIsMinority() == null ? "" : findFamily.getIsMinority();
            childrenData.add(TotalChildrenData.builder().caste(fm.getCategory() == null ? "" : fm.getCategory())
                    .gender(fm.getGender() == null ? "" : fm.getGender()).startDate(ApplicationConstants.df.format(startTime))
                    .endDate(ApplicationConstants.df.format(endTime)).isMinority(minority).name(fm.getName() == null ? "" : fm.getName())
                    .centerId(fm.getCenterId() == null ? "" : fm.getCenterId())
                    .centerName(fm.getCenterName() == null ? "" : fm.getCenterName()).build());
        }
        return childrenData;
    }

    private String calDuration(long missedPeriod, long endTimeInMillis) {
//        long currentMillis = new Date().getTime();
        LocalDate startTime = Instant.ofEpochMilli(endTimeInMillis).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate endTime = Instant.ofEpochMilli(missedPeriod).atZone(ZoneId.systemDefault()).toLocalDate();
        Period diff = Period.between(startTime, endTime);
//		log.error("gap " + Math.abs(diff.getMonths()));
        return String.valueOf(Math.abs(diff.getMonths()));
    }

    @Override
    public List<PregnantWomenDetails> getPregnantWomenDetails(DashboardFilter dashboardFilter) throws ParseException {

        List<PregnantWomenDetails> addInList = new ArrayList<>();
        HashSet<String> uniqueMemberId = new HashSet<>();

        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = ApplicationConstants.df.parse(dashboardFilter.getStartDate().trim());

        } else {
            startTime = ApplicationConstants.df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = ApplicationConstants.df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth());
        }

        List<PregnantAndDelivery> findPregnancyData = pregnantAndDeliveryRepository.findAllByPregnancyCriteria(
                startTime.getTime(), endTime.getTime(), dashboardFilter.getCenterId().trim());

        try {
            for (PregnantAndDelivery visits : findPregnancyData) {

                if (uniqueMemberId.add(visits.getMotherMemberId()) && visits.getRegDate() <= endTime.getTime()) {
                    List<FamilyMember> searchNames = familyMemberRepository
                            .findAllByIdAndNameSearch(visits.getMotherMemberId(), dashboardFilter.getSearch().trim());

                    if (searchNames.size() > 0) {
                        for (FamilyMember searchResults : searchNames) {
                            Family households = familyRepository.findByFamilyId(searchResults.getFamilyId());

                            PregnantWomenDetails addSingle = PregnantWomenDetails.builder()
                                    .name(searchResults.getName()).motherId(searchResults.getId())
                                    .centerId(searchResults.getCenterId())
                                    .lastMissedPeriodDate(ApplicationConstants.df.format(visits.getLastMissedPeriodDate()))
                                    .husbandName(searchResults.getFatherName()).dob(ApplicationConstants.df.format(searchResults.getDob()))
                                    .category(searchResults.getCategory()).minority(households.getIsMinority())
                                    .religion(households.getReligion())
                                    .duration(calDuration(visits.getLastMissedPeriodDate(), endTime.getTime())).build();

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


        List<DeliveryDTO> addInList = new ArrayList<>();

        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = ApplicationConstants.df.parse(dashboardFilter.getStartDate().trim());

        } else {
            startTime = ApplicationConstants.df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = ApplicationConstants.df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth());
        }

        List<BabiesBirth> birthList = babiesBirthRepository.findAllByMonth(startTime.getTime(), endTime.getTime(),
                dashboardFilter.getCenterId().trim());


        for (BabiesBirth bb : birthList) {
            DeliveryDTO singleEntry = DeliveryDTO.builder()
                    .startDate(ApplicationConstants.df.format(startTime))
                    .endDate(ApplicationConstants.df.format(endTime))
                    .centerId(bb.getCenterId()).childId(bb.getChildId() == null ? "" : bb.getChildId())
                    .birthType(bb.getBirthType() == null ? "" : bb.getBirthType())
                    .birthPlace(bb.getBirthPlace() == null ? "" : bb.getBirthPlace())
                    .motherId(bb.getMotherMemberId() == null ? "" : bb.getMotherMemberId()).build();
            addInList.add(singleEntry);
        }

        return addInList;
    }

    @Override
    public List<VaccinationRecordsDTO> getVaccinationData(DashboardFilter dashboardFilter) throws ParseException {

        Date startTime = null, endTime = null;

        if (dashboardFilter.getStartDate().trim().length() > 0) {
            startTime = ApplicationConstants.df.parse(dashboardFilter.getStartDate().trim());

        } else {
            startTime = ApplicationConstants.df.parse(commonMethodsService.startDateOfMonth());
        }

        if (dashboardFilter.getEndDate().trim().length() > 0) {
            endTime = ApplicationConstants.df.parse(dashboardFilter.getEndDate().trim());
        } else {
            endTime = ApplicationConstants.df.parse(commonMethodsService.endDateOfMonth());
        }

        Calendar addOneDay = Calendar.getInstance();
        addOneDay.setTime(endTime);
        addOneDay.add(Calendar.DATE, 1);
        endTime = addOneDay.getTime();

        List<VaccinationRecordsDTO> addInList = new ArrayList<>();
        List<Vaccination> vaccinationList = vaccinationRepository.findAllByVaccinationCriteria(startTime, endTime,
                dashboardFilter.getCenterId().trim());

        addOneDay.add(Calendar.DATE, -1);
        endTime = addOneDay.getTime();

        for (Vaccination details : vaccinationList) {
            VaccinationRecordsDTO singleEntry = VaccinationRecordsDTO.builder()
                    .vaccinationCode(details.getVaccinationCode())
                    .centerId(details.getCenterId())
                    .startDate(ApplicationConstants.df.format(startTime))
                    .endDate(ApplicationConstants.df.format(endTime))
                    .centerName(details.getCenterName())
                    .memberId(details.getChildId().isEmpty() ? details.getMotherId() : details.getChildId())
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

            List<FamilyMember> familyDetails = familyMemberRepository.findAllByFamilyId(findHousehold.getFamilyId(),
                    Sort.by(Sort.Direction.DESC, "createdDate"));

            for (FamilyMember fd : familyDetails) {
                fd.setCategory(householdsDTO.getCategory());

                // Update In Anganwadi Children

                List<AnganwadiChildren> ac = anganwadiChildrenRepository.findAllByChildId(fd.getId());

//                for (AnganwadiChildren child : ac) {
//                    child.setMinority(householdsDTO.getIsMinority());
//                    child.setCategory(householdsDTO.getCategory());
//                    anganwadiChildrenRepository.save(child);
//                }
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

            familyMemberRepository.save(findHead);

            return HouseholdsDTO.builder().id(findHousehold.getId()).centerName(findHousehold.getCenterName())
                    .uniqueCode(findHead.getIdType()).uniqueId(findHead.getIdNumber()).headName(findHead.getName())
                    .headDob(householdsDTO.getHeadDob()).totalMembers("").headPic(findHead.getPhoto()).centerId("")
                    .headGender(findHead.getGender()).houseNo(findHousehold.getHouseNo())
                    .mobileNumber(findHead.getMobileNumber()).uniqueIdType(findHead.getIdType())
                    .category(findHousehold.getCategory()).religion(findHousehold.getReligion())
                    .isMinority(findHousehold.getIsMinority()).icdsService(findHousehold.getIcdsService()).build();

        } catch (Exception e) {
            throw new CustomException("Household Not Found");
        }

    }

    @Override
    public FamilyMemberDTO updateHouseHoldMember(FamilyMemberDTO familyMemberDTO) {

        // Validating Death Date
        FamilyMember familyMember = familyMemberRepository.findById(familyMemberDTO.getId()).get();
        Family findFamilyId = familyRepository.findByFamilyId(familyMember.getFamilyId());

        // Validating Mortality Date
        if (familyMemberDTO.getDateOfMortality().length() > 0) {
            try {

                long validatedDate = commonMethodsService.dateChangeToLong(familyMemberDTO.getDateOfMortality());
                log.error("death date : " + validatedDate);
                if (validatedDate < familyMember.getDob()) {
                    throw new CustomException("Mortality Date Can't Be Before Birth Date");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        try {

            Date date = ApplicationConstants.df.parse(familyMemberDTO.getDob());
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

            List<AnganwadiChildren> ac = anganwadiChildrenRepository
                    .findAllByChildIdAndRegisteredTrue(familyMemberDTO.getId());

            if (ac.size() > 0) {
                for (AnganwadiChildren children : ac) {
                    children.setName(familyMemberDTO.getName());
//                    children.setGender(familyMemberDTO.getGender());
//                    children.setDob(familyMemberDTO.getDob());
//                    children.setFatherName(familyMemberDTO.getFatherName());
//                    children.setMotherName(familyMemberDTO.getMotherName());
                    children.setMobileNumber(familyMemberDTO.getMobileNumber());

                    anganwadiChildrenRepository.save(children);
                }
            }


            // Updating in Pregnancy Table

            List<PregnantAndDelivery> findWomen = pregnantAndDeliveryRepository.findAllByMotherMemberId(familyMemberDTO.getId(), Sort.by(Sort.Direction.ASC, "createdDate"));

            for (PregnantAndDelivery pdd : findWomen) {
                pdd.setHusbandName(familyMember.getFatherName());
                pdd.setMotherName(familyMember.getName());
                pregnantAndDeliveryRepository.save(pdd);
            }

            if (familyMember.getDateOfMortality().length() > 0) {
                familyMember.setDeleted(true);
                familyMember.setActive(false);
                commonMethodsService.memberSoftDelete(familyMemberDTO, familyMember.getDateOfMortality());
            }

            familyMemberRepository.save(familyMember);


            return FamilyMemberDTO.builder().id(familyMember.getId()).category(familyMember.getCategory())
                    .name(familyMember.getName()).relationWithOwner(familyMember.getRelationWithOwner())
                    .category(familyMember.getCategory()).gender(familyMember.getGender())
                    .centerName(familyMember.getCenterName()).memberCode(familyMember.getMemberCode())
                    .familyId(familyMember.getFamilyId())
                    .mobileNumber(familyMember.getMobileNumber() == null ? "" : familyMember.getMobileNumber())
                    .idType(familyMember.getIdType() == null ? "" : familyMember.getIdType())
                    .idNumber(familyMember.getIdNumber() == null ? "" : familyMember.getIdNumber())
                    .dob(familyMemberDTO.getDob()).handicap(familyMember.getHandicap())
                    .maritalStatus(familyMember.getMaritalStatus()).stateCode(familyMember.getStateCode())
                    .handicapType(familyMember.getHandicapType()).motherName(familyMember.getMotherName())
                    .fatherName(familyMember.getFatherName()).memberCode(familyMember.getMemberCode())
                    .residentArea(familyMember.getResidentArea()).dateOfArrival(familyMember.getDateOfArrival())
                    .dateOfLeaving(familyMember.getDateOfLeaving()).dateOfMortality(familyMember.getDateOfMortality())
                    .photo(familyMember.getPhoto()).build();

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

            return HouseholdsDTO.builder().id(family.getId()).centerName(family.getCenterName()).uniqueCode("")
                    .uniqueId(familyMember.getIdNumber()).headName(familyMember.getName()).headDob(df.format(date))
                    .totalMembers("").headPic(familyMember.getPhoto()).centerId("").headGender(familyMember.getGender())
                    .houseNo(family.getHouseNo()).mobileNumber(familyMember.getMobileNumber())
                    .uniqueIdType(familyMember.getIdType()).category(family.getCategory())
                    .religion(family.getReligion()).isMinority(family.getIsMinority())
                    .icdsService(family.getIcdsService()).build();

        } catch (Exception e) {
            throw new CustomException("Household Not Found");
        }
    }


//	public void removeFromAnganwadiChildren(String primaryId) {
//
//		List<AnganwadiChildren> removeChild = anganwadiChildrenRepository.findAllByChildId(primaryId);
//
//		if (removeChild.size() > 0) {
//			anganwadiChildrenRepository.deleteAllByChildId(primaryId);
//		}
//
//	}
//
//	public void removeFromAttendance(String primaryId) {
//
//		List<Attendance> removeAttendance = attendanceRepository.findAllByChildId(primaryId);
//		if (removeAttendance.size() > 0) {
//			attendanceRepository.deleteAllByChildId(primaryId);
//		}
//	}
//
//	public void removeFromBabiesBirth(String primaryId) {
//
//		if (babiesBirthRepository.findByChildId(primaryId) != null) {
//			BabiesBirth removeBirthRecords = babiesBirthRepository.findByChildId(primaryId);
//			babiesBirthRepository.deleteById(removeBirthRecords.getId());
//		}
//	}
//
//	public void removeFromWeightTracking(String primaryId) {
//
//		List<WeightTracking> wt = weightTrackingRepository.findByChildId(primaryId,
//				Sort.by(Sort.Direction.DESC, "createdDate"));
//		if (wt.size() > 0) {
//			weightTrackingRepository.deleteByChildId(primaryId);
//		}
//	}
//
//	public void removeFromVisits(String primaryId) {
//		List<Visits> visits = visitsRepository.findAllByMemberId(primaryId);
//		if (visits.size() > 0) {
//			visitsRepository.deleteByMemberId(primaryId);
//		}
//	}
//
//	public void removeFromPregnancyTable(String primaryId) {
//		List<PregnantAndDelivery> ppd = pregnantAndDeliveryRepository.findAllByMotherMemberId(primaryId,
//				Sort.by(Sort.Direction.DESC, "createdDate"));
//		if (ppd.size() > 0) {
//			pregnantAndDeliveryRepository.deleteByMotherMemberId(primaryId);
//		}
//	}

    @Override
    public List<PerVaccineRecord> getVaccinationByChildId(String childId) {

        List<Vaccination> findRecords = vaccinationRepository.findByChildId(childId, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<PerVaccineRecord> addInSingle = new ArrayList<>();

        for (Vaccination lists : findRecords) {
            Date date = new Date(lists.getDate());
            PerVaccineRecord singleList = PerVaccineRecord.builder()
                    .vaccinationCode(lists.getVaccinationCode() == null ? "" : lists.getVaccinationCode())
                    .vaccinationName(commonMethodsService.getVaccineName(lists.getVaccinationCode()))
                    .date(ApplicationConstants.df.format(date)).build();
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

                findFamily.setActive(false);
                findFamily.setDeleted(true);
                familyRepository.save(findFamily);
                boolean deletedStatus = findFamily.isDeleted();

                List<FamilyMember> findMembers = familyMemberRepository.findAllByFamilyId(familyId,
                        Sort.by(Sort.Direction.DESC, "createdDate"));
                String headName = "", headPic = "", headGender = "", headMobile = "";
                Date dob = new Date();


                long totalMembers = findMembers.size();

                if (findMembers.size() > 0) {

                    for (FamilyMember deleteMember : findMembers) {
                        deleteMember.setActive(false);
                        deleteMember.setDeleted(true);
                        familyMemberRepository.save(deleteMember);
                        FamilyMemberDTO familyMemberDTO = modelMapper.map(deleteMember, FamilyMemberDTO.class);

                        commonMethodsService.memberSoftDelete(familyMemberDTO, deleteMember.getId());
                        commonMethodsService.removeFromHouseVisitSchedule(deleteMember.getId());
                        commonMethodsService.removeFromVaccinationSchedule(deleteMember.getId());

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
                }

                // Remove From Stock Distro.
                List<StockDistribution> familyInStock = stockDistributionRepository.findAllByFamilyId(familyId);

                for (StockDistribution fs : familyInStock) {
                    fs.setActive(false);
                    fs.setDeleted(true);
                    stockDistributionRepository.save(fs);
                }

                // Remove From Visits

                List<Visits> checkVisits = visitsRepository.findAllByFamilyId(familyId);

                for (Visits visits : checkVisits) {
                    visits.setActive(false);
                    visits.setDeleted(true);

                    visitsRepository.save(visits);
                }

                return HouseholdsDTO.builder()
                        .id(primaryId)
                        .centerName(centerName)
                        .uniqueCode("")
                        .uniqueId("")
                        .headName(headName)
                        .headDob(ApplicationConstants.df.format(dob))
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

    @Override
    public FamilyMemberDTO deleteFamilyMembers(String memberId, String id) {

        try {
            User verifyUser = userRepository.findById(id).get();
            if (verifyUser.getId().length() > 0 && verifyUser.getRole().equals(ApplicationConstants.USER_ADMIN)) {

                FamilyMember checkMember = familyMemberRepository.findById(memberId).get();

                String primaryId = checkMember.getId();
                String category = checkMember.getCategory() == null ? "" : checkMember.getCategory();
                String name = checkMember.getName() == null ? "" : checkMember.getName();
                String relationWithOwner = checkMember.getRelationWithOwner() == null ? ""
                        : checkMember.getRelationWithOwner();
                String gender = checkMember.getGender() == null ? "" : checkMember.getGender();
                String centerName = checkMember.getCenterName() == null ? "" : checkMember.getCenterName();
                String memberCode = checkMember.getMemberCode() == null ? "" : checkMember.getMemberCode();
                String familyId = checkMember.getFamilyId() == null ? "" : checkMember.getFamilyId();
                String mobileNo = checkMember.getMobileNumber() == null ? "" : checkMember.getMobileNumber();
                String idType = checkMember.getIdType() == null ? "" : checkMember.getIdType();
                String idNumber = checkMember.getIdNumber() == null ? "" : checkMember.getIdNumber();
                String dob = ApplicationConstants.df.format(new Date(checkMember.getDob()));
                String handicap = checkMember.getHandicap() == null ? "" : checkMember.getHandicap();
                String maritalStatus = checkMember.getMaritalStatus() == null ? "" : checkMember.getMaritalStatus();
                String stateCode = checkMember.getStateCode() == null ? "" : checkMember.getStateCode();
                String handicapType = checkMember.getHandicapType() == null ? "" : checkMember.getHandicapType();
                String motherName = checkMember.getMotherName() == null ? "" : checkMember.getMotherName();
                String fatherName = checkMember.getFatherName() == null ? "" : checkMember.getFatherName();
                String residentArea = checkMember.getResidentArea() == null ? "" : checkMember.getResidentArea();
                String dateOfArrival = checkMember.getDateOfArrival() == null ? "" : checkMember.getDateOfArrival();
                String dateOfLeaving = checkMember.getDateOfLeaving() == null ? "" : checkMember.getDateOfLeaving();
                String dateOfMortality = checkMember.getDateOfMortality() == null ? ""
                        : checkMember.getDateOfMortality();
                String photo = checkMember.getPhoto() == null ? "" : checkMember.getPhoto();


                String currentMortalityDate = ApplicationConstants.df.format(new Date());
                FamilyMemberDTO familyMemberDTO = modelMapper.map(checkMember, FamilyMemberDTO.class);

                // Updating Active & Deleted Parameter in Family Member Table
                checkMember.setDateOfMortality(currentMortalityDate);
                checkMember.setDeleted(true);
                checkMember.setActive(false);

                familyMemberRepository.save(checkMember);
                commonMethodsService.memberSoftDelete(familyMemberDTO, currentMortalityDate);

                return FamilyMemberDTO.builder().id(primaryId).category(category).name(name)
                        .relationWithOwner(relationWithOwner).gender(gender).centerName(centerName)
                        .memberCode(memberCode).familyId(familyId).mobileNumber(mobileNo).idType(idType)
                        .idNumber(idType).dob(dob).handicap(handicap).maritalStatus(maritalStatus).stateCode(stateCode)
                        .handicapType(handicapType).motherName(motherName).fatherName(fatherName)
                        .residentArea(residentArea).dateOfArrival(dateOfArrival).dateOfLeaving(dateOfLeaving)
                        .dateOfMortality(dateOfMortality).photo(photo).build();

            } else {
                throw new CustomException("Un-Authorized Access");
            }

        } catch (NullPointerException | NoSuchElementException e) {
            throw new CustomException("Member Not Found or Already Deleted, Please Check Again");
        }
    }

    @Override
    public HouseholdWomenDetails getHouseholdWomenDetails(String centerId) throws ParseException {

        // Count Pregnant Women
        List<PregnantAndDelivery> findPW = pregnantAndDeliveryRepository.findAllPregnantWomen(centerId,
                Sort.by(Sort.Direction.DESC, "createdDate"), true, false);
        HashSet<String> uniqueWomen = new HashSet<>();

        for (PregnantAndDelivery pd : findPW) {
            uniqueWomen.add(pd.getMotherMemberId().trim());
        }

        // Count Dharti Women

        LocalDateTime dhartiDate = LocalDateTime.now().minusMonths(6);
        ZonedDateTime dhartiZdt = ZonedDateTime.of(dhartiDate, ZoneId.systemDefault());
        long convertToMills2 = dhartiZdt.toInstant().toEpochMilli();
        String currentDate = ApplicationConstants.df.format(new Date());

        List<PregnantAndDelivery> dhartiWomen = pregnantAndDeliveryRepository.findAllBeneficiaryDharti(convertToMills2, commonMethodsService.dateChangeToLong(currentDate), centerId, convertToMills2);
        HashSet<String> uniqueDharti = new HashSet<>();

        for (PregnantAndDelivery dharti : dhartiWomen) {
            uniqueDharti.add(dharti.getMotherMemberId().trim());
        }

        // Count New Born Children

        LocalDateTime date = LocalDateTime.now().minusMonths(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();
        log.error("millis :" + convertToMills);

        List<FamilyMember> findChild = familyMemberRepository.findAllByDobCriteria(convertToMills, centerId);

        return HouseholdWomenDetails.builder()
                .pregnantWomen(uniqueWomen.size())
                .newBornBabies(findChild.size())
                .nursingMother(uniqueDharti.size())
                .build();
    }

    private void updatePregnancyHouseVisitSchedule(PregnantAndDelivery pd) {

        LocalDate localDate = Instant.ofEpochMilli(pd.getLastMissedPeriodDate()).atZone(ZoneId.systemDefault())
                .toLocalDate();

        // Visit Type 1. With in 4-6 Months

        HouseVisitSchedule addVisit_1 = HouseVisitSchedule.builder().visitType("1").centerId(pd.getCenterId())
                .centerName(pd.getCenterName()).visitName("TILL_04_06_MONTHS").memberId(pd.getMotherMemberId())
                .dueDate(localDate.plusDays(119).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        HouseVisitSchedule addVisit_2 = HouseVisitSchedule.builder().visitType("1").centerId(pd.getCenterId())
                .centerName(pd.getCenterName()).visitName("TILL_04_06_MONTHS").memberId(pd.getMotherMemberId())
                .dueDate(localDate.plusDays(149).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        HouseVisitSchedule addVisit_3 = HouseVisitSchedule.builder().visitType("1").centerId(pd.getCenterId())
                .centerName(pd.getCenterName()).visitName("TILL_04_06_MONTHS").memberId(pd.getMotherMemberId())
                .dueDate(localDate.plusDays(179).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        // Visit Type 2. With in 7-9 Months

        HouseVisitSchedule addVisit_4 = HouseVisitSchedule.builder().visitType("2").centerId(pd.getCenterId())
                .centerName(pd.getCenterName()).visitName("TILL_07_09_MONTHS").memberId(pd.getMotherMemberId())
                .dueDate(localDate.plusDays(209).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        HouseVisitSchedule addVisit_5 = HouseVisitSchedule.builder().visitType("2").centerId(pd.getCenterId())
                .centerName(pd.getCenterName()).visitName("TILL_07_09_MONTHS").memberId(pd.getMotherMemberId())
                .dueDate(localDate.plusDays(239).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        HouseVisitSchedule addVisit_6 = HouseVisitSchedule.builder().visitType("2").centerId(pd.getCenterId())
                .centerName(pd.getCenterName()).visitName("TILL_07_09_MONTHS").memberId(pd.getMotherMemberId())
                .dueDate(localDate.plusDays(269).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        // Visit Type 3. Day Of Delivery
        HouseVisitSchedule addVisit_7 = HouseVisitSchedule.builder().visitType("3").centerId(pd.getCenterId())
                .centerName(pd.getCenterName()).visitName("DAY_OF_DELIVERY").memberId(pd.getMotherMemberId())
                .dueDate(localDate.plusDays(279).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .build();

        houseVisitScheduleRepository.save(addVisit_1);
        houseVisitScheduleRepository.save(addVisit_2);
        houseVisitScheduleRepository.save(addVisit_3);
        houseVisitScheduleRepository.save(addVisit_4);
        houseVisitScheduleRepository.save(addVisit_5);
        houseVisitScheduleRepository.save(addVisit_6);
        houseVisitScheduleRepository.save(addVisit_7);
    }

    private void updatePregnancyVaccinationSchedule(PregnantAndDelivery pd) {

        LocalDate localDate = Instant.ofEpochMilli(pd.getLastMissedPeriodDate()).atZone(ZoneId.systemDefault())
                .toLocalDate();

        // After 30 days

        vaccinationScheduleRepository
                .save(VaccinationSchedule.builder().code("V-1").vaccinationName("TD-1").vaccinationDate(0)
                        .centerId(pd.getCenterId()).centerName(commonMethodsService.findCenterName(pd.getCenterId()))
                        .dueDate(localDate.plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(pd.getFamilyId()).memberId(pd.getMotherMemberId()).build());

        vaccinationScheduleRepository
                .save(VaccinationSchedule.builder().code("V-2").vaccinationName("TD-2").vaccinationDate(0)
                        .centerId(pd.getCenterId()).centerName(commonMethodsService.findCenterName(pd.getCenterId()))
                        .dueDate(localDate.plusDays(58).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(pd.getFamilyId()).memberId(pd.getMotherMemberId()).build());

        vaccinationScheduleRepository
                .save(VaccinationSchedule.builder().code("V-3").vaccinationName("TD_BOOSTER").vaccinationDate(0)
                        .centerId(pd.getCenterId()).centerName(commonMethodsService.findCenterName(pd.getCenterId()))
                        .dueDate(localDate.plusDays(72).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
                        .familyId(pd.getFamilyId()).memberId(pd.getMotherMemberId()).build());

    }

    private boolean checkPregnantWithInYear(PregnantAndDeliveryDTO pregnantAndDeliveryDTO, long missedPeriodDate) {

        HashSet<String> recentData = new HashSet<>();

        List<PregnantAndDelivery> findMother = pregnantAndDeliveryRepository.findAllByMotherMemberId(
                pregnantAndDeliveryDTO.getMotherMemberId(), Sort.by(Sort.Direction.DESC, "createdDate"));
        long dod = 0;

        for (PregnantAndDelivery checkMother : findMother) {
            if (recentData.add(checkMother.getMotherMemberId())) {
                dod = checkMother.getDateOfDelivery();
            }
        }

        Instant instant = Instant.ofEpochMilli(missedPeriodDate);
        LocalDateTime date = instant.atZone(ZoneId.systemDefault()).toLocalDateTime().minusMonths(12);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        return convertToMills <= dod;

    }

    @Override
    public PregnantAndDeliveryDTO registerPregnantWomen(PregnantAndDeliveryDTO pregnantAndDeliveryDTO, String centerId)
            throws ParseException {

        long missedPeriodDate = ApplicationConstants.df.parse(pregnantAndDeliveryDTO.getLastMissedPeriodDate()).getTime();

        Date currentTime = new Date();
        String formatToString = ApplicationConstants.df.format(currentTime.getTime());
        Date formatToTime = ApplicationConstants.df.parse(formatToString);
        long timestamp = formatToTime.getTime();

        FamilyMember fm = familyMemberRepository.findById(pregnantAndDeliveryDTO.getMotherMemberId()).get();
        Family family = familyRepository.findByFamilyId(fm.getFamilyId());

        if (checkPregnantWithInYear(pregnantAndDeliveryDTO, missedPeriodDate)) {
            throw new CustomException("The User " + fm.getName()
                    + " had an Delivery With in 12 Months. Please Contact Support Team, For Adding the Data.");
        }

        PregnantAndDelivery pd = PregnantAndDelivery.builder()
                .familyId(pregnantAndDeliveryDTO.getFamilyId() == null ? "" : pregnantAndDeliveryDTO.getFamilyId())
                .motherMemberId(pregnantAndDeliveryDTO.getMotherMemberId() == null ? ""
                        : pregnantAndDeliveryDTO.getMotherMemberId())
                .motherName(fm.getName() == null ? "" : fm.getName()).dob(fm.getDob())
                .yojana(pregnantAndDeliveryDTO.getYojana() == null ? new String[0] : pregnantAndDeliveryDTO.getYojana())
                .husbandName(fm.getFatherName() == null ? "" : fm.getFatherName())
                .childName(pregnantAndDeliveryDTO.getChildName() == null ? "" : pregnantAndDeliveryDTO.getChildName())
                .childGender(
                        pregnantAndDeliveryDTO.getChildGender() == null ? "" : pregnantAndDeliveryDTO.getChildGender())
                .category(fm.getCategory() == null ? "" : fm.getCategory())
                .religion(family.getReligion() == null ? "" : family.getReligion())
                .houseNumber(family.getHouseNo() == null ? "" : family.getHouseNo())
                .profilePic(fm.getPhoto() == null ? "" : fm.getPhoto()).regDate(timestamp).centerId(centerId)
                .centerName(commonMethodsService.findCenterName(centerId))
                .noOfChild(pregnantAndDeliveryDTO.getNoOfChild()).lastMissedPeriodDate(missedPeriodDate)
                .dateOfDelivery(0).build();

        pregnantAndDeliveryRepository.save(pd);

        updatePregnancyHouseVisitSchedule(pd);
        updatePregnancyVaccinationSchedule(pd);

        return PregnantAndDeliveryDTO.builder().id(pd.getId())
                .familyId(pregnantAndDeliveryDTO.getFamilyId() == null ? "" : pregnantAndDeliveryDTO.getFamilyId())
                .motherMemberId(pregnantAndDeliveryDTO.getMotherMemberId() == null ? ""
                        : pregnantAndDeliveryDTO.getMotherMemberId())
                .motherName(fm.getName() == null ? "" : fm.getName()).dob(ApplicationConstants.df.format(new Date(fm.getDob())))
                .husbandName(fm.getFatherName() == null ? "" : fm.getFatherName()).yojana(pd.getYojana())
                .profilePic(fm.getPhoto() == null ? "" : fm.getPhoto())
                .childName(pregnantAndDeliveryDTO.getChildName() == null ? "" : pregnantAndDeliveryDTO.getChildName())
                .childGender(
                        pregnantAndDeliveryDTO.getChildGender() == null ? "" : pregnantAndDeliveryDTO.getChildGender())
                .category(fm.getCategory() == null ? "" : fm.getCategory())
                .religion(family.getReligion() == null ? "" : family.getReligion())
                .houseNumber(family.getHouseNo() == null ? "" : family.getHouseNo()).regDate(ApplicationConstants.df.format(formatToTime))
                .centerId(centerId).isDeleted(pd.isDeleted()).centerName(commonMethodsService.findCenterName(centerId))
                .noOfChild(pregnantAndDeliveryDTO.getNoOfChild()).lastMissedPeriodDate(ApplicationConstants.df.format(missedPeriodDate))
                .dateOfDelivery(pregnantAndDeliveryDTO.getDateOfDelivery() == null ? ""
                        : pregnantAndDeliveryDTO.getDateOfDelivery())
                .build();
    }

    @Override
    public List<PregnantAndDeliveryDTO> getAllPregnantWomenDetails(String centerId) {
        List<PregnantAndDelivery> findPD = pregnantAndDeliveryRepository.findAllPregnantWomen(centerId,
                Sort.by(Sort.Direction.DESC, "createdDate"), true, false);
        List<PregnantAndDeliveryDTO> addInList = new ArrayList<>();

        Set<String> uniqueMother = new HashSet<>();

        if (findPD.size() > 0) {
            for (PregnantAndDelivery pd : findPD) {
                if (uniqueMother.add(pd.getMotherMemberId())) {
                    String dod = "";
                    if (pd.getDateOfDelivery() > 0) {
                        dod = ApplicationConstants.df.format(pd.getDateOfDelivery());
                    }

                    PregnantAndDeliveryDTO singleEntry = PregnantAndDeliveryDTO.builder().id(pd.getId())
                            .familyId(pd.getFamilyId() == null ? "" : pd.getFamilyId())
                            .centerId(pd.getCenterId() == null ? "" : pd.getCenterId())
                            .centerName(pd.getCenterName() == null ? "" : pd.getCenterName())
                            .regDate(ApplicationConstants.df.format(pd.getRegDate())).noOfChild(pd.getNoOfChild()).isDeleted(pd.isDeleted())
                            .yojana(pd.getYojana() == null ? new String[0] : pd.getYojana())
                            .motherMemberId(pd.getMotherMemberId() == null ? "" : pd.getMotherMemberId())
                            .motherName(pd.getMotherName() == null ? "" : pd.getMotherName())
                            .dob(ApplicationConstants.df.format(new Date(pd.getDob())))
                            .husbandName(pd.getHusbandName() == null ? "" : pd.getHusbandName())
                            .profilePic(pd.getProfilePic() == null ? "" : pd.getProfilePic())
                            .childName(pd.getChildName() == null ? "" : pd.getChildName())
                            .childGender(pd.getChildGender() == null ? "" : pd.getChildGender())
                            .category(pd.getCategory() == null ? "" : pd.getCategory())
                            .religion(pd.getReligion() == null ? "" : pd.getReligion())
                            .misCarriageDate(pd.getMisCarriageDate() > 0 ? ApplicationConstants.df.format(pd.getMisCarriageDate()) : "-")
                            .houseNumber(pd.getHouseNumber() == null ? "" : pd.getHouseNumber()).dateOfDelivery(dod)
                            .lastMissedPeriodDate(ApplicationConstants.df.format(pd.getLastMissedPeriodDate())).build();
                    addInList.add(singleEntry);
                }
            }

        }
        return addInList;
    }

    private void updateFromHouseVisitSchedule(String id, String lastMissedPeriod) throws ParseException {
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        if (!StringUtils.isEmpty(id)) {
            List<HouseVisitSchedule> hvs = houseVisitScheduleRepository.findAllByMemberId(id);
            if (hvs.size() > 0) {
                for (HouseVisitSchedule updateVisits : hvs) {

                    Date date = df.parse(lastMissedPeriod);
                    long missedPeriod = date.getTime();

                    LocalDate localDate = Instant.ofEpochMilli(missedPeriod).atZone(ZoneId.systemDefault())
                            .toLocalDate();

                    switch (updateVisits.getVisitType().trim()) {
                        case "1":
                            updateVisits.setDueDate(localDate.plusDays(180).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "2":
                            updateVisits.setDueDate(localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "3":
                            updateVisits.setDueDate(localDate.plusDays(280).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;
                    }
                    houseVisitScheduleRepository.save(updateVisits);
                }
            }
        }

    }

    private Boolean checkMisCarriageDate(PregnantAndDelivery pd, String misCarriageDate) {

        boolean isValid = false;
        try {

            LocalDate localDate = Instant.ofEpochMilli(pd.getLastMissedPeriodDate()).atZone(ZoneId.systemDefault()).toLocalDate();

            long after9MonthsDate = localDate.plusDays(270).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            Date date = ApplicationConstants.df.parse(misCarriageDate);

            log.error("MisCarriage Date : " + date.getTime());
            log.error("Pd Date : " + pd.getLastMissedPeriodDate());
            log.error("after9Months : " + after9MonthsDate);

            if (date.getTime() >= pd.getLastMissedPeriodDate() && date.getTime() <= after9MonthsDate) {
                isValid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isValid;
    }

    @Override
    public PregnantAndDeliveryDTO updatePregnantWomenDetails(PregnantAndDeliveryDTO pregnantAndDeliveryDTO,
                                                             String centerId) throws ParseException {

        if (StringUtils.isEmpty(pregnantAndDeliveryDTO.getId())) {
            throw new CustomException("Id Not Passed, Please Check");
        }

        String[] yojanaList = new String[0];

//        long dod = df.parse(pregnantAndDeliveryDTO.getDateOfDelivery()).getTime();

        PregnantAndDelivery findPD = pregnantAndDeliveryRepository.findById(pregnantAndDeliveryDTO.getId()).get();
        long millisDate = 0;

        // Check MisCarriage Date is Valid
        if (pregnantAndDeliveryDTO.getMisCarriageDate().length() > 0) {
            Date date = ApplicationConstants.df.parse(pregnantAndDeliveryDTO.getMisCarriageDate());
            if (!checkMisCarriageDate(findPD, pregnantAndDeliveryDTO.getMisCarriageDate())) {
                throw new CustomException("MisCarriage Date Should Be Between 9 Months Of LMP Date");
            }
            millisDate = date.getTime();
        }

        long missedPeriodDate = ApplicationConstants.df.parse(pregnantAndDeliveryDTO.getLastMissedPeriodDate()).getTime();
        try {

            if (pregnantAndDeliveryDTO.getMisCarriageDate().length() > 0) {


                // Removing House visits & vaccination entries from db, If Mis-charged Date is Entered
                commonMethodsService.removeFromVaccinationSchedule(findPD.getMotherMemberId());
                commonMethodsService.removeFromHouseVisitSchedule(findPD.getMotherMemberId());

            } else {
                yojanaList = pregnantAndDeliveryDTO.getYojana() == null ? new String[0] : pregnantAndDeliveryDTO.getYojana();
            }


            findPD.setChildName(pregnantAndDeliveryDTO.getChildName());
            findPD.setChildGender(pregnantAndDeliveryDTO.getChildGender());
            findPD.setNoOfChild(pregnantAndDeliveryDTO.getNoOfChild());
            findPD.setMisCarriageDate(millisDate);
//            findPD.setDateOfDelivery(dod);
            findPD.setYojana(yojanaList);
            findPD.setLastMissedPeriodDate(findPD.getMisCarriageDate() > 0 ? 0 : missedPeriodDate);
            findPD.setMisCarriageReason(pregnantAndDeliveryDTO.getMisCarriageReason() == null ? findPD.getMisCarriageReason() == null ? "" : findPD.getMisCarriageReason() : pregnantAndDeliveryDTO.getMisCarriageReason());
            pregnantAndDeliveryRepository.save(findPD);

            updateFromHouseVisitSchedule(findPD.getMotherMemberId(), pregnantAndDeliveryDTO.getLastMissedPeriodDate());

            return PregnantAndDeliveryDTO.builder().id(findPD.getId())
                    .familyId(findPD.getFamilyId() == null ? "" : findPD.getFamilyId())
                    .centerId(findPD.getCenterId() == null ? "" : findPD.getCenterId())
                    .centerName(findPD.getCenterName() == null ? "" : findPD.getCenterName())
                    .regDate(ApplicationConstants.df.format(findPD.getRegDate())).noOfChild(findPD.getNoOfChild())
                    .dob(ApplicationConstants.df.format(findPD.getDob()))
                    .husbandName(findPD.getHusbandName() == null ? "" : findPD.getHusbandName())
                    .profilePic(findPD.getProfilePic() == null ? "" : findPD.getProfilePic()).yojana(findPD.getYojana())
                    .motherMemberId(findPD.getMotherMemberId() == null ? "" : findPD.getMotherMemberId())
                    .motherName(findPD.getMotherName() == null ? "" : findPD.getMotherName())
                    .childName(findPD.getChildName() == null ? "" : findPD.getChildName())
                    .childGender(findPD.getChildGender() == null ? "" : findPD.getChildGender())
                    .category(findPD.getCategory() == null ? "" : findPD.getCategory())
                    .religion(findPD.getReligion() == null ? "" : findPD.getReligion())
                    .houseNumber(findPD.getHouseNumber() == null ? "" : findPD.getHouseNumber())
                    .dateOfDelivery(findPD.getMisCarriageDate() > 0 ? "-" : ApplicationConstants.df.format(findPD.getDateOfDelivery()))
                    .isDeleted(findPD.isDeleted())
                    .misCarriageDate(findPD.getMisCarriageDate() > 0 ? ApplicationConstants.df.format(findPD.getMisCarriageDate()) : "-")
                    .misCarriageReason(findPD.getMisCarriageReason() == null ? "" : findPD.getMisCarriageReason())
                    .lastMissedPeriodDate(findPD.getMisCarriageDate() > 0 ? "-" : ApplicationConstants.df.format(missedPeriodDate)).build();

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
            commonMethodsService.removeFromHouseVisitSchedule(id);
            commonMethodsService.removeFromVaccinationSchedule(id);

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            PregnantAndDelivery findPD = pregnantAndDeliveryRepository.findById(id).get();

            findPD.setDeleted(true);

            PregnantAndDeliveryDTO deletePD = PregnantAndDeliveryDTO.builder().id(findPD.getId())
                    .familyId(findPD.getFamilyId() == null ? "" : findPD.getFamilyId())
                    .centerId(findPD.getCenterId() == null ? "" : findPD.getCenterId())
                    .centerName(findPD.getCenterName() == null ? "" : findPD.getCenterName())
                    .regDate(df.format(findPD.getRegDate())).noOfChild(findPD.getNoOfChild())
                    .isDeleted(findPD.isDeleted())
                    .motherMemberId(findPD.getMotherMemberId() == null ? "" : findPD.getMotherMemberId())
                    .dateOfDelivery(df.format(findPD.getDateOfDelivery()))
                    .lastMissedPeriodDate(df.format(findPD.getLastMissedPeriodDate())).build();

            pregnantAndDeliveryRepository.deleteById(id);

            return deletePD;

        } catch (NullPointerException | NoSuchElementException e) {
            throw new CustomException("Details Not Found or Already Deleted, Please Check");
        }

    }

    @Override
    public List<WomenListByPeriodDateDTO> getWomenListByPeriodDate(String centerId) {

        List<PregnantAndDelivery> findWomen = pregnantAndDeliveryRepository.findAllPregnantWomen(centerId, Sort.by(Sort.Direction.ASC, "createdDate"), true, false);
        HashSet<String> uniqueWomenList = new HashSet<>();
        List<WomenListByPeriodDateDTO> addInList = new ArrayList<>();

        if (findWomen.size() > 0) {

            for (PregnantAndDelivery womenList : findWomen) {

                // Fetching From Family Member & Family

                Optional<FamilyMember> findMember = familyMemberRepository.findById(womenList.getMotherMemberId());
                if (findMember.isPresent()) {
                    Family findFamily = familyRepository.findByFamilyId(findMember.get().getFamilyId());

                    PregnantAndDeliveryDTO pdd = modelMapper.map(womenList, PregnantAndDeliveryDTO.class);

                    if (uniqueWomenList.add(womenList.getMotherMemberId()) && womenList.getLastMissedPeriodDate() > 0) {
                        WomenListByPeriodDateDTO singePDList = WomenListByPeriodDateDTO.builder().id(womenList.getId())
                                .familyId(womenList.getFamilyId() == null ? "" : womenList.getFamilyId())
                                .centerId(womenList.getCenterId() == null ? "" : womenList.getCenterId())
                                .centerName(commonMethodsService.findCenterName(centerId))
                                .regDate(ApplicationConstants.df.format(womenList.getRegDate())).noOfChild(womenList.getNoOfChild())
                                .memberId(womenList.getMotherMemberId() == null ? "" : womenList.getMotherMemberId())
                                .name(findMember.get().getName() == null ? "" : findMember.get().getName())
                                .profilePic(findMember.get().getPhoto() == null ? "" : findMember.get().getPhoto())
                                .husbandName(womenList.getHusbandName() == null ? "" : womenList.getHusbandName())
                                .dob(ApplicationConstants.df.format(new Date(findMember.get().getDob())))
                                .childName(womenList.getChildName() == null ? "" : womenList.getChildName())
                                .childGender(womenList.getChildGender() == null ? "" : womenList.getChildGender())
                                .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                                .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion())
                                .houseNo(findFamily.getHouseNo() == null ? "" : findFamily.getHouseNo())
                                .dateOfDelivery(ApplicationConstants.df.format(womenList.getDateOfDelivery())).isDeleted(womenList.isDeleted())
                                .lastMissedPeriodDate(ApplicationConstants.df.format(womenList.getLastMissedPeriodDate())).build();

                        addInList.add(singePDList);

                    }

                }

            }

        }
        return addInList;
    }

    @Override
    public List<NewBornChildDTO> getNewBornChildRecords(String centerId) throws ParseException {


        LocalDateTime date = LocalDateTime.now().minusMonths(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        Set<String> uniqueRecords = new HashSet<>();

        long convertToMills = zdt.toInstant().toEpochMilli();
//		log.error("time "+convertToMills);

        List<BabiesBirth> birthList = babiesBirthRepository.findAllByDobCriteria(convertToMills, centerId);

        List<NewBornChildDTO> addInList = new ArrayList<>();
        try {
            if (birthList.size() > 0) {
                for (BabiesBirth child : birthList) {
                    String birthType = "", birthPlace = "", motherMeemberId = "", motherPhoto = "", srNo = "";
                    FamilyMember findChild = familyMemberRepository.findById(child.getChildId()).get();

                    for (BabiesBirth lists : birthList) {
                        birthPlace = lists.getBirthPlace();
                        birthType = lists.getBirthType();
                        srNo = lists.getSrNo();
                        motherMeemberId = lists.getMotherMemberId();

                        List<FamilyMember> findMotherDetails = familyMemberRepository
                                .findByFamilyIdAndName(findChild.getFamilyId().trim(), findChild.getMotherName().trim());

                        for (FamilyMember fm : findMotherDetails) {
                            motherPhoto = fm.getPhoto().trim();
                        }
                    }

                    Family familyDetails = familyRepository.findByFamilyId(findChild.getFamilyId());
                    if (uniqueRecords.add(child.getChildId())) {
                        NewBornChildDTO singleEntry = NewBornChildDTO.builder()
                                .id(findChild.getId())
                                .name(findChild.getName() == null ? "" : findChild.getName())
                                .motherPhoto(motherPhoto)
                                .motherName(findChild.getMotherName() == null ? "" : findChild.getMotherName())
                                .fatherName(findChild.getFatherName() == null ? "" : findChild.getFatherName())
                                .houseNumber(familyDetails.getHouseNo() == null ? "" : familyDetails.getHouseNo())
                                .relationWithOwner(findChild.getRelationWithOwner() == null ? "" : findChild.getRelationWithOwner())
                                .dob(ApplicationConstants.df.format(findChild.getDob()))
                                .srNo(srNo)
                                .birthPlace(birthPlace)
                                .birthType(birthType)
                                .familyId(findChild.getFamilyId() == null ? "" : findChild.getFamilyId())
                                .motherMemberId(motherMeemberId)
                                .gender(findChild.getGender() == null ? "" : findChild.getGender())
                                .centerId(findChild.getCenterId() == null ? "" : findChild.getCenterId())
                                .centerName(commonMethodsService.findCenterName(centerId))
                                .visitFor("")
                                .visitType("")
                                .firstWeight(child.getFirstWeight() == null ? "" : child.getFirstWeight())
                                .visitRound("")
                                .height(child.getHeight() == null ? "" : child.getHeight())
                                .build();
                        addInList.add(singleEntry);
                    }

                }
            }
        } catch (Exception e) {

        }

        return addInList;
    }

    private void updateNewBornHouseVisitSchedule(String id, long dob) throws ParseException {

        if (!StringUtils.isEmpty(id)) {
            List<HouseVisitSchedule> hvs = houseVisitScheduleRepository.findAllByMemberId(id);
            if (hvs.size() > 0) {
                for (HouseVisitSchedule updateVisits : hvs) {

                    LocalDate localDate = Instant.ofEpochMilli(dob).atZone(ZoneId.systemDefault()).toLocalDate();

                    switch (updateVisits.getVisitType().trim()) {
                        case "4":
                            updateVisits.setDueDate(
                                    localDate.plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
                            break;

                        case "5":
                            updateVisits.setDueDate(
                                    localDate.plusDays(30).atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli());
                            break;

                        case "6":
                            updateVisits.setDueDate(localDate.plusDays(150).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "7":
                            updateVisits.setDueDate(localDate.plusDays(240).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "8":
                            updateVisits.setDueDate(localDate.plusDays(330).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "9":
                            updateVisits.setDueDate(localDate.plusDays(510).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "10":
                            updateVisits.setDueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;
                    }

                    houseVisitScheduleRepository.save(updateVisits);

                }
            }
        }
    }

    private void updateNewBornVaccinationSchedule(String id, long dob) {
        if (!StringUtils.isEmpty(id)) {
            List<VaccinationSchedule> hs = vaccinationScheduleRepository.findAllByMemberId(id);
            if (hs.size() > 0) {

                for (VaccinationSchedule vs : hs) {
                    LocalDate localDate = Instant.ofEpochMilli(dob).atZone(ZoneId.systemDefault()).toLocalDate();

                    switch (vs.getCode().trim()) {
                        case "1":
                            vs.setDueDate(dob);
                            break;

                        case "22":
                            vs.setDueDate(dob);
                            break;

                        case "8":
                            vs.setDueDate(dob);
                            break;

                        case "9":
                            vs.setDueDate(dob);
                            break;

                        case "5":
                            vs.setDueDate(dob);
                            break;

                        case "2":
                            vs.setDueDate(dob);
                            break;

                        case "10":
                            vs.setDueDate(localDate.plusDays(180).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "6":
                            vs.setDueDate(localDate.plusDays(180).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "3":
                            vs.setDueDate(localDate.plusDays(180).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "11":
                            vs.setDueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "7":
                            vs.setDueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "4":
                            vs.setDueDate(localDate.plusDays(365).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "14":
                            vs.setDueDate(localDate.plusDays(540).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "13":
                            vs.setDueDate(localDate.plusDays(540).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "12":
                            vs.setDueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                        case "15":
                            vs.setDueDate(localDate.plusDays(720).atStartOfDay(ZoneId.systemDefault()).toInstant()
                                    .toEpochMilli());
                            break;

                    }
                    vaccinationScheduleRepository.save(vs);
                }

            }
        }

    }

    @Override
    public NewBornChildDTO updateNewBornChildRecords(BirthPlaceDTO birthPlaceDTO) {

        if (StringUtils.isEmpty(birthPlaceDTO.getId())) {
            throw new CustomException("Id Not Passed, Please Check");
        }

        try {
            HashSet<String> uniqueWeight = new HashSet<>();

            BabiesBirth bb = babiesBirthRepository.findById(birthPlaceDTO.getId()).get();

            Date date = ApplicationConstants.df.parse(birthPlaceDTO.getDob());
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
            findChild.setRelationWithOwner(birthPlaceDTO.getRelationWithOwner() == null ? findChild.getRelationWithOwner() == null ? "" : findChild.getRelationWithOwner() : birthPlaceDTO.getRelationWithOwner());
            findChild.setGender(birthPlaceDTO.getGender());

            familyMemberRepository.save(findChild);

            // Saving in Weight Tracking Table

            List<WeightTracking> wt = weightTrackingRepository.findTopByChildIdAndIsActiveTrueAndDeletedFalse(bb.getChildId(),
                    Sort.by(Sort.Direction.ASC, "createdDate"));

            for (WeightTracking tracking : wt) {
                if (uniqueWeight.add(tracking.getChildId())) {
                    tracking.setHeight(birthPlaceDTO.getHeight());
                    tracking.setWeight(birthPlaceDTO.getFirstWeight());
                    weightTrackingRepository.save(tracking);
                }
            }

            updateNewBornHouseVisitSchedule(bb.getChildId(), bb.getDob());
            updateNewBornVaccinationSchedule(bb.getChildId(), bb.getDob());

            FamilyMember findMotherDetails = familyMemberRepository.findById(bb.getMotherMemberId()).get();
            Family family = familyRepository.findByFamilyId(bb.getFamilyId());

            return NewBornChildDTO.builder().id(bb.getId()).name(bb.getName() == null ? "" : bb.getName())
                    .childId(bb.getChildId())
                    .motherPhoto(findMotherDetails.getPhoto())
                    .motherName(findMotherDetails.getName() == null ? "" : findMotherDetails.getName())
                    .houseNumber(family.getHouseNo() == null ? "" : family.getHouseNo())
                    .relationWithOwner(findChild.getRelationWithOwner() == null ? "" : findChild.getRelationWithOwner())
                    .dob(ApplicationConstants.df.format(bb.getDob()))
                    .fatherName(findMotherDetails.getFatherName() == null ? "" : findMotherDetails.getFatherName())
                    .srNo(birthPlaceDTO.getSrNo() == null ? "" : birthPlaceDTO.getSrNo())
                    .birthPlace(bb.getBirthPlace() == null ? "" : bb.getBirthPlace())
                    .birthType(bb.getBirthType() == null ? "" : bb.getBirthType())
                    .familyId(bb.getFamilyId() == null ? "" : bb.getFamilyId())
                    .motherMemberId(bb.getMotherMemberId())
                    .gender(bb.getGender() == null ? "" : bb.getGender())
                    .centerId(bb.getCenterId() == null ? "" : bb.getCenterId()).centerName(bb.getCenterName())
                    .visitFor("")
                    .visitType("")
                    .firstWeight(bb.getFirstWeight() == null ? "" : bb.getFirstWeight())
                    .visitRound("")
                    .height(bb.getHeight() == null ? "" : bb.getHeight())
                    .build();

        } catch (NullPointerException | NoSuchElementException | ParseException e) {
            throw new CustomException("Child Not Found.");
        }

    }

    private List<NewBornChildDTO> childDetails(String motherId, String houseNo) {

        List<NewBornChildDTO> addInList = new ArrayList<>();
        List<BabiesBirth> findBabies = babiesBirthRepository.findAllByMotherMemberIdAndIsActiveTrueAndDeletedFalse(motherId);

        for (BabiesBirth bb : findBabies) {
            log.error("childId : " + bb.getChildId());

            FamilyMember findChild = familyMemberRepository.findById(bb.getChildId()).get();
            FamilyMember findMother = familyMemberRepository.findById(bb.getMotherMemberId()).get();

            addInList.add(NewBornChildDTO
                    .builder()
                    .id(bb.getId())
                    .childId(bb.getChildId())
                    .name(findChild.getName() == null ? "" : findChild.getName())
                    .motherPhoto(findMother.getPhoto() == null ? "" : findMother.getPhoto())
                    .motherName(findChild.getMotherName() == null ? "" : findChild.getMotherName())
                    .fatherName(findChild.getFatherName() == null ? "" : findChild.getFatherName())
                    .houseNumber(houseNo == null ? "" : houseNo)
                    .relationWithOwner(findChild.getRelationWithOwner() == null ? "" : findChild.getRelationWithOwner())
                    .dob(ApplicationConstants.df.format(findChild.getDob()))
                    .srNo(bb.getSrNo() == null ? "" : bb.getSrNo())
                    .birthPlace(bb.getBirthPlace())
                    .birthType(bb.getBirthType())
                    .familyId(findChild.getFamilyId() == null ? "" : findChild.getFamilyId())
                    .motherMemberId(bb.getMotherMemberId())
                    .gender(findChild.getGender() == null ? "" : findChild.getGender())
                    .centerId(findChild.getCenterId() == null ? "" : findChild.getCenterId())
                    .centerName(commonMethodsService.findCenterName(findChild.getCenterId()))
                    .visitFor("")
                    .visitType("")
                    .firstWeight(bb.getFirstWeight() == null ? "" : bb.getFirstWeight())
                    .visitRound("")
                    .height(bb.getHeight() == null ? "" : bb.getHeight())
                    .build());

        }

        return addInList;

    }

    @Override
    public List<MotherChildDTO> getDhatriDetails(String centerId) {

        LocalDateTime date = LocalDateTime.now().minusMonths(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();
        List<MotherChildDTO> addInList = new ArrayList<>();
        Set<String> uniqueMotherId = new HashSet<>();

        List<PregnantAndDelivery> findPD = pregnantAndDeliveryRepository.findAllByDeliveryCriteria(centerId,
                convertToMills, Sort.by(Sort.Direction.DESC, "dateOfDelivery"));

        for (PregnantAndDelivery pd : findPD) {
            if (uniqueMotherId.add(pd.getMotherMemberId())) {

                MotherChildDTO singleEntry = MotherChildDTO.builder().id(pd.getId())
                        .familyId(pd.getFamilyId() == null ? "" : pd.getFamilyId())
                        .centerId(pd.getCenterId() == null ? "" : pd.getCenterId())
                        .centerName(pd.getCenterName() == null ? "" : pd.getCenterName())
                        .regDate(ApplicationConstants.df.format(pd.getRegDate()))
                        .motherMemberId(pd.getMotherMemberId() == null ? "" : pd.getMotherMemberId())
                        .motherName(pd.getMotherName() == null ? "" : pd.getMotherName())
                        .husbandName(pd.getHusbandName() == null ? "" : pd.getHusbandName())
                        .profilePic(pd.getProfilePic() == null ? "" : pd.getProfilePic())
                        .category(pd.getCategory() == null ? "" : pd.getCategory())
                        .religion(pd.getReligion() == null ? "" : pd.getReligion())
                        .houseNumber(pd.getHouseNumber() == null ? "" : pd.getHouseNumber())
                        .dateOfDelivery(ApplicationConstants.df.format(pd.getDateOfDelivery()))
                        .lastMissedPeriodDate(ApplicationConstants.df.format(pd.getLastMissedPeriodDate()))
                        .yojana(pd.getYojana() == null ? new String[0] : pd.getYojana())
                        .childDetails(childDetails(pd.getMotherMemberId(), pd.getHouseNumber()))
                        .build();
                addInList.add(singleEntry);
            }
        }
        return addInList;
    }

    @Override
    public List<FamilyChildrenDetails> getAllChildrenDetails(String centerId) {

        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();
        List<FamilyChildrenDetails> addInList = new ArrayList<>();
//        log.error("age Limit : "+convertToMills);
        List<FamilyMember> findChildren = familyMemberRepository.findAllFamilyChildrenByCenterId(centerId,
                convertToMills, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (FamilyMember fm : findChildren) {
            Family findFamily = familyRepository.findByFamilyId(fm.getFamilyId());
            FamilyChildrenDetails singleChild = FamilyChildrenDetails.builder()
                    .name(fm.getName() == null ? "" : fm.getName())
                    .dob(ApplicationConstants.df.format(fm.getDob()))
                    .photo(fm.getPhoto() == null ? "" : fm.getPhoto())
                    .gender(fm.getGender() == null ? "" : fm.getGender())
                    .motherName(fm.getMotherName() == null ? "" : fm.getMotherName())
                    .fatherName(fm.getFatherName() == null ? "" : fm.getFatherName())
                    .houseNo(findFamily.getHouseNo() == null ? "" : findFamily.getHouseNo())
                    .category(findFamily.getCategory() == null ? "" : findFamily.getCategory())
                    .religion(findFamily.getReligion() == null ? "" : findFamily.getReligion()).build();
            addInList.add(singleChild);
        }

        return addInList;
    }

    @Override
    public DeleteBornChildDTO deleteNewBornChildRecords(String id) {

        try {
            String name = "", relationWithOwner = "", dob = "", birthPlace = "", birthType = "", familyId = "",
                    gender = "", centerId = "", firstWeight = "", height = "", centerName = "";
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            commonMethodsService.removeFromHouseVisitSchedule(id);
            commonMethodsService.removeFromVaccinationSchedule(id);

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
                centerName = findMember.getCenterName() == null ? "" : findMember.getCenterName();
                familyMemberRepository.deleteById(id);
            }

            if (babiesBirthRepository.findById(id).isPresent()) {
                findChild = babiesBirthRepository.findById(id).get();
                birthPlace = findChild.getBirthPlace() == null ? "" : findChild.getBirthPlace();
                birthType = findChild.getBirthType() == null ? "" : findChild.getBirthType();
                firstWeight = findChild.getFirstWeight() == null ? "" : findChild.getFirstWeight();
                height = findChild.getHeight() == null ? "" : findChild.getHeight();

                babiesBirthRepository.deleteById(findChild.getId());
            }

            deleteRecord = DeleteBornChildDTO.builder().id(id).name(name).relationWithOwner(relationWithOwner).dob(dob)
                    .birthPlace(birthPlace).birthType(birthType).familyId(familyId).gender(gender).centerId(centerId)
                    .firstWeight(firstWeight).height(height).deleted(true).centerName(centerName).build();

            List<WeightTracking> checkInWeightTable = weightTrackingRepository.findAllByChildId(findMember.getId(),
                    Sort.by(Sort.Direction.DESC, "createdDate"));

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

        for (FamilyMember familyMember : findFemales) {

            List<Visits> checkInVisits = visitsRepository.findAllByLadiesBeneficiaryCriteria(familyMember.getId());
            Family findHouseholds = familyRepository.findByFamilyId(familyMember.getFamilyId());

            if (checkInVisits.size() > 0) {

                BeneficiaryList addSingle = BeneficiaryList.builder()
                        .category(familyMember.getCategory() == null ? "" : familyMember.getCategory())
                        .id(familyMember.getId() == null ? "" : familyMember.getId())
                        .name(familyMember.getName() == null ? "" : familyMember.getName())
                        .dob(ApplicationConstants.df.format(new Date(familyMember.getDob())))
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
                        .totalMembers("").headName("").headGender("").headDob("").headPic("").build();

                addInList.add(addSingle);
            }

        }

        if (findChildren.size() > 0) {

            for (FamilyMember fc : findChildren) {
                Family findHouseholds = familyRepository.findByFamilyId(fc.getFamilyId());
                BeneficiaryList addSingle = BeneficiaryList.builder()
                        .category(fc.getCategory() == null ? "" : fc.getCategory())
                        .name(fc.getName() == null ? "" : fc.getName()).id(fc.getId() == null ? "" : fc.getId())
                        .dob(ApplicationConstants.df.format(new Date(fc.getDob())))
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
                        .totalMembers("").headName("").headGender("").headDob("").headPic("").build();

                addInList.add(addSingle);
            }
        }
        return addInList;
    }

    private List<BloodTestCasesDTO> bloodTestArrayList(Visits findDetails, String currentMember) {

        List<BloodTestCasesDTO> reportList = new ArrayList<>();

        List<BloodTestTracking> findReports = bloodTestTrackingRepository.findAllByMotherIdAndVisitTypeAndVisitRound(findDetails.getMotherId(), findDetails.getVisitType(), findDetails.getVisitRound());

        if (findReports.size() > 0) {
            for (BloodTestTracking btt : findReports) {
                reportList.add(BloodTestCasesDTO.builder().date(ApplicationConstants.df.format(btt.getDate()))
                        .result(btt.getResult() == null ? "" : btt.getResult())
                        .testCode(btt.getTestCode() == null ? "" : btt.getTestCode())
                        .memberId(btt.getMemberId().equals("") ? currentMember : btt.getMemberId())
                        .name("").build());
            }
        }
        return reportList;
    }

    private List<ChildWeightDTO> weightArrayList(Visits findDetails) {
        List<ChildWeightDTO> weightList = new ArrayList<>();
        List<WeightTracking> findWeights = weightTrackingRepository.findAllByMotherIdAndVisitTypeAndVisitRound(
                findDetails.getMotherId(), findDetails.getVisitType(), findDetails.getVisitRound());

        if (findWeights.size() > 0) {
            for (WeightTracking wt : findWeights) {
                weightList.add(ChildWeightDTO.builder()
                        .childId(wt.getChildId())
                        .weight(wt.getWeight())
                        .height(wt.getHeight())
                        .date(ApplicationConstants.df.format(wt.getDate()))
                        .bmi(commonMethodsService.calBMI(wt.getWeight(), wt.getHeight())).build());
            }
        }

        return weightList;
    }

    private List<VaccinationDTO> vaccinationArrayList(Visits findDetails) {

        List<VaccinationDTO> vaccinationList = new ArrayList<>();

        List<Vaccination> findVaccine = vaccinationRepository.findAllByMotherIdAndVisitTypeAndVisitRound(
                findDetails.getMotherId(), findDetails.getVisitType(), findDetails.getVisitRound());

        if (findVaccine.size() > 0) {
            for (Vaccination vc : findVaccine) {
                vaccinationList.add(VaccinationDTO.builder().id(vc.getId()).vaccinationCode(vc.getVaccinationCode())
                        .vaccinationName("").message("").build());
            }

        }
        return vaccinationList;

    }

    @Override
    public List<MemberVisits> getMemberVisitDetails(String memberId, String centerId) {

        List<Visits> findMember = visitsRepository.findAllByMemberIdAndCenterId(memberId, centerId);
        List<MemberVisits> addInList = new ArrayList<>();

        HashSet<String> uniqueMember = new HashSet<>();
        for (Visits checkDetails : findMember) {

            if (uniqueMember.add(checkDetails.getVisitType())) {
                MemberVisits memberVisits = MemberVisits.builder().visitType(checkDetails.getVisitType())
                        .visitArray(visitArrayList(checkDetails.getMemberId(), checkDetails.getVisitType())).build();

                addInList.add(memberVisits);

            }
        }

        return addInList;
    }

    @Override
    public List<VaccinationDTO> getAllVaccinationName() {
        // return vaccinationNameRepository.findAll();
        List<VaccinationName> listForReturn = vaccinationNameRepository.findAll();
        List<VaccinationDTO> addinList = new ArrayList<>();

        for (VaccinationName vn : listForReturn) {
            addinList.add(VaccinationDTO.builder()
                    .id(vn.getId())
                    .vaccinationName(vn.getVaccineName())
                    .vaccinationCode(vn.getVaccineCode())
                    .message("")
                    .build());
        }
        return addinList;
    }

    private List<BeneficiariesDTO> beneficiaryChildren() {
        // Children less than 6 Years
        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        List<FamilyMember> members = familyMemberRepository.findAllByDob(convertToMills);
        List<BeneficiariesDTO> result = new ArrayList<>();

        // Get Children Records
        if (members.size() > 0) {
            for (FamilyMember fm : members) {
                String centerName = fm.getCenterName();

                if (Arrays.stream(ApplicationConstants.ignoreCenters).noneMatch(fm.getCenterId().trim()::equals)) {
                    Family family = familyRepository.findByFamilyId(fm.getFamilyId());
                    result.add(BeneficiariesDTO.builder()
                            .id(fm.getId())
                            .name(fm.getName())
                            .familyId(fm.getFamilyId())
                            .fatherName(fm.getFatherName())
                            .motherName(fm.getMotherName())
                            .category(fm.getCategory())
                            .dob(this.commonMethodsService.dateChangeToString(fm.getDob()))
                            .religion(family.getReligion())
                            .centerName(fm.getCenterName())
                            .build());
                }
            }
        }
        return result;
    }

    private List<BeneficiariesDTO> beneficiaryPregnant() {
        List<PregnantAndDelivery> findPregnantWomen = pregnantAndDeliveryRepository.findAllByPregnancyCriteria(1672511400000L, 1703961000000L, "");
        List<BeneficiariesDTO> result = new ArrayList<>();

        for (PregnantAndDelivery pdd : findPregnantWomen) {
            if (Arrays.stream(ApplicationConstants.ignoreCenters).noneMatch(pdd.getCenterId().trim()::equals)) {
                if (familyMemberRepository.existsById(pdd.getMotherMemberId())) {
                    FamilyMember pm = familyMemberRepository.findById(pdd.getMotherMemberId()).get();
                    Family family = familyRepository.findByFamilyId(pm.getFamilyId());
                    result.add(BeneficiariesDTO.builder()
                            .id(pm.getId())
                            .name(pm.getName())
                            .familyId(pm.getFamilyId())
                            .fatherName(pm.getFatherName())
                            .motherName(pm.getMotherName())
                            .category(pm.getCategory())
                            .dob(this.commonMethodsService.dateChangeToString(pm.getDob()))
                            .religion(family.getReligion())
                            .centerName(pm.getCenterName())
                            .build());
                }
            }

        }
        return result;
    }

    private List<BeneficiariesDTO> beneficiaryDharti() {

        LocalDateTime dhartiDate = LocalDateTime.now().minusMonths(6);
        ZonedDateTime dhartiZdt = ZonedDateTime.of(dhartiDate, ZoneId.systemDefault());
        long convertToMills2 = dhartiZdt.toInstant().toEpochMilli();

        List<BeneficiariesDTO> result = new ArrayList<>();
        List<PregnantAndDelivery> findDhartiWomen = pregnantAndDeliveryRepository.findAllBeneficiaryDharti(1672511400000L, 1703961000000L, "", convertToMills2);

        for (PregnantAndDelivery dharti : findDhartiWomen) {
            if (Arrays.stream(ApplicationConstants.ignoreCenters).noneMatch(dharti.getCenterId().trim()::equals)) {
                if (familyMemberRepository.existsById(dharti.getMotherMemberId())) {
                    FamilyMember pm = familyMemberRepository.findById(dharti.getMotherMemberId()).get();
                    Family family = familyRepository.findByFamilyId(pm.getFamilyId());
                    result.add(BeneficiariesDTO.builder()
                            .id(pm.getId())
                            .name(pm.getName())
                            .familyId(pm.getFamilyId())
                            .fatherName(pm.getFatherName())
                            .motherName(pm.getMotherName())
                            .category(pm.getCategory())
                            .dob(this.commonMethodsService.dateChangeToString(pm.getDob()))
                            .religion(family.getReligion())
                            .centerName(pm.getCenterName())
                            .build());
                }
            }

        }
        return result;
    }

    @Override
    public List<BeneficiariesDTO> getBeneficiariesReport() {
        List<BeneficiariesDTO> addInList = new ArrayList<>();

        // Get Children Records
        addInList.addAll(beneficiaryChildren());

        // Get Pregnant Women Records
        addInList.addAll(beneficiaryPregnant());

        // Beneficiary dharti
        addInList.addAll(beneficiaryDharti());

        return addInList;
    }
}
