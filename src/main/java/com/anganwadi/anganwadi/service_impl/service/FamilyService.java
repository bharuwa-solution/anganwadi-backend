package com.anganwadi.anganwadi.service_impl.service;


import com.anganwadi.anganwadi.domains.dto.*;

import java.util.List;

public interface FamilyService {
    List<householdsHeadList> getAllHouseholds();

    HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO);

    FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO);

    List<FamilyMemberDTO> getFamilyMembers(String familyId);

    VisitsDetailsDTO saveVisitsDetails(VisitsDetailsDTO visitsDetailsDTO);

    WeightRecordsDTO saveWeightRecords(WeightRecordsDTO weightRecordsDTO);

    List<WeightRecordsDTO> getWeightRecords(String familyId, String childId);

    List<WeightRecordsDTO> getAllChildWeightRecords(String familyId);

    List<MPRDTO> getMPRRecords(String familyId);
}
