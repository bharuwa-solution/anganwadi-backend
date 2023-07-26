package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.Visits;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Type;
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
    private HouseholdsDTO saveHouseholds(@RequestBody HouseholdsDTO householdsDTO, @RequestHeader String centerId, @RequestHeader String centerName) throws ParseException {
        return familyService.saveHouseholds(householdsDTO, centerId, centerName);
    }

    @PostMapping("/saveFamilyMembers")
    private FamilyMemberDTO saveFamilyMembers(@RequestBody FamilyMemberDTO familyMemberDTO, @RequestHeader String centerId, @RequestHeader String centerName) throws ParseException {
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
    private HouseholdsDTO updateHouseHold(@RequestBody HouseholdsDTO householdsDTO) {
        return familyService.updateHouseHold(householdsDTO);
    }

    @PutMapping("/deleteHouseHold")
    private HouseholdsDTO deleteHouseHold(@RequestParam String familyId, @RequestParam String id) {
        return familyService.deleteHouseHold(familyId, id);
    }

    @PutMapping("/deleteFamilyMembers")
    private FamilyMemberDTO deleteFamilyMembers(@RequestParam String memberId, @RequestParam String id) {
        return familyService.deleteFamilyMembers(memberId, id);
    }

    @PutMapping("/updateHouseHoldMember")
    private FamilyMemberDTO updateHouseHoldMember(@RequestBody FamilyMemberDTO familyMemberDTO) {
        return familyService.updateHouseHoldMember(familyMemberDTO);
    }

    @GetMapping("/getHouseholdById")
    private HouseholdsDTO getHouseholdById(@RequestParam String id) {
        return familyService.getHouseholdById(id);
    }

    @GetMapping("/getAllBeneficiaryList")
    private List<BeneficiaryList> getAllBeneficiaryList(@RequestParam(required = false) String centerId) {
        return familyService.getAllBeneficiaryList(centerId);
    }

    @GetMapping("/getHouseholdWomenDetails")
    private HouseholdWomenDetails getHouseholdWomenDetails(@RequestHeader String centerId, @RequestHeader String centerName) {
        return familyService.getHouseholdWomenDetails(centerId, centerName);
    }

    @PostMapping("/registerPregnantWomen")
    private PregnantAndDeliveryDTO registerPregnantWomen(@RequestBody PregnantAndDeliveryDTO pregnantAndDeliveryDTO, @RequestHeader String centerId) throws ParseException {
        return familyService.registerPregnantWomen(pregnantAndDeliveryDTO, centerId);
    }

    @GetMapping("/getAllPregnantWomenDetails")
    private List<PregnantAndDeliveryDTO> getAllPregnantWomenDetails(@RequestHeader String centerId) {
        return familyService.getAllPregnantWomenDetails(centerId);
    }

    @PutMapping("/updatePregnantWomenDetails")
    private PregnantAndDeliveryDTO updatePregnantWomenDetails(@RequestBody PregnantAndDeliveryDTO pregnantAndDeliveryDTO, @RequestHeader String centerId) throws ParseException {
        return familyService.updatePregnantWomenDetails(pregnantAndDeliveryDTO, centerId);
    }

    @PostMapping("/deletePregnantWomenDetails")
    private PregnantAndDeliveryDTO deletePregnantWomenDetails(@RequestParam String id) {
        return familyService.deletePregnantWomenDetails(id);
    }

    @GetMapping("/getWomenListByPeriodDate")
    private List<WomenListByPeriodDateDTO> getWomenListByPeriodDate(@RequestHeader String centerId) {
        return familyService.getWomenListByPeriodDate(centerId);
    }

    @GetMapping("/getNewBornChildRecords")
    private List<NewBornChildDTO> getNewBornChildRecords(@RequestHeader String centerName) throws ParseException {
        return familyService.getNewBornChildRecords(centerName);
    }

    @PutMapping("/updateNewBornChildRecords")
    private NewBornChildDTO updateNewBornChildRecords(@RequestBody BirthPlaceDTO birthPlaceDTO) {
        return familyService.updateNewBornChildRecords(birthPlaceDTO);
    }

    @PostMapping("/deleteNewBornChildRecords")
    private DeleteBornChildDTO deleteNewBornChildRecords(@RequestParam String id) {
        return familyService.deleteNewBornChildRecords(id);
    }

    @GetMapping("/getDhatriDetails")
    private List<PregnantAndDeliveryDTO> getDhatriDetails(@RequestHeader String centerId) {
        return familyService.getDhatriDetails(centerId);
    }

    @GetMapping("/getAllChildrenDetails")
    private List<FamilyChildrenDetails> getAllChildrenDetails(@RequestHeader String centerName) throws ParseException {
        return familyService.getAllChildrenDetails(centerName);
    }

    @PutMapping("/updateMissingFields")
    private List<Visits> updateMissingFields(){
        return familyService.updateMissingFields();
    }


}

