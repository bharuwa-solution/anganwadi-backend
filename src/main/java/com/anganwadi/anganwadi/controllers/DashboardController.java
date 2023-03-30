package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

    private final FamilyService familyService;
    private final AnganwadiChildrenService anganwadiChildrenService;

    @Autowired
    public DashboardController(FamilyService familyService, AnganwadiChildrenService anganwadiChildrenService) {
        this.familyService = familyService;
        this.anganwadiChildrenService = anganwadiChildrenService;
    }

    @PostMapping("getDashboardFamilyData")
    private DashboardFamilyData getDashboardFamilyData(@RequestBody LocationFilter filter) {
        return familyService.getDashboardFamilyData(filter);
    }

    @GetMapping("getTotalChildrenData")
    private TotalChildrenData getTotalChildrenData(@RequestParam(required = false) String caste, @RequestParam String gender, @RequestParam String month) throws ParseException {
        return familyService.getTotalChildrenData(caste, gender, month);
    }

    @GetMapping("getHouseholdCategoryData")
    private HouseholdCategoryData getHouseholdCategoryData(@RequestParam(required = false) String type, @RequestParam String month) throws ParseException {
        return familyService.getHouseholdCategoryData(type, month);
    }

    @GetMapping("getPregnancyData")
    private PregnancyData getPregnancyData(@RequestParam String month) throws ParseException {
        return familyService.getPregnancyData(month);
    }

    @GetMapping("getPregnantWomenDetails")
    private List<PregnantWomenDetails> getPregnantWomenDetails(@RequestParam String month, @RequestParam(required = false) String search) throws ParseException {
        return familyService.getPregnantWomenDetails(month, search);
    }

    @GetMapping("getAnganwadiAahaarData")
    private List<AnganwadiAahaarData> getAnganwadiAahaarData(@RequestParam(required = false) String month) {
        return anganwadiChildrenService.getAnganwadiAahaarData(month);
    }

    @GetMapping("getChildrenWeightData")
    private List<WeightTrackingDTO> getChildrenWeightData(@RequestParam(required = false) String month) {
        return anganwadiChildrenService.getChildrenWeightData(month);
    }

    @GetMapping("getDeliveryData")
    private List<DeliveryDTO> getDeliveryData(@RequestParam(required = false) String month) {
        return familyService.getDeliveryData(month);
    }

    @GetMapping("getVaccinationData")
    private List<VaccinationRecordsDTO> getVaccinationData(@RequestParam(required = false) String month){
        return familyService.getVaccinationData(month);
    }

}
