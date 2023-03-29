package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.DashboardFamilyData;
import com.anganwadi.anganwadi.domains.dto.LocationFilter;
import com.anganwadi.anganwadi.domains.dto.TotalChildrenData;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;

@RestController
@RequestMapping("dashboard")
public class DashboardController {

    private final FamilyService familyService;

    @Autowired
    public DashboardController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @PostMapping("getDashboardFamilyData")
    private DashboardFamilyData getDashboardFamilyData(@RequestBody LocationFilter filter) {
        return familyService.getDashboardFamilyData(filter);
    }

    @PostMapping("getTotalChildrenData")
    private TotalChildrenData getTotalChildrenData(@RequestParam(required = false) String caste, @RequestParam String gender, @RequestParam String month) throws ParseException {
        return familyService.getTotalChildrenData(caste,gender,month);
    }


}
