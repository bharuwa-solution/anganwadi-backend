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
    private VisitsDetailsDTO saveVisitsDetails(@RequestBody VisitsDetailsDTO visitsDetailsDTO, @RequestHeader String centerName) throws ParseException {
        return familyService.saveVisitsDetails(visitsDetailsDTO,centerName);
    }

    @GetMapping("/getHouseholdsFemaleDetails")
    private List<FemaleMembersDTO> getHouseholdsFemaleDetails(@RequestHeader String centerName) {
        return familyService.getHouseholdsFemaleDetails(centerName);
    }

    @GetMapping("/getHouseVisitListing")
    private List<HouseVisitDTO> getHouseVisitListing(@RequestHeader String centerName) {
        return familyService.getHouseVisitListing(centerName);
    }

    @GetMapping("/getMemberVisitDetails")
    private List<MemberVisits> getMemberVisitDetails(@RequestParam String memberId,@RequestHeader String centerName) {
        return familyService.getMemberVisitDetails(memberId,centerName);
    }

    @GetMapping("/getMemberVisitDetailsLatest")
    private List<MemberVisits> getMemberVisitDetailsLatest(@RequestParam String memberId, @RequestHeader String centerName) {
        return familyService.getMemberVisitDetailsLatest(memberId,centerName);
    }


}
