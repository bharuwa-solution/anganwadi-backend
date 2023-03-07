package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.FamilyMemberDTO;
import com.anganwadi.anganwadi.domains.dto.HouseholdsDTO;
import com.anganwadi.anganwadi.domains.dto.householdsHeadList;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/family")
public class FamilyController {

    private final FamilyService familyService;

    @Autowired
    public FamilyController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @GetMapping("/getAllHouseholds")
    private List<householdsHeadList> getAllHouseholds() {
        return familyService.getAllHouseholds();
    }


    @PostMapping("/saveHouseholds")
    private HouseholdsDTO saveHouseholds(@RequestBody HouseholdsDTO householdsDTO) {
        return familyService.saveHouseholds(householdsDTO);
    }

    @PostMapping("/saveFamilyMembers")
    private FamilyMemberDTO saveFamilyMembers(@RequestBody FamilyMemberDTO familyMemberDTO) {
        return familyService.saveFamilyMembers(familyMemberDTO);
    }

    @GetMapping("/getFamilyMembers")
    private List<FamilyMemberDTO> getFamilyMembers(@RequestParam String familyId) {
        return familyService.getFamilyMembers(familyId);
    }
}

