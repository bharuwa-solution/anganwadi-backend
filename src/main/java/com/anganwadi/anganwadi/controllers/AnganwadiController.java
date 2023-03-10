package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.ChildrenDTO;
import com.anganwadi.anganwadi.domains.dto.DashboardDetails;
import com.anganwadi.anganwadi.domains.dto.UploadDTO;
import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("anganwadi")
public class AnganwadiController {

    private final AnganwadiChildrenService anganwadiChildrenService;
    private final FamilyService familyService;

    @Autowired
    public AnganwadiController(AnganwadiChildrenService anganwadiChildrenService, FamilyService familyService) {
        this.anganwadiChildrenService = anganwadiChildrenService;
        this.familyService = familyService;
    }

    @GetMapping("getDashboardDetails")
    private DashboardDetails getDashboardDetails() {
        return anganwadiChildrenService.getDashboardDetails();
    }

    @PostMapping("getAttendance")
    private DashboardDetails getAttendance() {
        return anganwadiChildrenService.getAttendance();
    }


    @PostMapping(path = "uploadPic",  consumes = "multipart/form-data")
    private UploadDTO uploadPic(@RequestPart MultipartFile file) throws IOException {

        return anganwadiChildrenService.uploadPic(file);
    }

    @GetMapping("getAddress")
    private String getAddress() {
        return "All Users Address";
    }

    @PostMapping("saveChildrenRecord")
    private AnganwadiChildren saveChildrenRecord(@RequestBody AnganwadiChildren anganwadiChildren) throws ParseException, IOException {
        return anganwadiChildrenService.saveChildrenRecord(anganwadiChildren);
    }

    @GetMapping("getTotalChildren")
    private List<ChildrenDTO> getTotalChildren() {
        return anganwadiChildrenService.getTotalChildren();
    }


}
