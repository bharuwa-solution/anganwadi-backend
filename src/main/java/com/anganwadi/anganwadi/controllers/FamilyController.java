package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    private List<householdsHeadList> getAllHouseholds(@RequestHeader String centerName) {
        return familyService.getAllHouseholds(centerName);
    }


    @PostMapping("/saveHouseholds")
    private HouseholdsDTO saveHouseholds(@RequestBody HouseholdsDTO householdsDTO, @RequestHeader String centerName) throws ParseException {
        return familyService.saveHouseholds(householdsDTO, centerName);
    }

    @PostMapping("/saveFamilyMembers")
    private FamilyMemberDTO saveFamilyMembers(@RequestBody FamilyMemberDTO familyMemberDTO,@RequestHeader String centerName) throws ParseException {
        return familyService.saveFamilyMembers(familyMemberDTO, centerName);
    }

    @GetMapping("/getFamilyMembers")
    private List<FamilyMemberDTO> getFamilyMembers(@RequestParam String familyId) {
        return familyService.getFamilyMembers(familyId);
    }

//    @PostMapping("/saveVisitsDetails")
//    private VisitsDetailsDTO saveVisitsDetails(@RequestBody VisitsDetailsDTO visitsDetailsDTO) {
//        return familyService.saveVisitsDetails(visitsDetailsDTO);
//    }

    @PostMapping("/saveWeightRecords")
    private WeightRecordsDTO saveWeightRecords(@RequestBody WeightRecordsDTO weightRecordsDTO) {
        return familyService.saveWeightRecords(weightRecordsDTO);
    }

    @GetMapping("/getChildWeightRecords")
    private List<WeightRecordsDTO> getWeightRecords(@RequestParam String familyId, @RequestParam String childId) {
        return familyService.getWeightRecords(familyId, childId);
    }

    @GetMapping("/getAllChildWeightRecords")
    private List<WeightRecordsDTO> getAllChildWeightRecords(@RequestParam String familyId) {
        return familyService.getAllChildWeightRecords(familyId);
    }

    @GetMapping("/getMPRRecords")
    private MPRDTO getMPRRecords(@RequestParam(required = false) String month, @RequestParam(required = false) String duration, @RequestParam(required = false) String category, @RequestHeader String centerName) throws ParseException {
        return familyService.getMPRRecords(month, duration, category, centerName);
    }

    @GetMapping("/getMembersByFamilyId")
    private FamilyMemberCounts getMembersByFamilyId(@RequestParam String familyId) {
        return familyService.getMembersByFamilyId(familyId);
    }




    @GetMapping("/getAllHouseholdsChildren")
    private List<HouseholdsChildren> getAllHouseholdsChildren(@RequestHeader String centerName) throws ParseException {
        return familyService.getAllHouseholdsChildren(centerName);
    }




}

