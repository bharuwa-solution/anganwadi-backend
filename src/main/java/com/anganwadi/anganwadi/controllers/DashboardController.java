package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.domains.entity.FamilyMember;
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
    private TotalChildrenData getTotalChildrenData(@RequestParam(required = false) String caste, @RequestParam String gender, @RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return familyService.getTotalChildrenData(caste, gender, startDate, endDate);
    }

    @GetMapping("getHouseholdCategoryData")
    private HouseholdCategoryData getHouseholdCategoryData(@RequestParam(required = false) String type, @RequestParam String month) throws ParseException {
        return familyService.getHouseholdCategoryData(type, month);
    }

    @GetMapping("getPregnancyData")
    private PregnancyData getPregnancyData(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return familyService.getPregnancyData(startDate,endDate);
    }

    @GetMapping("getPregnantWomenDetails")
    private List<PregnantWomenDetails> getPregnantWomenDetails(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String search) throws ParseException {
        return familyService.getPregnantWomenDetails(startDate,endDate, search);
    }

    @GetMapping("getAnganwadiAahaarData")
    private List<AnganwadiAahaarData> getAnganwadiAahaarData(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return anganwadiChildrenService.getAnganwadiAahaarData(startDate,endDate);
    }

    @GetMapping("getChildrenWeightData")
    private List<WeightTrackingDTO> getChildrenWeightData(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return anganwadiChildrenService.getChildrenWeightData(startDate,endDate);
    }

    @GetMapping("getDeliveryData")
    private DeliveryDTO getDeliveryData(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return familyService.getDeliveryData(startDate, endDate);
    }

    @GetMapping("getVaccinationData")
    private List<VaccinationRecordsDTO> getVaccinationDa(@RequestParam String startDate, @RequestParam String endDate) throws ParseException {
        return familyService.getVaccinationData(startDate, endDate);
    }

    @GetMapping("getAttendanceData")
    private List<DashboardAttendanceDTO> getAttendanceData(@RequestParam(required = false) String month) throws ParseException {
        return anganwadiChildrenService.getAttendanceData(month);
    }

    @GetMapping("getAnganwadiChildrenData")
    private AnganwadiChildrenDTO getAnganwadiChildrenData(@RequestParam(required = false) String month) throws ParseException {
        return anganwadiChildrenService.getAnganwadiChildrenData(month);
    }

    @GetMapping("getAnganwadiChildrenDetails")
    private List<AnganwadiChildrenList> getAnganwadiChildrenDetails(@RequestParam String startDate, @RequestParam String endDate, @RequestParam(required = false) String search) throws ParseException {
        return anganwadiChildrenService.getAnganwadiChildrenDetails(startDate,endDate, search);
    }


    @GetMapping("convertUnixToDate")
    private List<FamilyMemberConverted> convertUnixToDate() {
        return anganwadiChildrenService.convertUnixToDate();
    }


    @GetMapping("convertAttendanceUnixToDate")
    private List<AttendanceConverted> convertAttendanceUnixToDate() {
        return anganwadiChildrenService.convertAttendanceUnixToDate();
    }

}
