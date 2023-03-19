package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.FemaleMembersDTO;
import com.anganwadi.anganwadi.domains.dto.HouseVisitDTO;
import com.anganwadi.anganwadi.domains.dto.MemberVisits;
import com.anganwadi.anganwadi.domains.dto.VisitsDetailsDTO;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    private VisitsDetailsDTO saveVisitsDetails(@RequestBody VisitsDetailsDTO visitsDetailsDTO) throws ParseException {
        return familyService.saveVisitsDetails(visitsDetailsDTO);
    }

    @GetMapping("/getHouseholdsFemaleDetails")
    private List<FemaleMembersDTO> getHouseholdsFemaleDetails() {
        return familyService.getHouseholdsFemaleDetails();
    }

    @GetMapping("/getHouseVisitListing")
    private List<HouseVisitDTO> getHouseVisitListing() {
        return familyService.getHouseVisitListing();
    }

    @GetMapping("/getMemberVisitDetails")
    private List<MemberVisits> getMemberVisitDetails(@RequestParam String memberId) {
        return familyService.getMemberVisitDetails(memberId);
    }

    @GetMapping("/getMemberVisitDetailsLatest")
    private List<MemberVisits> getMemberVisitDetailsLatest(@RequestParam String memberId) {
        return familyService.getMemberVisitDetailsLatest(memberId);
    }


}
