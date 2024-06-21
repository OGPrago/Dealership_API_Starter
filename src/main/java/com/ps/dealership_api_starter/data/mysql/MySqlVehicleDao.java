package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;

import javax.sql.DataSource;

public class MySqlVehicleDao extends MySqlDealershipDao implements VehicleDao {
    public MySqlVehicleDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Vehicle getByVin(int vin) {
        return null;
    }

    @Override
    public Vehicle createVehicle(Vehicle vehicle) {
        return null;
    }

    @Override
    public void updateVehicle(int vin, Vehicle vehicle) {

    }

    @Override
    public void deleteVehicle(int vin) {

    }
}
