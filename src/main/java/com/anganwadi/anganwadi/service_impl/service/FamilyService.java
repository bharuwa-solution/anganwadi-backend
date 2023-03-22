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

    WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO);

    List<WeightRecordsDTO> getWeightRecords(String familyId, String childId);

    List<WeightRecordsDTO> getAllChildWeightRecords(String familyId);

    List<MPRDTO> getMPRRecords(String dateFrom,String dateTo, String category);

    MPRDTO getMembersByFamilyId(String familyId);

    List<VaccinationRecords> getVaccinationRecords();

    List<HouseholdsChildren> getAllHouseholdsChildren(String centerName) throws ParseException;


    List<FemaleMembersDTO> getHouseholdsFemaleDetails(String centerName);

    List<HouseVisitDTO> getHouseVisitListing(String centerName);

    List<MemberVisits> getMemberVisitDetails(String memberId,String centerName);

    List<MemberVisits> getMemberVisitDetailsLatest(String memberId,String centerName);

    List<BirthPlaceDTO> saveBirthDetails(List<BirthPlaceDTO> birthPlaceDTO, String centerName);
}
