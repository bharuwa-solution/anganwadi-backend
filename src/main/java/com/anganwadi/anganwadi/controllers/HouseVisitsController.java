package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.FemaleMembersDTO;
import com.anganwadi.anganwadi.domains.dto.VisitsDetailsDTO;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("visits")
public class HouseVisitsController {

    private final FamilyService familyService;

    @Autowired
    public HouseVisitsController(FamilyService familyService) {
        this.familyService = familyService;
    }


    @PostMapping("/saveVisitsDetails")
    private VisitsDetailsDTO saveVisitsDetails(@RequestBody VisitsDetailsDTO visitsDetailsDTO) {
        return familyService.saveVisitsDetails(visitsDetailsDTO);
    }

    @GetMapping("/getHouseholdsFemaleDetails")
    private List<FemaleMembersDTO> getHouseholdsFemaleDetails() {
        return familyService.getHouseholdsFemaleDetails();
    }

    @GetMapping("/getHouseVisitListing")
    private List<FemaleMembersDTO> getHouseVisitListing() {
        return familyService.getHouseVisitListing();
    }



}
