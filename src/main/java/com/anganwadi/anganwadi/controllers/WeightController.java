package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.WeightRecordsDTO;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/weight")
public class WeightController {

    private final FamilyService familyService;

    @Autowired
    public WeightController(FamilyService familyService) {
        this.familyService = familyService;
    }

    @PostMapping("/saveWeightRecords")
    private WeightRecordsDTO saveWeightRecords(@RequestBody WeightRecordsDTO weightRecordsDTO, @RequestHeader String centerName) throws ParseException {
        return familyService.saveWeightRecords(weightRecordsDTO,centerName);
    }

    @GetMapping("/getChildWeightRecords")
    private List<WeightRecordsDTO> getWeightRecords( @RequestParam String childId) {
        return familyService.getWeightRecords(childId);
    }

    @GetMapping("/getAllChildWeightRecords")
    private List<WeightRecordsDTO> getAllChildWeightRecords(@RequestHeader String centerName) {
        return familyService.getAllChildWeightRecords(centerName);
    }

}
