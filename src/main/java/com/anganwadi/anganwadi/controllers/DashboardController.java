package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

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
    private DashboardFamilyData getDashboardFamilyData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
            return familyService.getDashboardFamilyData(dashboardFilter);
    }

    @PostMapping("getTotalChildrenData")
    private List<TotalChildrenData> getTotalChildrenData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getTotalChildrenData(dashboardFilter);
    }

    @PostMapping("getReligionCategoryData")
    private List<HouseholdRelCatData> getReligionCategoryData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getReligionCategoryData(dashboardFilter);
    }

//
//    @PostMapping("getHouseholdReligionData")
//    private List<HouseholdReligionData> getHouseholdReligionData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
//        return familyService.getHouseholdReligionData(dashboardFilter);
//    }
//
//    @PostMapping("getHouseholdCategoryData")
//    private List<HouseholdCategoryData> getHouseholdCategoryData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
//        return familyService.getHouseholdCategoryData(dashboardFilter);
//    }


//    @PostMapping("getPregnancyData")
//    private List<PregnancyData> getPregnancyData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
//        return familyService.getPregnancyData(dashboardFilter);
//    }

    @PostMapping("getPregnantWomenDetails")
    private List<PregnantWomenDetails> getPregnantWomenDetails(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getPregnantWomenDetails(dashboardFilter);
    }

//    @PostMapping("getDhartiData")
//    private List<DhartiData> getDhartiData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
//        return familyService.getDhartiData(dashboardFilter);
//    }

    @PostMapping("getDhartiWomenDetails")
    private List<DhartiData> getDhartiWomenDetails(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getDhartiWomenDetails(dashboardFilter);
    }


    @PostMapping("getAnganwadiAahaarData")
    private List<AnganwadiAahaarData> getAnganwadiAahaarData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return anganwadiChildrenService.getAnganwadiAahaarData(dashboardFilter);
    }


    @PostMapping("getRationDistributionData")
    private List<RationDistribution> getRationDistributionData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return anganwadiChildrenService.getRationDistributionData(dashboardFilter);
    }


    @PostMapping("getChildrenWeightData")
    private List<WeightTrackingDTO> getChildrenWeightData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return anganwadiChildrenService.getChildrenWeightData(dashboardFilter);
    }


    @PostMapping("getDeliveryData")
    private List<DeliveryDTO> getDeliveryData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getDeliveryData(dashboardFilter);
    }


    @PostMapping("getVaccinationData")
    private List<VaccinationRecordsDTO> getVaccinationData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getVaccinationData(dashboardFilter);
    }


    @PostMapping("getAttendanceData")
    private List<DashboardAttendanceDTO> getAttendanceData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return anganwadiChildrenService.getAttendanceData(dashboardFilter);
    }

//
//    @PostMapping("getAnganwadiChildrenData")
//    private List<AnganwadiChildrenDTO> getAnganwadiChildrenData(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
//        return anganwadiChildrenService.getAnganwadiChildrenData(dashboardFilter);
//    }

    @PostMapping("getAnganwadiChildrenDetails")
    private List<AnganwadiChildrenList> getAnganwadiChildrenDetails(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return anganwadiChildrenService.getAnganwadiChildrenDetails(dashboardFilter);
    }

    @ApiIgnore
    @GetMapping("convertUnixToDate")
    private List<FamilyMemberConverted> convertUnixToDate() {
        return anganwadiChildrenService.convertUnixToDate();
    }


    @GetMapping("convertAttendanceUnixToDate")
    private List<AttendanceConverted> convertAttendanceUnixToDate() {
        return anganwadiChildrenService.convertAttendanceUnixToDate();
    }

    @GetMapping("getDashboardMasterDetails")
    private List<DashboardMaster> getDashboardMasterDetails() throws ParseException {
        return anganwadiChildrenService.getDashboardMasterDetails();
    }

    @PostMapping("/getVaccinationSchedule")
    private List<VaccinationScheduleDTO> getVaccinationSchedule(@RequestBody DashboardFilter dashboardFilter) throws ParseException {
        return familyService.getVaccinationSchedule(dashboardFilter);
    }

}
