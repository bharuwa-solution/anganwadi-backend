package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.ChildrenDTO;
import com.anganwadi.anganwadi.domains.entity.AnganwadiChildren;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@RestController
@RequestMapping("/anganwadi")
public class AnganwadiController {

    private final AnganwadiChildrenService anganwadiChildrenService;

    @Autowired
    public AnganwadiController(AnganwadiChildrenService anganwadiChildrenService) {
        this.anganwadiChildrenService = anganwadiChildrenService;
    }


    @GetMapping("/getAll")
    private String getAll() {
        return "All Users";
    }

    @PostMapping("/uploadPic")
    private String uploadPic(MultipartFile file) throws IOException {
        return anganwadiChildrenService.uploadPic(file);
    }

    @GetMapping("/getAddress")
    private String getAddress() {
        return "All Users Address";
    }

    @PostMapping("/saveChildrenRecord")
    private AnganwadiChildren saveChildrenRecord(@RequestBody  AnganwadiChildren anganwadiChildren) throws ParseException, IOException {
        return anganwadiChildrenService.saveChildrenRecord(anganwadiChildren);
    }

    @GetMapping("/getTotalChildren")
    private List<ChildrenDTO> getTotalChildren(){
        return anganwadiChildrenService.getTotalChildren();
    }


}
