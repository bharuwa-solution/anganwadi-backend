package com.anganwadi.anganwadi.service_impl.service;


import com.anganwadi.anganwadi.domains.dto.FamilyMemberDTO;
import com.anganwadi.anganwadi.domains.dto.HouseholdsDTO;
import com.anganwadi.anganwadi.domains.dto.householdsHeadList;

import java.util.List;

public interface FamilyService {
    List<householdsHeadList> getAllHouseholds();

    HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO);

    FamilyMemberDTO saveFamilyMembers(FamilyMemberDTO familyMemberDTO);

    List<FamilyMemberDTO> getFamilyMembers(String familyId);
}
