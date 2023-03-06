package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.HouseholdsDTO;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/family")
public class FamilyController {

    private final FamilyService familyService;

    @Autowired
    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }


    @PostMapping("/saveHouseholds")
    private HouseholdsDTO saveHouseholds(@RequestBody HouseholdsDTO householdsDTO) {
        return familyService.saveHouseholds(householdsDTO);
    }
}

