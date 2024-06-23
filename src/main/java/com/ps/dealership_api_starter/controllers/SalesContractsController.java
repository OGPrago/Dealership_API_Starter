package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("salescontracts")
@CrossOrigin
public class SalesContractsController {
    private SalesContractDao salesContractDao;

    @Autowired
    public SalesContractsController(SalesContractDao salesContractDao) {
        this.salesContractDao = salesContractDao;
    }

    @GetMapping("{id}")
    public SalesContract getSalesContractsById(@PathVariable int id) {
        try {
            var product = salesContractDao.getSalesContractsById(id);

            if (product == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }
            return product;
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @PostMapping()
    public SalesContract addSalesContract(@RequestBody SalesContract salesContract) {
        try {
            return salesContractDao.addSalesContracts(salesContract);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }
}
