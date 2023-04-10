package com.anganwadi.anganwadi.service_impl.service;


import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.BirthPlaceDTO;

import java.text.ParseException;
import java.util.List;

public interface FamilyService {
    List<householdsHeadList> getAllHouseholds(String centerName);

    HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO, String centerName) throws ParseException;

    FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO,  String centerName) throws ParseException;

    List<FamilyMemberDTO> getFamilyMembers(String familyId);

    VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO, String centerName) throws ParseException;

    WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO, String centerName) throws ParseException;

    List<WeightRecordsDTO> getWeightRecords(String childId);

    List<WeightRecordsDTO> getAllChildWeightRecords(String centerName);

    MPRDTO getMPRRecords(String month, String duration, String category, String centerName) throws ParseException;

    FamilyMemberCounts getMembersByFamilyId(String familyId);

    List<GetVaccinationDTO> getVaccinationRecords(String vaccineName, String centerName);

    List<HouseholdsChildren> getAllHouseholdsChildren(String centerName) throws ParseException;

    List<FemaleMembersDTO> getHouseholdsFemaleDetails(String centerName);

    List<HouseVisitDTO> getHouseVisitListing(String centerName);

    List<MemberVisits> getMemberVisitDetails(String memberId,String centerName);

    List<MemberVisits> getMemberVisitDetailsLatest(String memberId,String centerName);

    List<BirthPlaceDTO> saveBirthDetails(BirthPlaceDTO birthDetails, String centerName) throws ParseException;

    SaveVaccinationDTO saveVaccinationDetails(SaveVaccinationDTO saveVaccinationDTO, String centerName);

    DashboardFamilyData getDashboardFamilyData(LocationFilter filter);

    TotalChildrenData getTotalChildrenData(String caste, String gender, String startDate, String endDate) throws ParseException;

    HouseholdCategoryData getHouseholdCategoryData(String type, String month);

    PregnancyData getPregnancyData(String startDate, String endDate) throws ParseException;

    List<PregnantWomenDetails> getPregnantWomenDetails(String startDate, String endDate, String search) throws ParseException;

    DeliveryDTO getDeliveryData(String startDate, String endDate) throws ParseException;

    List<VaccinationRecordsDTO> getVaccinationData(String month);

    HouseholdsDTO updateHouseHold(HouseholdsDTO householdsDTO);

    FamilyMemberDTO updateHouseHoldMember(FamilyMemberDTO familyMemberDTO);

    HouseholdsDTO getHouseholdById(String id);

//    FamilyMemberDTO updateRegisteredValue(String id, boolean isRegistered);
}
