package com.anganwadi.anganwadi.controllers;

import com.anganwadi.anganwadi.domains.dto.StockItemsDTO;
import com.anganwadi.anganwadi.domains.dto.StockListDTO;
import com.anganwadi.anganwadi.domains.dto.StockOutputItemsDTO;
import com.anganwadi.anganwadi.domains.entity.AssetsStock;
import com.anganwadi.anganwadi.domains.entity.StockList;
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
    private List<StockItemsDTO> addStocks(@RequestBody List<StockItemsDTO> assetsStock, @RequestHeader String centerName) throws ParseException {
        return anganwadiChildrenService.addStocks(assetsStock, centerName);
    }


    @GetMapping("getStocks")
    private List<StockOutputItemsDTO> getStocks(@RequestHeader String centerName) {
        return anganwadiChildrenService.getStocks(centerName);
    }

    @GetMapping("getStocksLists")
    private List<StockListDTO> getStocksLists() {
        return anganwadiChildrenService.getStocksLists();
    }
}
