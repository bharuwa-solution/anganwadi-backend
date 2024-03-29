package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.*;
import com.anganwadi.anganwadi.service_impl.service.AnganwadiChildrenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
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
    private List<StockListDTO> getAvailableItems() {
        return anganwadiChildrenService.getAvailableItems();
    }

    @PostMapping("addStocks")
    private List<StockItemsDTO> addStocks(@RequestBody List<StockItemsDTO> assetsStock, @RequestHeader String centerId) throws ParseException {
        return anganwadiChildrenService.addStocks(assetsStock, centerId);
    }


    @GetMapping("getStocks")
    private StockOutputItemsDTO getStocks(@RequestHeader String centerId, @RequestParam String selectedMonth) throws ParseException {
        return anganwadiChildrenService.getStocks(centerId, selectedMonth);
    }

//    @GetMapping("getStocksLists")
//    private List<StockListDTO> getStocksLists() {
//        return anganwadiChildrenService.getStocksLists();
//    }

    @PostMapping("saveDistributionList")
    private List<StockDistributionDTO> saveDistributionList(@RequestBody List<StockDistributionDTO> stockDistributionDTOS, @RequestHeader String centerId) throws ParseException {
        return anganwadiChildrenService.saveDistributionList(stockDistributionDTOS, centerId);
    }

    @GetMapping("getDistributionList")
    private List<DistributionOutputList> getDistributionList(@RequestHeader String centerId, String selectedMonth) throws ParseException {
        return anganwadiChildrenService.getDistributionList(centerId, selectedMonth);
    }

}
