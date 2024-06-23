package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.Vehicle;

import java.util.List;

public interface VehicleDao {
    List<Vehicle> vehicleSearch(Integer yearMin, Integer yearMax, String make, String model, String vehicleType, String color, Integer odometerMin, Integer odometerMax, Double priceMin, Double priceMax);
    Vehicle getByVin(int vin);
    Vehicle createVehicle(Vehicle vehicle);
    void updateVehicle(int vin, Vehicle vehicle);
    void deleteVehicle(int vin);
}
