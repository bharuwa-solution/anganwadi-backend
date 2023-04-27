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

    @PostMapping("getTotalChildrenData")
    private List<TotalChildrenData> getTotalChildrenData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getTotalChildrenData(dashboardFilter);
    }

    @PostMapping("getHouseholdReligionData")
    private List<HouseholdReligionData> getHouseholdReligionData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getHouseholdReligionData(dashboardFilter);
    }

    @PostMapping("getHouseholdCategoryData")
    private List<HouseholdCategoryData> getHouseholdCategoryData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getHouseholdCategoryData(dashboardFilter);
    }


    @PostMapping("getPregnancyData")
    private List<PregnancyData> getPregnancyData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getPregnancyData(dashboardFilter);
    }

    @PostMapping("getPregnantWomenDetails")
    private List<PregnantWomenDetails> getPregnantWomenDetails(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getPregnantWomenDetails(dashboardFilter);
    }

    @PostMapping("getAnganwadiAahaarData")
    private List<AnganwadiAahaarData> getAnganwadiAahaarData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return anganwadiChildrenService.getAnganwadiAahaarData(dashboardFilter);
    }

    @PostMapping("getChildrenWeightData")
    private List<WeightTrackingDTO> getChildrenWeightData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return anganwadiChildrenService.getChildrenWeightData(dashboardFilter);
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
