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
    private List<householdsHeadList> getAllHouseholds(@RequestHeader String centerId) {
        return familyService.getAllHouseholds(centerId);
    }


    @PostMapping("/saveHouseholds")
    private HouseholdsDTO saveHouseholds(@RequestBody HouseholdsDTO householdsDTO, @RequestHeader String centerId,@RequestHeader String centerName) throws ParseException {
        return familyService.saveHouseholds(householdsDTO, centerId, centerName);
    }

    @PostMapping("/saveFamilyMembers")
    private FamilyMemberDTO saveFamilyMembers(@RequestBody FamilyMemberDTO familyMemberDTO,@RequestHeader String centerId, @RequestHeader String centerName) throws ParseException {
        return familyService.saveFamilyMembers(familyMemberDTO, centerId, centerName);
    }

//    @PutMapping("updateRegisteredValue")
//    private FamilyMemberDTO updateRegisteredValue(@RequestParam String id, @RequestParam boolean isRegistered) {
//        return familyService.updateRegisteredValue(id, isRegistered);
//    }

    @GetMapping("/getFamilyMembers")
    private List<FamilyMemberDTO> getFamilyMembers(@RequestParam String familyId) {
        return familyService.getFamilyMembers(familyId);
    }

//    @PostMapping("/saveVisitsDetails")
//    private VisitsDetailsDTO saveVisitsDetails(@RequestBody VisitsDetailsDTO visitsDetailsDTO) {
//        return familyService.saveVisitsDetails(visitsDetailsDTO);
//    }


    @GetMapping("/getMPRRecords")
    private MPRDTO getMPRRecords(@RequestParam(required = false) String month, @RequestParam(required = false) String duration, @RequestParam(required = false) String category, @RequestHeader String centerName) throws ParseException {
        return familyService.getMPRRecords(month, duration, category, centerName);
    }

    @GetMapping("/getMembersByFamilyId")
    private FamilyMemberCounts getMembersByFamilyId(@RequestParam String familyId) {
        return familyService.getMembersByFamilyId(familyId);
    }

    @GetMapping("/getAllHouseholdsChildren")
    private List<HouseholdsChildren> getAllHouseholdsChildren(@RequestHeader String centerId) throws ParseException {
        return familyService.getAllHouseholdsChildren(centerId);
    }

    @PutMapping("/updateHouseHold")
    private HouseholdsDTO updateHouseHold(@RequestBody HouseholdsDTO householdsDTO){
        return familyService.updateHouseHold(householdsDTO);
    }


    @PutMapping("/updateHouseHoldMember")
    private FamilyMemberDTO updateHouseHoldMember(@RequestBody FamilyMemberDTO familyMemberDTO){
        return familyService.updateHouseHoldMember(familyMemberDTO);
    }

    @GetMapping("/getHouseholdById")
    private HouseholdsDTO getHouseholdById(@RequestParam String id){
        return familyService.getHouseholdById(id);
    }

}

