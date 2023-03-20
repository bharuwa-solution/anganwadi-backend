package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.StockItemsDTO;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import com.anganwadi.anganwadi.service_impl.service.FamilyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("stock")
public class StockController {

    @Autowired
    private final AnganwadiChildrenService anganwadiChildrenService;

    @Autowired
    public StockController(AnganwadiChildrenService anganwadiChildrenService) {
        this.anganwadiChildrenService = anganwadiChildrenService;
    }

    @GetMapping("getAvailableItems")
    private List<StockItemsDTO> getAvailableItems(@RequestHeader String centerName) {
        return anganwadiChildrenService.getAvailableItems(centerName);
    }

}
