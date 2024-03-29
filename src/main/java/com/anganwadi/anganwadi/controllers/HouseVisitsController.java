package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
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
    private VisitsDetailsDTO saveVisitsDetails(@RequestBody VisitsDetailsDTO visitsDetailsDTO, @RequestHeader String centerId, @RequestHeader String centerName) throws ParseException {
        return familyService.saveVisitsDetails(visitsDetailsDTO, centerId, centerName);
    }

    @GetMapping("/getHouseholdsFemaleDetails")
    private List<FemaleMembersDTO> getHouseholdsFemaleDetails(@RequestHeader String centerId) {
        return familyService.getHouseholdsFemaleDetails(centerId);
    }

    @GetMapping("/getHouseVisitListing")
    private List<HouseVisitDTO> getHouseVisitListing(@RequestHeader String centerId) {
        return familyService.getHouseVisitListing(centerId);
    }

    @GetMapping("/getMemberVisitDetails")
    private List<MemberVisits> getMemberVisitDetails(@RequestParam String memberId, @RequestHeader String centerId) {
        return familyService.getMemberVisitDetails(memberId, centerId);
    }

    @GetMapping("/getMemberVisitDetailsLatest")
    private List<MemberVisits> getMemberVisitDetailsLatest(@RequestParam String memberId, @RequestHeader String centerId) {
        return familyService.getMemberVisitDetailsLatest(memberId, centerId);
    }

    @PostMapping("/saveBirthDetails")
    private List<BirthPlaceDTO> saveBirthDetails(@RequestBody BirthPlaceDTO birthDetails, @RequestHeader String centerId) throws ParseException {
        return familyService.saveBirthDetails(birthDetails, centerId);
    }

    @PostMapping("/getVisitScheduler")
    private List<MemberDetails> getVisitScheduler(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getVisitScheduler(dashboardFilter);
    }

    @PostMapping("/saveVisitsDetailsTemp")
    private VisitsDetailsDTOTemp saveVisitsDetailsTemp(@RequestBody VisitsDetailsDTOTemp visitsDetailsDTOTemp, @RequestHeader String centerId) throws ParseException {
        return familyService.saveVisitsDetailsTemp(visitsDetailsDTOTemp, centerId);
    }


}
