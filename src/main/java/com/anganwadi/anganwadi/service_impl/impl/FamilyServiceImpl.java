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

    @Autowired
    public FamilyServiceImpl(FamilyRepository familyRepository, ModelMapper modelMapper, FamilyMemberRepository familyMemberRepository,
                             VisitsRepository visitsRepository, WeightTrackingRepository weightTrackingRepository, VaccinationRepository vaccinationRepository,
                             PregnantAndDeliveryRepository pregnantAndDeliveryRepository) {
        this.familyRepository = familyRepository;
        this.modelMapper = modelMapper;
        this.familyMemberRepository = familyMemberRepository;
        this.visitsRepository = visitsRepository;
        this.weightTrackingRepository = weightTrackingRepository;
        this.vaccinationRepository = vaccinationRepository;
        this.pregnantAndDeliveryRepository = pregnantAndDeliveryRepository;
    }


    private int totalHouseholdsMale(String familyId) {
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

    private int totalHouseholdsFemale(String familyId) {
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

    private int totalHouseholdsChildren(String familyId) {
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
    public List<householdsHeadList> getAllHouseholds() {
        List<householdsHeadList> addInList = new ArrayList<>();
        List<Family> familyList = familyRepository.findAll(Sort.by(Sort.Direction.DESC, "createdDate"));

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


                    if (checkDetails.getRelationWithOwner().equalsIgnoreCase("Self")) {
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
    public HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO) throws ParseException {

        String name = householdsDTO.getHeadName() == null ? "" : householdsDTO.getHeadName();
        String headDob = householdsDTO.getHeadDob() == null ? "" : householdsDTO.getHeadDob();
        String centerID = householdsDTO.getCenterId() == null ? "" : householdsDTO.getCenterId();
        String totalMembers = householdsDTO.getTotalMembers() == null ? "" : householdsDTO.getTotalMembers();
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


        Family saveFamily = Family.builder()
                .houseNo(houseNo)
                .familyId(familyId)
                .uniqueIdType(uniqueIdType)
                .centerId(centerID)
                .uniqueCode(uniqueCode)
                .uniqueId(uniqueId)
                .category(category)
                .religion(religion)
                .isMinority(isMinority)
                .icdsService(icdsService)
                .build();
        familyRepository.save(saveFamily);

        FamilyMember saveInMember = FamilyMember.builder()
                .name(name)
                .dob(mills)
                .familyId(familyId)
                .mobileNumber(mobileNo)
                .category(category)
                .photo(headPic)
                .relationWithOwner("Self")
                .gender(headGender)
                .build();

        familyMemberRepository.save(saveInMember);

        return HouseholdsDTO.builder()
                .id(saveInMember.getId())
                .headName(name)
                .headDob(headDob)
                .headPic(headPic)
                .centerId("Belda")
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
    public FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO) throws ParseException {

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

        FamilyMember saveMember = FamilyMember.builder()
                .name(name)
                .relationWithOwner(relationShip)
                .gender(gender)
                .familyId(familyMemberDTO.getFamilyId())
                .mobileNumber(familyMemberDTO.getMobileNumber() == null ? "" : familyMemberDTO.getMobileNumber())
                .idType(familyMemberDTO.getIdType() == null ? "" : familyMemberDTO.getIdType())
                .idNumber(familyMemberDTO.getIdNumber() == null ? "" : familyMemberDTO.getIdNumber())
                .dob(mills)
                .motherName(motherName)
                .fatherName(fatherName)
                .memberCode(memberCode)
                .memberCode(code)
                .category(category)
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
        List<FamilyMember> getMembers = familyMemberRepository.findAllByFamilyId(familyId, Sort.by(Sort.Direction.DESC, "createdDate"));
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
                    .maritalStatus(passDetails.getMaritalStatus() == null ? "" : passDetails.getMaritalStatus())
                    .stateCode(passDetails.getStateCode() == null ? "" : passDetails.getStateCode())
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
    public VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO) throws ParseException {


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
                .visitType(visitsDetailsDTO.getVisitType() == null ? "" : visitsDetailsDTO.getVisitType())
                .visitFor(visitsDetailsDTO.getVisitFor() == null ? "" : visitsDetailsDTO.getVisitFor())
                .description(visitsDetailsDTO.getDescription() == null ? "" : visitsDetailsDTO.getDescription())
                .visitRound(visitsDetailsDTO.getVisitRound() == null ? "" : visitsDetailsDTO.getVisitRound())
                .latitude(visitsDetailsDTO.getLatitude() == null ? "" : visitsDetailsDTO.getLatitude())
                .longitude(visitsDetailsDTO.getLongitude() == null ? "" : visitsDetailsDTO.getLongitude())
                .visitDateTime(millis)
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
        List<WeightTracking> findAllChildRecords = weightTrackingRepository.findAllByFamilyId(familyId,Sort.by(Sort.Direction.ASC, "createdDate"));

        List<WeightRecordsDTO> addList = new ArrayList<>();

        return null;
    }

    @Override
    public List<MPRDTO> getMPRRecords(String dateFrom, String dateTo, String category) {

        dateFrom = dateFrom == null ? "" : dateFrom;
        dateTo = dateTo == null ? "" : dateTo;
        category = category == null ? "" : category;

//        List<FamilyMember> chekByCat = familyMemberRepository.findAllByPeriod(dateFrom, dateTo, category);


        return null;
    }

    @Override
    public MPRDTO getMembersByFamilyId(String familyId) {

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

        return MPRDTO.builder()
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
    public List<HouseholdsChildren> getAllHouseholdsChildren() throws ParseException {

        LocalDateTime date = LocalDateTime.now().minusYears(6);
        ZonedDateTime zdt = ZonedDateTime.of(date, ZoneId.systemDefault());
//        String convertToString = String.valueOf(new Date().getTime());
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//        Date parseTime = df.parse(convertToString);

        List<HouseholdsChildren> addList = new ArrayList<>();


        long convertToMills = zdt.toInstant().toEpochMilli();
        log.info("Time "+convertToMills);

        List<FamilyMember> findAllChildren = familyMemberRepository.findAllByDob(convertToMills);
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

        HashSet<String> prefixType = new HashSet<>();
        prefixType.add(visitPrefix);

        visitPrefix = visitPrefix + noOfRounds;
        return visitPrefix;
    }

    private String visitRounds(String familyId) {


        StringBuilder appendRounds = new StringBuilder();


        List<Visits> checkRounds = visitsRepository.findAllByFamilyId(familyId);
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
    public List<FemaleMembersDTO> getHouseholdsFemaleDetails() {


        List<FamilyMember> findFemales = familyMemberRepository.findAllByGender();
        List<FemaleMembersDTO> addList = new ArrayList<>();

        for (FamilyMember checkAge : findFemales) {
            int getYear = 0 ;
            String centerName = "", husbandName = "", houseNo = "";

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
    public List<HouseVisitDTO> getHouseVisitListing() {

        List<Visits> checkRounds = visitsRepository.findAll();
        List<HouseVisitDTO> addInList = new ArrayList<>();
        HashSet<String> memberId = new HashSet<>();

        for (Visits visitsDetails : checkRounds) {
            FamilyMember checkMembers = familyMemberRepository.findById(visitsDetails.getMemberId()).get();

            String husband_FatherName = "", houseNo = "", centerName = "";


            long millis = checkMembers.getDob();
            Date date = new Date(millis);
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

            husband_FatherName = checkMembers.getFatherName();


            List<Family> checkHouseDetails = familyRepository.findAllByFamilyId(visitsDetails.getFamilyId());

            for (Family findFamily : checkHouseDetails) {
                houseNo = findFamily.getHouseNo();
                centerName = findFamily.getCenterId();
            }

            if (memberId.add(visitsDetails.getMemberId())) {
                HouseVisitDTO addSingle = HouseVisitDTO.builder()
                        .name(checkMembers.getName() == null ? "" : checkMembers.getName())
                        .dob(df.format(date))
                        .husbandName(husband_FatherName == null ? "" : husband_FatherName)
                        .houseNo(houseNo)
                        .memberId(checkMembers.getId())
                        .centerName(centerName == null ? "" : houseNo)
                        .profilePic(checkMembers.getPhoto() == null ? "" : checkMembers.getPhoto())
                        .visits("")
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
    public List<MemberVisits> getMemberVisitDetailsLatest(String memberId) {


        List<MemberVisits> addInList = new ArrayList<>();
        MemberVisits memberVisits = new MemberVisits();
        HashSet<String> uniqueMember = new HashSet<>();
        int count = 0;


        for (int i = 1; i <= 10; i++) {
            List<Visits> findMember = visitsRepository.findAllByMemberIdAndVisitType(memberId, String.valueOf(i));
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
                        .build();


            }
            addInList.add(memberVisits);
        }


        return addInList;
    }

    @Override
    public List<MemberVisits> getMemberVisitDetails(String memberId) {

        List<Visits> findMember = visitsRepository.findAllByMemberId(memberId);
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
