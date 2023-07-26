package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.GetVaccinationDTO;
import com.anganwadi.anganwadi.domains.dto.PerVaccineRecord;
import com.anganwadi.anganwadi.domains.dto.SaveVaccinationDTO;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("vaccination")
public class VaccinationController {

    private final FamilyService familyService;

    @Autowired
    public VaccinationController(FamilyService familyService) {
        this.familyService = familyService;
    }

//    @ApiIgnore
//    @PostMapping("/saveVaccinationDetails")
//    private SaveVaccinationDTO saveVaccinationDetails(@RequestBody SaveVaccinationDTO saveVaccinationDTO, @RequestHeader String centerId, @RequestHeader String centerName) throws ParseException {
//        return familyService.saveVaccinationDetails(saveVaccinationDTO, centerId, centerName);
//    }

    @GetMapping("/getVaccinationRecords")
    private List<GetVaccinationDTO> getVaccinationRecords(@RequestParam(required = false) String vaccineCode, @RequestHeader String centerId) {
        return familyService.getVaccinationRecords(vaccineCode, centerId);
    }

    @GetMapping("/getVaccinationByChildId")
    private List<PerVaccineRecord> getVaccinationByChildId(@RequestParam String childId) {
        return familyService.getVaccinationByChildId(childId);
    }

}
