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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Year;
import java.time.ZoneId;
import java.time.ZonedDateTime;
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

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository, ModelMapper modelMapper, FamilyMemberRepository familyMemberRepository,
                             VisitsRepository visitsRepository, WeightTrackingRepository weightTrackingRepository, VaccinationRepository vaccinationRepository,
                             PregnantAndDeliveryRepository pregnantAndDeliveryRepository, BabiesBirthRepository babiesBirthRepository) {
        this.familyRepository = familyRepository;
        this.modelMapper = modelMapper;
        this.familyMemberRepository = familyMemberRepository;
        this.visitsRepository = visitsRepository;
        this.weightTrackingRepository = weightTrackingRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.pregnantAndDeliveryRepository = pregnantAndDeliveryRepository;
        this.babiesBirthRepository = babiesBirthRepository;
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
    public List<householdsHeadList> getAllHouseholds(String centerName) {
        List<householdsHeadList> addInList = new ArrayList<>();
        List<Family> familyList = familyRepository.findAllByCenterName(centerName,Sort.by(Sort.Direction.DESC, "createdDate"));

        // Get Head of Family Details

        for (Family getHouseholds : familyList) {
            String headName = "", dob = "", pic = "", gender = "";
            int male = 0, female = 0, children = 0;


            List<FamilyMember> findHeadDetails = familyMemberRepository.findAllByFamilyId(getHouseholds.getFamilyId().trim(), Sort.by(Sort.Direction.DESC, "createdDate"));
            boolean headFound = false;

            for (int i = 0; i <= findHeadDetails.size(); i++) {

                for (FamilyMember checkDetails : findHeadDetails) {

                    long getDob = checkDetails.getDob();
                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    Date date = new Date(getDob);


                    if (checkDetails.getRelationWithOwner().equalsIgnoreCase("0")) {
                        headName = checkDetails.getName();
                        dob = df.format(date);
                        pic = checkDetails.getPhoto();
                        gender = checkDetails.getGender();
                        break;
                    } else {
                        headName = checkDetails.getName();
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

        return addInList;
    }

    @Override
    public HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO, String centerName) throws ParseException {

        String name = householdsDTO.getHeadName() == null ? "" : householdsDTO.getHeadName();
        String headDob = householdsDTO.getHeadDob() == null ? "" : householdsDTO.getHeadDob();
        String centerID = householdsDTO.getCenterId() == null ? "" : householdsDTO.getCenterId();
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
                .centerName(centerName)
                .uniqueIdType(uniqueIdType)
                .centerId(centerID)
                .uniqueCode(uniqueCode)
                .uniqueId(uniqueId)
                .centerName(centerName)
                .category(category)
                .religion(religion)
                .isMinority(isMinority)
                .icdsService(icdsService)
                .build();
        familyRepository.save(saveFamily);

        FamilyMember saveInMember = FamilyMember.builder()
                .name(name)
                .dob(mills)
                .centerName(centerName)
                .maritalStatus("1")
                .familyId(familyId)
                .idNumber(uniqueId)
                .idType(uniqueIdType)
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
                .centerName(centerName)
                .totalMembers("")
                .headPic(headPic)
                .centerId(centerName)
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
    public FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO, String centerName) throws ParseException {

        String name = familyMemberDTO.getName() == null ? "" : familyMemberDTO.getName();
        String relationShip = familyMemberDTO.getRelationWithOwner() == null ? "" : familyMemberDTO.getRelationWithOwner();
        String gender = familyMemberDTO.getGender() == null ? "" : familyMemberDTO.getGender();
        String dob = familyMemberDTO.getDob() == null ? "" : familyMemberDTO.getDob();
        String martialStatus = familyMemberDTO.getMaritalStatus() == null ? "" : familyMemberDTO.getMaritalStatus();
        String stateCode = familyMemberDTO.getStateCode() == null ? "" : familyMemberDTO.getStateCode();
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
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        Date date = df.parse(familyMemberDTO.getDob());
        long mills = date.getTime();
        String headCategory = "";
        List<Family> findHod = familyRepository.findAllByFamilyId(familyMemberDTO.getFamilyId());
        for (Family getFamilyId : findHod) {
            headCategory = getFamilyId.getCategory();
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
                .centerName(centerName)
                .motherName(motherName)
                .fatherName(fatherName)
                .memberCode(memberCode)
                .memberCode(code)
                .category(category.equals("") ? headCategory : category)
                .maritalStatus(martialStatus)
                .stateCode(stateCode)
                .handicapType(handicapType)
                .residentArea(area)
                .dateOfArrival(arrivalDate)
                .dateOfLeaving(leavingDate)
                .dateOfMortality(deathDate)
                .photo(photo)
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
                .handicapType(handicapType)
                .motherName(motherName)
                .fatherName(fatherName)
                .memberCode(memberCode)
                .residentArea(area)
                .dateOfArrival(arrivalDate)
                .dateOfLeaving(leavingDate)
                .dateOfMortality(deathDate)
                .photo(photo)
                .build();
    }

    @Override
    public List<FamilyMemberDTO> getFamilyMembers(String familyId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }
        List<FamilyMemberDTO> addInList = new ArrayList<>();
        List<FamilyMember> getMembers = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.ASC, "createdDate"));
        String gender = "";
        for (FamilyMember passDetails : getMembers) {

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
                    .centerName(passDetails.getCenterName())
                    .category(passDetails.getCategory()) //join
                    .idType(passDetails.getIdType())
                    .idNumber(passDetails.getIdNumber())
                    .maritalStatus(passDetails.getMaritalStatus() == null ? "" : passDetails.getMaritalStatus())
                    .stateCode(passDetails.getStateCode() == null ? "" : passDetails.getStateCode())
//                    .handicapType(passDetails.getHandicapType() == null ? "" : passDetails.getHandicapType())
                    .residentArea(passDetails.getResidentArea() == null ? "" : passDetails.getResidentArea())
                    .dateOfArrival(passDetails.getDateOfArrival() == null ? "" : passDetails.getDateOfArrival())
                    .dateOfLeaving(passDetails.getDateOfLeaving() == null ? "" : passDetails.getDateOfLeaving())
                    .dateOfMortality(passDetails.getDateOfLeaving() == null ? "" : passDetails.getDateOfLeaving())
                    .build();
            addInList.add(singleList);
        }

        return addInList;
    }

    @Override
    public VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO, String centerName) throws ParseException {


//        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("UTC+5.30"));

        Date date = new Date();
        String formattedDate = df.format(date);
        log.info("Formatted Date : " + formattedDate);

        Date AfterFormat = df.parse(formattedDate);
        long millis = AfterFormat.getTime();
        log.info("Date in Millis  : " + millis);

        Visits saveVisitDetails = Visits.builder()
                .memberId(visitsDetailsDTO.getMemberId() == null ? "" : visitsDetailsDTO.getMemberId())
                .centerName(centerName)
                .visitType(visitsDetailsDTO.getVisitType() == null ? "" : visitsDetailsDTO.getVisitType())
                .visitFor(visitsDetailsDTO.getVisitFor() == null ? "" : visitsDetailsDTO.getVisitFor())
                .description(visitsDetailsDTO.getDescription() == null ? "" : visitsDetailsDTO.getDescription())
                .visitRound(visitsDetailsDTO.getVisitRound() == null ? "" : visitsDetailsDTO.getVisitRound())
                .latitude(visitsDetailsDTO.getLatitude() == null ? "" : visitsDetailsDTO.getLatitude())
                .longitude(visitsDetailsDTO.getLongitude() == null ? "" : visitsDetailsDTO.getLongitude())
                .visitDateTime(millis)
                .childDob(0)
                .build();
        visitsRepository.save(saveVisitDetails);

        return modelMapper.map(saveVisitDetails, VisitsDetailsDTO.class);
    }

    @Override
    public WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO) {

        WeightTracking saveRecord = WeightTracking.builder()
                .familyId(weightRecordsDTO.getFamilyId() == null ? "" : weightRecordsDTO.getFamilyId())
                .childId(weightRecordsDTO.getChildId() == null ? "" : weightRecordsDTO.getChildId())
                .date(weightRecordsDTO.getDate() == null ? "" : weightRecordsDTO.getDate())
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight())
                .build();

        weightTrackingRepository.save(saveRecord);
        return modelMapper.map(saveRecord, WeightRecordsDTO.class);
    }

    @Override
    public List<WeightRecordsDTO> getWeightRecords(String familyId, String childId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }

        if (StringUtils.isEmpty(childId.trim())) {
            throw new CustomException("Child Id Is Missed, Please Check!!");
        }

        List<WeightTracking> findChildRecords = weightTrackingRepository.findAllByFamilyIdAndChildId(familyId, childId, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<WeightRecordsDTO> addList = new ArrayList<>();

        for(WeightTracking passRecords : findChildRecords){
            WeightRecordsDTO getRecords =  modelMapper.map(passRecords, WeightRecordsDTO.class);
            addList.add(getRecords);
        }
        return addList;
    }

    @Override
    public List<WeightRecordsDTO> getAllChildWeightRecords(String familyId) {

        if (StringUtils.isEmpty(familyId.trim())) {
            throw new CustomException("Family Id Is Missed, Please Check!!");
        }
        List<WeightTracking> findAllChildRecords = weightTrackingRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.ASC, "createdDate"));

        List<WeightRecordsDTO> addList = new ArrayList<>();

        return null;
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

            default:
                LocalDateTime minus60Months = LocalDateTime.now().minusMonths(60);
                String temp60Date = DateTimeFormatter.ofPattern("dd-MM-yyyy").format(minus60Months);
                DateFormat df60 = new SimpleDateFormat("dd-MM-yyyy");

                Date last60Months = df60.parse(temp60Date);
                startMillis = last60Months.getTime();
                break;
        }


        return startMillis;

    }


    @Override
    public MPRDTO getMPRRecords(String month, String duration, String category, String centerName) throws ParseException {

        month = month == null ? "" : month;
        duration = duration == null ? "" : duration;
        category = category == null ? "" : category;
        long male = 0, female = 0, dharti = 0, pregnant = 0, birth = 0, mortality = 0;
        MPRDTO MprCounts = new MPRDTO();

        List<FamilyMember> findByCenter = familyMemberRepository.findAllByCenterNameAndRecordForMonthAndCategory(centerName, month, category);
        long startDate = 0, endDate = 0;

        for (FamilyMember formatDetails : findByCenter) {


            startDate = MPRDurationStartDate(duration);
            endDate = MPRDurationEndDate(duration);
            long deathInMillis = 0L;

            if (formatDetails.getDateOfMortality().trim().length() > 0) {
                DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date deathDate = df.parse(formatDetails.getDateOfMortality());
                deathInMillis = deathDate.getTime();
            }


            if (formatDetails.getDob() >= startDate && formatDetails.getDob() < endDate && formatDetails.getGender().trim().equals("1")) {
                male++;
                log.error("Male Name " + formatDetails.getName());
            }

            if (formatDetails.getDob() >= startDate && formatDetails.getDob() < endDate && formatDetails.getGender().trim().equals("2")) {
                female++;
                log.error("Female Name " + formatDetails.getName());
            }

            if (formatDetails.getDob() >= startDate && formatDetails.getDob() < endDate) {
                birth++;
                log.error("Birth Name " + formatDetails.getName());
            }

            if (deathInMillis >= startDate && deathInMillis < endDate) {
                mortality++;
                log.error("Birth Name " + formatDetails.getName());
            }

        }


        List<Visits> findDhatri = visitsRepository.findAllByCenterName(centerName);
        HashSet<String> uniqueMemberId = new HashSet<>();

        for (Visits checkDetails : findDhatri) {

            long getMills = checkDetails.getVisitDateTime();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);
            String[] splitMonth = df.format(date).split("-");
            String getMonth = splitMonth[1].replace("0", "");

            if (month.length() > 0) {

                if (getMonth.trim().equals(month) && checkDetails.getVisitType().equals("3") && checkDetails.getChildDob() >= startDate && checkDetails.getChildDob() < endDate) {
                    if (uniqueMemberId.add(checkDetails.getMemberId())) {
                        dharti++;
                    }
                }
            } else {
                if (checkDetails.getVisitType().equals("3") && checkDetails.getChildDob() >= startDate && checkDetails.getChildDob() < endDate) {
                    if (uniqueMemberId.add(checkDetails.getMemberId())) {
                        dharti++;
                    }
                }
            }
        }

        MprCounts = MPRDTO.builder()
                .male(male)
                .female(female)
                .birth(birth)
                .mortality(mortality)
                .dharti(dharti)
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
    public List<VaccinationRecords> getVaccinationRecords() {

        List<Vaccination> vaccinationList = vaccinationRepository.findAll(Sort.by(Sort.Direction.ASC, "createdDate"));
        List<VaccinationRecords> addList = new ArrayList<>();

        for (Vaccination getVaccinationDetails : vaccinationList) {
            String motherName = "", dob = "";
            List<PregnantAndDelivery> findChildDetails = pregnantAndDeliveryRepository.findByFamilyId(getVaccinationDetails.getFamilyId());
            for (PregnantAndDelivery getChildDetails : findChildDetails) {
                motherName = getChildDetails.getMotherName();
                dob = getChildDetails.getDateOfDelivery().toString();
            }

            VaccinationRecords singleEntry = VaccinationRecords.builder()
                    .name(getVaccinationDetails.getChildName())
                    .motherName(motherName)
                    .age(dob)
                    .gender(getVaccinationDetails.getGender())
                    .photo(getVaccinationDetails.getPhoto())
                    .vaccination(getVaccinationDetails.getVaccinationName())
                    .build();

            addList.add(singleEntry);
        }

        return addList;
    }

    @Override
    public List<HouseholdsChildren> getAllHouseholdsChildren(String centerName) throws ParseException {

        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date parseTime = df.parse(convertToString);

        List<HouseholdsChildren> addList = new ArrayList<>();


        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Time "+convertToMills);

        List<FamilyMember> findAllChildren = familyMemberRepository.findAllByDobAndCenterName(convertToMills, centerName);
        String gender = "";

        if (findAllChildren.size() > 0) {
            String minority = "", category = "";
            for (FamilyMember member : findAllChildren) {

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
    public List<FemaleMembersDTO> getHouseholdsFemaleDetails(String centerName) {

        List<FamilyMember> findFemales = familyMemberRepository.findAllByGenderAndCenterName(centerName);
        List<FemaleMembersDTO> addList = new ArrayList<>();

        for (FamilyMember checkAge : findFemales) {
            int getYear = 0;
            String husbandName = "", houseNo = "";

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
                    centerName = getDetails.getCenterId();
                    houseNo = getDetails.getHouseNo();
                }

                husbandName = checkAge.getFatherName();


                FemaleMembersDTO addMember = FemaleMembersDTO.builder()
                        .name(checkAge.getName())
                        .centerName(centerName)
                        .husbandName(husbandName == null ? "" : husbandName)
                        .houseNo(houseNo)
                        .memberId(checkAge.getId())
                        .profilePic(checkAge.getPhoto())
                        .dob(df.format(checkAge.getDob()))
                        .build();

                addList.add(addMember);
            }
        }


        return addList;
    }


    @Override
    public List<HouseVisitDTO> getHouseVisitListing(String centerName) {

        List<Visits> checkRounds = visitsRepository.findAllByCenterName(centerName);
        List<HouseVisitDTO> addInList = new ArrayList<>();
        HashSet<String> memberId = new HashSet<>();

        for (Visits visitsDetails : checkRounds) {
            FamilyMember checkMembers = familyMemberRepository.findById(visitsDetails.getMemberId()).get();

            String husband_FatherName = "", houseNo = "";


            long millis = checkMembers.getDob();
            Date date = new Date(millis);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            husband_FatherName = checkMembers.getFatherName();

            List<Family> checkHouseDetails = familyRepository.findAllByFamilyId(checkMembers.getFamilyId());

            for (Family findFamily : checkHouseDetails) {
                houseNo = findFamily.getHouseNo();
                centerName = findFamily.getCenterName();
            }

            if (memberId.add(visitsDetails.getMemberId())) {
                HouseVisitDTO addSingle = HouseVisitDTO.builder()
                        .name(checkMembers.getName() == null ? "" : checkMembers.getName())
                        .dob(df.format(date))
                        .husbandName(husband_FatherName == null ? "" : husband_FatherName)
                        .houseNo(houseNo)
                        .memberId(checkMembers.getId())
                        .centerName(centerName == null ? "" : centerName)
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
    public List<MemberVisits> getMemberVisitDetailsLatest(String memberId, String centerName) {


        List<MemberVisits> addInList = new ArrayList<>();
        MemberVisits memberVisits = new MemberVisits();
        HashSet<String> uniqueMember = new HashSet<>();
        int count = 0;

        for (int i = 1; i <= 10; i++) {
            List<Visits> findMember = visitsRepository.findAllByMemberIdAndCenterNameAndVisitType(memberId, centerName, String.valueOf(i));
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

    @Override
    public List<BirthPlaceDTO> saveBirthDetails(BirthPlaceDTO birthDetails, String centerName) throws ParseException {
        List<BirthPlaceDTO> addInList = new ArrayList<>();


        // Find Family Details

        FamilyMember searchFamilyId = familyMemberRepository.findById(birthDetails.getMotherMemberId()).get();
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


        // Save in Birth Table

        BabiesBirth saveDetails = BabiesBirth.builder()
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .birthPlace(birthDetails.getBirthPlace() == null ? "" : birthDetails.getBirthPlace())
                .birthType(birthDetails.getBirthType() == null ? "" : birthDetails.getBirthType())
                .familyId(searchFamilyId.getFamilyId())
                .motherMemberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .gender(birthDetails.getGender() == null ? "" : birthDetails.getGender())
                .centerId(centerName)
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight())
                .firstWeight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .build();
        babiesBirthRepository.save(saveDetails);

        // Save in Family Member Table

        FamilyMember addMember = FamilyMember.builder()
                .familyId(searchFamilyId.getFamilyId())
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .category(searchFamilyId.getCategory())
                .motherName(saveDetails.getName())
                .fatherName(headName)
                .maritalStatus("2")
                .recordForMonth(getMonth)
                .mobileNumber(searchFamilyId.getMobileNumber())
                .stateCode(searchFamilyId.getStateCode())
                .centerName(centerName)
                .relationWithOwner(birthDetails.getRelationWithOwner() == null ? "" : birthDetails.getRelationWithOwner())
                .gender(birthDetails.getGender())
                .dob(mills)
                .build();
        familyMemberRepository.save(addMember);

        // Save in Visit
        Date visitDate = new Date();

        DateFormat visitDf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String convertToString = visitDf.format(new Date());
        Date visitTime = visitDf.parse(convertToString);
        long timestamp = visitTime.getTime();


        Visits updateRecord = Visits.builder()
                .visitFor(birthDetails.getVisitFor())
                .visitType(birthDetails.getVisitType())
                .visitRound(birthDetails.getVisitRound())
                .memberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .centerName(centerName)
                .childDob(mills)
                .description("")
                .longitude("")
                .latitude("")
                .visitDateTime(timestamp)
                .build();

        visitsRepository.save(updateRecord);


        BirthPlaceDTO singleEntry = BirthPlaceDTO.builder()
                .name(birthDetails.getName() == null ? "" : birthDetails.getName())
                .relationWithOwner(birthDetails.getRelationWithOwner() == null ? "" : birthDetails.getRelationWithOwner())
                .dob(birthDetails.getDob())
                .birthPlace(birthDetails.getBirthPlace() == null ? "" : birthDetails.getBirthPlace())
                .birthType(birthDetails.getBirthType() == null ? "" : birthDetails.getBirthType())
                .familyId(searchFamilyId.getFamilyId() == null ? "" : searchFamilyId.getFamilyId())
                .motherMemberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .gender(birthDetails.getGender() == null ? "" : birthDetails.getGender())
                .centerId(centerName)
                .visitFor(birthDetails.getVisitFor())
                .visitType(birthDetails.getVisitType())
                .firstWeight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight())
                .build();
        addInList.add(singleEntry);

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
