package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.BirthPlaceDTO;
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
        return familyService.getMemberVisitDetailsLatest(memberId, centerName);
    }

    @PostMapping("/saveBirthDetails")
    private List<BirthPlaceDTO> saveBirthDetails(@RequestBody BirthPlaceDTO birthDetails, @RequestHeader String centerName) throws ParseException {
        return familyService.saveBirthDetails(birthDetails, centerName);
    }

    @PostMapping("/saveVaccinationDetails")
    private SaveVaccinationDTO saveVaccinationDetails(@RequestBody SaveVaccinationDTO saveVaccinationDTO, @RequestHeader String centerName) throws ParseException {
        return familyService.saveVaccinationDetails(saveVaccinationDTO, centerName);
    }

    @GetMapping("/getVaccinationRecords")
    private List<GetVaccinationDTO> getVaccinationRecords(@RequestParam(required = false) String vaccineName, @RequestHeader String centerName) {
        return familyService.getVaccinationRecords(vaccineName, centerName);
    }
}
