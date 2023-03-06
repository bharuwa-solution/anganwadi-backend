package com.anganwadi.anganwadi.service_impl.service;


import com.anganwadi.anganwadi.domains.dto.HouseholdsDTO;

import java.util.List;

public interface FamilyService {
    List<HouseholdsDTO> getAllHouseholds();

    HouseholdsDTO saveHouseholds(HouseholdsDTO householdsDTO);
}
