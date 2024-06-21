package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.Vehicle;

public interface VehicleDao {
    Vehicle getByVin(int vin);
    Vehicle createVehicle(Vehicle vehicle);
    void updateVehicle(int vin, Vehicle vehicle);
    void deleteVehicle(int vin);
}
