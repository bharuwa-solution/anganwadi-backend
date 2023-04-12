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
    private final AnganwadiChildrenRepository anganwadiChildrenRepository;
    private final AnganwadiCenterRepository anganwadiCenterRepository;

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository, ModelMapper modelMapper, FamilyMemberRepository familyMemberRepository,
                             VisitsRepository visitsRepository, WeightTrackingRepository weightTrackingRepository, VaccinationRepository vaccinationRepository,
                             PregnantAndDeliveryRepository pregnantAndDeliveryRepository, BabiesBirthRepository babiesBirthRepository,
                             AnganwadiChildrenRepository anganwadiChildrenRepository, AnganwadiCenterRepository anganwadiCenterRepository) {
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

        return addInList;
    }


    @Override
    public HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO, String centerId, String centerName) throws ParseException {

        String name = householdsDTO.getHeadName() == null ? "" : householdsDTO.getHeadName();
        String headDob = householdsDTO.getHeadDob() == null ? "" : householdsDTO.getHeadDob();
        String centerID = householdsDTO.getCenterId() == null ? "" : householdsDTO.getCenterId();
        String centerNames = householdsDTO.getCenterName() == null ? "" : householdsDTO.getCenterName();
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
                    .centerName(passDetails.getCenterName())
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
        String visitCat = "", familyId = "";
        List<Family> findCat = familyRepository.findAllByFamilyId(visitsDetailsDTO.getMemberId());

        for (Family getCat : findCat) {
            visitCat = getCat.getCategory();
            familyId = getCat.getFamilyId();
        }

        Visits saveVisitDetails = Visits.builder()
                .memberId(visitsDetailsDTO.getMemberId() == null ? "" : visitsDetailsDTO.getMemberId())
                .familyId(familyId)
                .centerName(centerName)
                .visitType(visitsDetailsDTO.getVisitType() == null ? "" : visitsDetailsDTO.getVisitType())
                .visitFor(visitsDetailsDTO.getVisitFor() == null ? "" : visitsDetailsDTO.getVisitFor())
                .description(visitsDetailsDTO.getDescription() == null ? "" : visitsDetailsDTO.getDescription())
                .visitRound(visitsDetailsDTO.getVisitRound() == null ? "" : visitsDetailsDTO.getVisitRound())
                .latitude(visitsDetailsDTO.getLatitude() == null ? "" : visitsDetailsDTO.getLatitude())
                .longitude(visitsDetailsDTO.getLongitude() == null ? "" : visitsDetailsDTO.getLongitude())
                .visitDateTime(millis)
                .category(visitCat)
                .childDob(0)
                .build();
        visitsRepository.save(saveVisitDetails);

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
                .centerName(centerName)
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight())
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
                .photo(findDetails.getPhoto() == null ? "" : weightRecordsDTO.getPhoto())
                .date(df.format(date))
                .centerName(centerName)
                .weight(weightRecordsDTO.getWeight() == null ? "" : weightRecordsDTO.getWeight())
                .height(weightRecordsDTO.getHeight() == null ? "" : weightRecordsDTO.getHeight())
                .build();
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
                    .motherName(findChildDetails.getMotherName())
                    .dob(df.format(findChildDetails.getDob()))
                    .photo(findChildDetails.getPhoto())
                    .date(df.format(date))
                    .centerName(passRecords.getCenterName())
                    .weight(passRecords.getWeight() == null ? "" : passRecords.getWeight())
                    .height(passRecords.getHeight() == null ? "" : passRecords.getHeight())
                    .build();

            addList.add(getRecords);
        }
        return addList;
    }

    @Override
    public List<WeightRecordsDTO> getAllChildWeightRecords(String centerId) {

        if (StringUtils.isEmpty(centerId.trim())) {
            throw new CustomException("center Details Are Missed, Please Check!!");
        }

        LocalDateTime minus6Years = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(minus6Years, ZoneId.systemDefault());
        long convertToMills = zdt.toInstant().toEpochMilli();

        HashSet<String> uniqueChildId = new HashSet<>();
        List<WeightRecordsDTO> addInList = new ArrayList<>();
        List<FamilyMember> findAllChildRecords = familyMemberRepository.findAllByCenterIdAndDob(centerId, convertToMills, Sort.by(Sort.Direction.DESC, "createdDate"));

        for (FamilyMember tracking : findAllChildRecords) {

            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            List<WeightTracking> findChildDetails = weightTrackingRepository.findAllByChildId(tracking.getId(),Sort.by(Sort.Direction.DESC, "createdDate"));

            Date date = new Date();
            for(WeightTracking weightDetails :  findChildDetails) {
                date = new Date(weightDetails.getDate());
            }


            if (uniqueChildId.add(tracking.getChildId())) {
                WeightRecordsDTO singleEntry = WeightRecordsDTO.builder()
                        .familyId(tracking.getFamilyId() == null ? "" : tracking.getFamilyId())
                        .childId(tracking.getChildId() == null ? "" : tracking.getChildId())
                        .name(findChildDetails.getName())
                        .houseNo(findFamilyDetails.getHouseNo() == null ? "" : findFamilyDetails.getHouseNo())
                        .gender(findChildDetails.getGender())
                        .motherName(findChildDetails.getMotherName())
                        .dob(df.format(findChildDetails.getDob()))
                        .photo(findChildDetails.getPhoto())
                        .date(df.format(date))
                        .centerName(centerName)
                        .weight(tracking.getWeight() == null ? "" : tracking.getWeight())
                        .height(tracking.getHeight() == null ? "" : tracking.getHeight())
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

            if (formatDetails.getDateOfMortality().trim().length() > 0 && (deathInMillis >= startDate && deathInMillis < endDate)) {
                mortality++;
                log.error("Birth Name " + formatDetails.getName());
            }

        }


        List<Visits> findDhatri = visitsRepository.findAllByCenterNameAndCategory(centerName, category);
        HashSet<String> uniqueDhartiMemberId = new HashSet<>();
        HashSet<String> uniquePregnantMemberId = new HashSet<>();
        List<Visits> findPregnant = visitsRepository.findAllByCenterNameAndVisitTypeAndCategory(centerName, duration, category);
        String visitCat = "";


        // Find Dharti
        for (Visits checkDetails : findDhatri) {

            long getMills = checkDetails.getVisitDateTime();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);
            String[] splitMonth = df.format(date).split("-");
            String getMonth = splitMonth[1].replace("0", "");


            if (month.length() > 0) {

                if (getMonth.trim().equals(month) && checkDetails.getVisitType().equals("3") && checkDetails.getChildDob() >= startDate && checkDetails.getChildDob() < endDate) {
                    if (uniqueDhartiMemberId.add(checkDetails.getMemberId())) {
                        dharti++;
                    }
                }

            } else {
                if (checkDetails.getVisitType().equals("3") && checkDetails.getChildDob() >= startDate && checkDetails.getChildDob() < endDate) {
                    if (uniqueDhartiMemberId.add(checkDetails.getMemberId())) {
                        dharti++;
                    }
                }
            }

        }

        // Find Pregnant

        for (Visits preg : findPregnant) {

            long getMills = preg.getVisitDateTime();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);
            String[] splitMonth = df.format(date).split("-");
            String getMonth = splitMonth[1].replace("0", "");

            if (month.length() > 0) {

                if (getMonth.trim().equals(month)) {
                    if (uniquePregnantMemberId.add(preg.getMemberId())) {
                        log.error("MemberId " + preg.getMemberId());
                        pregnant++;
                    }
                }
            } else {
                if (uniquePregnantMemberId.add(preg.getMemberId())) {
                    log.error("MemberId " + preg.getMemberId());
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
        List<Vaccination> vaccinationList = new ArrayList<>();
        HashSet<String> uniqueFamilyId = new HashSet<>();
        String code = vaccineCode == null ? "" : vaccineCode;

        if (code.trim().length() > 0) {
            vaccinationList = vaccinationRepository.findAllByVaccinationCodeAndCenterId(vaccineCode, centerId, Sort.by(Sort.Direction.ASC, "createdDate"));
        } else {
            vaccinationList = vaccinationRepository.findAllByCenterId(centerId, Sort.by(Sort.Direction.DESC, "createdDate"));
        }

        for (Vaccination vaccDetails : vaccinationList) {
            FamilyMember fmd = familyMemberRepository.findById(vaccDetails.getChildId()).get();
            long getMills = fmd.getDob();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            Date date = new Date(getMills);

                GetVaccinationDTO addSingle = GetVaccinationDTO.builder()
                        .name(fmd.getName())
                        .gender(fmd.getGender())
                        .vaccinationCode(vaccDetails.getVaccinationCode())
                        .vaccinationName(vaccDetails.getVaccinationName())
                        .age(df.format(date))
                        .photo(fmd.getPhoto())
                        .motherName(fmd.getMotherName())
                        .build();
                addList.add(addSingle);
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

                List<AnganwadiChildren> findNonRegistered = anganwadiChildrenRepository.findAllByChildIdAndRegisteredTrue(member.getId());

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
                .centerName(centerName)
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
                .motherMemberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .gender(birthDetails.getGender() == null ? "" : birthDetails.getGender())
                .centerId(centerName)
                .height(birthDetails.getHeight() == null ? "" : birthDetails.getHeight())
                .firstWeight(birthDetails.getFirstWeight() == null ? "" : birthDetails.getFirstWeight())
                .build();
        babiesBirthRepository.save(saveDetails);

        // Save in Weight Table

        WeightTracking saveRecord = WeightTracking.builder()
                .familyId(searchFamilyId.getFamilyId() == null ? "" : searchFamilyId.getFamilyId())
                .childId(saveDetails.getChildId() == null ? "" : saveDetails.getChildId())
                .date(mills)
                .centerName(centerName)
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
                .memberId(birthDetails.getMotherMemberId() == null ? "" : birthDetails.getMotherMemberId())
                .centerName(centerName)
                .childDob(mills)
                .category(searchFamilyId.getCategory().length() <= 0 ? headCategory : searchFamilyId.getCategory())
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
                .centerName(centerName)
                .motherName(motherName)
                .childId(childId)
                .description(desc)
                .vaccinationCode(vaccinationCode)
                .vaccinationName(vaccinationName)
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
    public DashboardFamilyData getDashboardFamilyData(LocationFilter filter) {

        List<Family> findData = familyRepository.findAll();
        List<FamilyMember> fm = familyMemberRepository.findAll();
        List<AnganwadiChildren> ac = anganwadiChildrenRepository.findAll();
        HashSet<String> uniqueFamilyId = new HashSet<>();
        long totalChildren = 0;

        for (AnganwadiChildren anganwadiChildren : ac) {
            uniqueFamilyId.add(anganwadiChildren.getFamilyId());
        }

        for (FamilyMember children : fm) {
            LocalDateTime date = LocalDateTime.now().minusYears(6);
            ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
            long convertToMills = zdt.toInstant().toEpochMilli();

            if (children.getDob() >= convertToMills) {
                totalChildren++;
            }

        }

        return DashboardFamilyData.builder()
                .households(findData.size())
                .population(fm.size())
                .totalBeneficiary(uniqueFamilyId.size())
                .children(totalChildren)
                .build();
    }

    @Override
    public TotalChildrenData getTotalChildrenData(String caste, String gender, String startDate, String endDate) throws ParseException {

        TotalChildrenData childrenData = new TotalChildrenData();
        String childrenCaste = caste == null ? "" : caste;

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date currentDate = new Date();
        long currentMillis = currentDate.getTime();

        Date startTime = df.parse(startDate);
        Date endTime = df.parse(endDate);


        List<FamilyMember> findChildrenRecords = familyMemberRepository.findAllByChildrenCriteria(childrenCaste, gender, startTime, endTime);

        long zeroToOne = 0, oneToTwo = 0, twoToThree = 0, threeToFour = 0, fourToFive = 0, fiveToSix = 0;

        LocalDateTime firstDate = LocalDateTime.now().minusYears(1);
        ZonedDateTime firstZdt = ZonedDateTime.of(firstDate, ZoneId.systemDefault());
        long firstMills = firstZdt.toInstant().toEpochMilli();

        LocalDateTime secondDate = LocalDateTime.now().minusYears(2);
        ZonedDateTime secondZdt = ZonedDateTime.of(secondDate, ZoneId.systemDefault());
        long secondMills = secondZdt.toInstant().toEpochMilli();

        LocalDateTime thirdDate = LocalDateTime.now().minusYears(3);
        ZonedDateTime thirdZdt = ZonedDateTime.of(thirdDate, ZoneId.systemDefault());
        long thirdMills = thirdZdt.toInstant().toEpochMilli();

        LocalDateTime fourDate = LocalDateTime.now().minusYears(4);
        ZonedDateTime fourZdt = ZonedDateTime.of(fourDate, ZoneId.systemDefault());
        long fourMills = fourZdt.toInstant().toEpochMilli();

        LocalDateTime fifthDate = LocalDateTime.now().minusYears(5);
        ZonedDateTime fifthZdt = ZonedDateTime.of(fifthDate, ZoneId.systemDefault());
        long fifthMills = fifthZdt.toInstant().toEpochMilli();

        LocalDateTime sixthDate = LocalDateTime.now().minusYears(6);
        ZonedDateTime sixthZdt = ZonedDateTime.of(sixthDate, ZoneId.systemDefault());
        long sixthMills = sixthZdt.toInstant().toEpochMilli();


        for (FamilyMember fm : findChildrenRecords) {


            if (fm.getDob() >= firstMills && fm.getDob() < currentMillis) {
                zeroToOne++;
            }

            if (fm.getDob() >= secondMills && fm.getDob() < firstMills) {
                oneToTwo++;
            }

            if (fm.getDob() >= thirdMills && fm.getDob() < secondMills) {
                twoToThree++;
            }

            if (fm.getDob() >= fourMills && fm.getDob() < thirdMills) {
                threeToFour++;
            }

            if (fm.getDob() >= fifthMills && fm.getDob() < fourMills) {
                fourToFive++;
            }

            if (fm.getDob() >= sixthMills && fm.getDob() < fifthMills) {
                fiveToSix++;
            }


        }


        childrenData = TotalChildrenData.builder()
                .caste(childrenCaste)
                .gender(gender)
                .startDate(startDate)
                .endDate(endDate)
                .zeroToOne(zeroToOne)
                .oneToTwo(oneToTwo)
                .twoToThree(twoToThree)
                .threeToFour(threeToFour)
                .fourToFive(fourToFive)
                .fiveToSix(fiveToSix)
                .build();

        return childrenData;
    }

    @Override
    public HouseholdCategoryData getHouseholdCategoryData(String type, String month) {

        long general = 0, obc = 0, sc = 0, st = 0, others = 0;
        String categoryType = type == null ? "" : type;

        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
        String[] splitType = categoryType.split(",");
        for (String fetchCat : splitType) {
            List<FamilyMember> findByCategory = familyMemberRepository.findByCategoryCriteria(fetchCat.trim());

            for (FamilyMember fm : findByCategory) {

                switch (fm.getCategory().trim()) {
                    case  "1":
                        general++;
                        break;

                    case  "2":
                        obc++;
                        break;

                    case  "3":
                        sc++;
                        break;

                    case  "4":
                        st++;
                        break;

                    case  "5":
                        others++;
                        break;

                }
            }
        }

        return HouseholdCategoryData.builder()
                .type(categoryType)
                .month(month)
                .general(general)
                .sc(sc)
                .st(st)
                .others(others)
                .obc(obc)
                .build();

    }

    @Override
    public PregnancyData getPregnancyData(String startDate, String endDate) throws ParseException {


        HashSet<String> uniqueMemberId = new HashSet<>();

        long gen = 0, st = 0, sc = 0, obc = 0, others = 0;
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date startTime = df.parse(startDate);
        Date endTime = df.parse(endDate);

        List<Visits> findPregnancyData = visitsRepository.findAllByPregnancyCriteria(startTime, endTime);
        for (Visits cat : findPregnancyData) {

            switch (cat.getCategory().trim()) {
                case "1":
                    gen++;
                    break;
            }

            if (cat.getCategory().trim().equals("2")) {
                obc++;
            }

            if (cat.getCategory().trim().equals("3")) {
                sc++;
            }

            if (cat.getCategory().trim().equals("4")) {
                st++;
            }

            if (cat.getCategory().trim().equals("5")) {
                others++;
            }
        }

        return PregnancyData.builder()
                .general(gen)
                .st(st)
                .sc(sc)
                .obc(obc)
                .others(others)
                .startDate(startDate)
                .endDate(endDate)
                .build();

    }

    @Override
    public List<PregnantWomenDetails> getPregnantWomenDetails(String startDate, String endDate, String search) throws ParseException {

        List<PregnantWomenDetails> addInList = new ArrayList<>();
        HashSet<String> uniqueMemberId = new HashSet<>();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        String searchKeyword = search == null ? "" : search;
        Date startTime = df.parse(startDate);
        Date endTime = df.parse(endDate);

        List<Visits> findPregnancyData = visitsRepository.findAllByPregnancyCriteria(startTime, endTime);

        try {
            for (Visits visits : findPregnancyData) {

                if (uniqueMemberId.add(visits.getMemberId())) {
                    List<FamilyMember> searchNames = familyMemberRepository.findAllByIdAndNameSearch(visits.getMemberId(), searchKeyword.trim());

                    if (searchNames.size() > 0) {
                        for (FamilyMember searchResults : searchNames) {
                            Family households = familyRepository.findByFamilyId(searchResults.getFamilyId());

                            PregnantWomenDetails addSingle = PregnantWomenDetails.builder()
                                    .name(searchResults.getName())
                                    .husbandName(searchResults.getFatherName())
                                    .dob(df.format(searchResults.getDob()))
                                    .category(searchResults.getCategory())
                                    .religion(households.getReligion())
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
    public DeliveryDTO getDeliveryData(String startDate, String endDate) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date startTime = df.parse(startDate);
        Date endTime = df.parse(endDate);
        long inHome = 0, inHospital = 0;


        List<BabiesBirth> birthList = babiesBirthRepository.findAllByMonth(startTime, endTime);


        for (BabiesBirth bb : birthList) {

            if (bb.getBirthType().trim().equals("1")) {
                inHome++;
            }
            if (bb.getBirthType().trim().equals("2")) {
                inHospital++;
            }


        }
        return DeliveryDTO.builder()
                .startDate(startDate)
                .endDate(endDate)
                .inHome(inHome)
                .inHospital(inHospital)
                .build();
    }

    @Override
    public List<VaccinationRecordsDTO> getVaccinationData(String startDate, String endDate) throws ParseException {

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        Date startTime = df.parse(startDate);
        Date endTime = df.parse(endDate);

        List<VaccinationRecordsDTO> addInList = new ArrayList<>();
        List<Vaccination> vaccinationList = vaccinationRepository.findAllByMonthCriteria(startDate, endDate);

        for (Vaccination details : vaccinationList) {
            VaccinationRecordsDTO singleEntry = VaccinationRecordsDTO.builder()
                    .vaccinationCode(details.getVaccinationCode())
                    .centerName(details.getCenterName())
                    .childId(details.getChildId())
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

            FamilyMember headDetails = familyMemberRepository.findByHead(findHousehold.getFamilyId());
            headDetails.setCategory(householdsDTO.getCategory());
            headDetails.setName(householdsDTO.getHeadName());
            headDetails.setMobileNumber(householdsDTO.getMobileNumber());
            headDetails.setGender(householdsDTO.getHeadGender());
            headDetails.setDob(mills);
            headDetails.setIdType(householdsDTO.getUniqueIdType());
            headDetails.setIdNumber(householdsDTO.getUniqueId());
            headDetails.setPhoto(householdsDTO.getHeadPic());
            familyMemberRepository.save(headDetails);

            return HouseholdsDTO.builder()
                    .id(findHousehold.getId())
                    .centerName(findHousehold.getCenterName())
                    .uniqueCode(headDetails.getIdType())
                    .uniqueId(headDetails.getIdNumber())
                    .headName(headDetails.getName())
                    .headDob(householdsDTO.getHeadDob())
                    .totalMembers("")
                    .headPic(headDetails.getPhoto())
                    .centerId("")
                    .headGender(headDetails.getGender())
                    .houseNo(findHousehold.getHouseNo())
                    .mobileNumber(headDetails.getMobileNumber())
                    .uniqueIdType(headDetails.getIdType())
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
            throw new CustomException("Household Not Found");
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

        List<Vaccination> findRecords = vaccinationRepository.findAllByChildId(childId, Sort.by(Sort.Direction.ASC, "createdDate"));
        List<PerVaccineRecord> addInSingle = new ArrayList<>();

        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        for (Vaccination lists : findRecords) {
            Date date = new Date(lists.getDate());
            PerVaccineRecord singleList = PerVaccineRecord.builder()
                    .vaccinationCode(lists.getVaccinationCode())
                    .vaccinationName(lists.getVaccinationName())
                    .date(df.format(date))
                    .build();
            addInSingle.add(singleList);
        }

        return addInSingle;
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
