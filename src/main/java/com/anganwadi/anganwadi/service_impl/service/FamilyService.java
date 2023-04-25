package com.anganwadi.anganwadi.service_impl.service;


import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.dto.BirthPlaceDTO;
import org.springframework.web.bind.annotation.RequestBody;

import java.text.ParseException;
import java.util.List;

public interface FamilyService {
    List<householdsHeadList> getAllHouseholds(String centerId);

    HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO, String centerId, String centerName) throws ParseException;

    FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO, String centerId, String centerName) throws ParseException;

    List<FamilyMemberDTO> getFamilyMembers(String familyId);

    VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO, String centerId, String centerName) throws ParseException;

    WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO, String centerId, String centerName) throws ParseException;

    List<WeightRecordsDTO> getWeightRecords(String childId);

    List<WeightRecordsDTO> getAllChildWeightRecords(String centerId);

    MPRDTO getMPRRecords(String month, String duration, String category, String centerName) throws ParseException;

    FamilyMemberCounts getMembersByFamilyId(String familyId);

    List<GetVaccinationDTO> getVaccinationRecords(String vaccineCode, String centerId);

    List<HouseholdsChildren> getAllHouseholdsChildren(String centerId) throws ParseException;

    List<FemaleMembersDTO> getHouseholdsFemaleDetails(String centerId);

    List<HouseVisitDTO> getHouseVisitListing(String centerId);

    List<MemberVisits> getMemberVisitDetails(String memberId,String centerName);

    List<MemberVisits> getMemberVisitDetailsLatest(String memberId,String centerId, String centerName);

    List<BirthPlaceDTO> saveBirthDetails(BirthPlaceDTO birthDetails, String centerId, String centerName) throws ParseException;

    SaveVaccinationDTO saveVaccinationDetails(SaveVaccinationDTO saveVaccinationDTO, String centerId, String centerName);

    DashboardFamilyData getDashboardFamilyData(LocationFilter filter);

    TotalChildrenData getTotalChildrenData(String caste, String gender, String startDate, String endDate) throws ParseException;

    HouseholdCategoryData getHouseholdCategoryData(String type, String month);

    PregnancyData getPregnancyData(String startDate, String endDate) throws ParseException;

    List<PregnantWomenDetails> getPregnantWomenDetails(String startDate, String endDate, String search) throws ParseException;

    DeliveryDTO getDeliveryData(String startDate, String endDate) throws ParseException;

    List<VaccinationRecordsDTO> getVaccinationData(String startDate, String endDate) throws ParseException;

    HouseholdsDTO updateHouseHold(HouseholdsDTO householdsDTO);

    FamilyMemberDTO updateHouseHoldMember(FamilyMemberDTO familyMemberDTO);

    HouseholdsDTO getHouseholdById(String id);

    List<PerVaccineRecord> getVaccinationByChildId(String childId);

    HouseholdsDTO deleteHouseHold(String familyId, String id);

    List<BeneficiaryList> getAllBeneficiaryList(String centerId);

    FamilyMemberDTO deleteFamilyMembers(String memberId, String id);

    HouseholdWomenDetails getHouseholdWomenDetails(String centerId, String centerName);

    PregnantAndDeliveryDTO registerPregnantWomen(PregnantAndDeliveryDTO pregnantAndDeliveryDTO, String centerId) throws ParseException;

    List<PregnantAndDeliveryDTO> getAllPregnantWomenDetails(String centerId);

    PregnantAndDeliveryDTO updatePregnantWomenDetails(@RequestBody PregnantAndDeliveryDTO pregnantAndDeliveryDTO, String centerId) throws ParseException;

    PregnantAndDeliveryDTO deletePregnantWomenDetails(String id);

    List<WomenListByPeriodDateDTO> getWomenListByPeriodDate(String centerId);

    List<NewBornChildDTO> getNewBornChildRecords(String centerName) throws ParseException;

    NewBornChildDTO updateNewBornChildRecords(BirthPlaceDTO birthPlaceDTO);

    List<PregnantAndDeliveryDTO> getDhatriDetails(String centerId);

    List<FamilyChildrenDetails> getAllChildrenDetails(String centerName);

    DeleteBornChildDTO deleteNewBornChildRecords(String id);

    WeightRecordsDTO saveWeightRecordsCloned(WeightRecordsDTO weightRecordsDTO, String centerId, String centerName) throws ParseException;


//    FamilyMemberDTO updateRegisteredValue(String id, boolean isRegistered);
}
