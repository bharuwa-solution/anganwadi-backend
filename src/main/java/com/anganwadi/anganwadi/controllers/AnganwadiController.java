package com.anganwadi.anganwadi.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/anganwadi")
public class AnganwadiController {


    @GetMapping("/getAll")
    private String getAll(){
        return "All Users";
    }

    @GetMapping("/getAddress")
    private String getAddress(){
        return "All Users Address";
    }



}
