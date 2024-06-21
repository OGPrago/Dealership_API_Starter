package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.VehicleDao;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.stereotype.Component;


import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlVehicleDao extends MySqlDaoBase implements VehicleDao {

    public MySqlVehicleDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public List<Vehicle> vehicleSearch(int yearMin, int yearMax, String make, String model, String vehicleType, String color, int odometerMin, int odometerMax, double priceMin, double priceMax) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE price BETWEEN ? AND ?" +
                "AND make LIKE '%?%' AND model LIKE '%?%' " +
                "AND year BETWEEN ? AND ?" +
                "AND color LIKE '%?%'" +
                "AND odometer BETWEEN ? AND ?" +
                "AND vehicle_type LIKE '%?%'";

        try (
                Connection connection = getConnection();
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, yearMin);
            preparedStatement.setInt(2, yearMax);
            preparedStatement.setString(3, make);
            preparedStatement.setString(4, model);
            preparedStatement.setString(5, vehicleType);
            preparedStatement.setString(6, color);
            preparedStatement.setInt(7, odometerMin);
            preparedStatement.setInt(8, odometerMax);
            preparedStatement.setDouble(9, priceMin);
            preparedStatement.setDouble(10, priceMax);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return (List<Vehicle>) mapResultset(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Vehicle getByVin(int vin) {
        String query = "SELECT * FROM vehicles WHERE vin = ?";

        try (
                Connection connection = getConnection();
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, vin);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return mapResultset(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    protected static Vehicle mapResultset(ResultSet resultSet) throws SQLException {
        int vin = resultSet.getInt("vin");
        int year = resultSet.getInt("year");
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String vehicleType = resultSet.getString("vehicle_type");
        String color = resultSet.getString("color");
        int odometer = resultSet.getInt("odometer");
        double price = resultSet.getDouble("price");
        boolean sold = resultSet.getBoolean("sold");

        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
    }
}
