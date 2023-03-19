package com.anganwadi.anganwadi.service_impl.service;


import com.anganwadi.anganwadi.domains.dto.*;

import java.text.ParseException;
import java.util.List;

public interface FamilyService {
    List<householdsHeadList> getAllHouseholds();

    HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO) throws ParseException;

    FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO) throws ParseException;

    List<FamilyMemberDTO> getFamilyMembers(String familyId);

    VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO) throws ParseException;

    WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO);

    List<WeightRecordsDTO> getWeightRecords(String familyId, String childId);

    List<WeightRecordsDTO> getAllChildWeightRecords(String familyId);

    List<MPRDTO> getMPRRecords(String dateFrom,String dateTo, String category);

    MPRDTO getMembersByFamilyId(String familyId);

    List<VaccinationRecords> getVaccinationRecords();

    List<HouseholdsChildren> getAllHouseholdsChildren() throws ParseException;


    List<FemaleMembersDTO> getHouseholdsFemaleDetails();

    List<HouseVisitDTO> getHouseVisitListing();

    List<MemberVisits> getMemberVisitDetails(String memberId);

    List<MemberVisits> getMemberVisitDetailsLatest(String memberId);
}
