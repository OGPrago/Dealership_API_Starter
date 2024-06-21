package com.ps.dealership_api_starter.controllers;

import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("vehicles")
@CrossOrigin
public class VehicleController {
    private VehicleDao vehicleDao;

    @Autowired
    public VehicleController(VehicleDao vehicleDao) {
        this.vehicleDao = vehicleDao;
    }

    @GetMapping("")
    public List<Vehicle> vehicleSearch(
            @RequestParam(name="yearMin", required = false) int yearMin,
            @RequestParam(name="yearMax", required = false) int yearMax,
            @RequestParam(name="make", required = false) String make,
            @RequestParam(name="model", required = false) String model,
            @RequestParam(name="vehicleType", required = false) String vehicleType,
            @RequestParam(name="color", required = false) String color,
            @RequestParam(name="odometerMin", required = false) int odometerMin,
            @RequestParam(name="odomterMax", required = false) int odometerMax,
            @RequestParam(name="priceMin", required = false) double priceMin,
            @RequestParam(name="priceMax", required = false) double priceMax
    ) {
        try
        {
            return vehicleDao.vehicleSearch(yearMin,  yearMax,  make,  model,  vehicleType,  color,  odometerMin, odometerMax, priceMin,  priceMax);
        }
        catch(Exception ex)
        {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Oops... our bad.");
        }
    }

    @GetMapping("{vin}")
    public Vehicle getByVin(@PathVariable int vin) {
      return vehicleDao.getByVin(vin);
    }
}
