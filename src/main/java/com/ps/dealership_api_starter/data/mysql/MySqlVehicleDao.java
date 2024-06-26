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
    public List<Vehicle> vehicleSearch(Integer yearMin, Integer yearMax, String make, String model, String vehicleType, String color, Integer odometerMin, Integer odometerMax, Double priceMin, Double priceMax) {
        List<Vehicle> vehicles = new ArrayList<>();

        String sql = "SELECT * FROM vehicles " +
                "WHERE (year >= ?) AND (year <= ?) " +
                "AND make LIKE ? " +
                "AND model LIKE ? " +
                "AND vehicle_type LIKE ? " +
                "AND color LIKE ? " +
                "AND (odometer >= ?) AND (odometer <= ?) " +
                "AND (price >= ?) AND (price <= ?)";

        yearMin = (yearMin == null) ? Integer.MIN_VALUE : yearMin;
        yearMax = (yearMax == null) ? Integer.MAX_VALUE : yearMax;
        make = (make == null || make.isEmpty()) ? "%" : "%" + make + "%";
        model = (model == null || model.isEmpty()) ? "%" : "%" + model + "%";
        vehicleType = (vehicleType == null || vehicleType.isEmpty()) ? "%" : "%" + vehicleType + "%";
        color = (color == null || color.isEmpty()) ? "%" : "%" + color + "%";
        odometerMin = (odometerMin == null) ? Integer.MIN_VALUE : odometerMin;
        odometerMax = (odometerMax == null) ? Integer.MAX_VALUE : odometerMax;
        priceMin = (priceMin == null) ? Double.MIN_VALUE : priceMin;
        priceMax = (priceMax == null) ? Double.MAX_VALUE : priceMax;

        try (
                Connection connection = getConnection();
        ) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);

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

            while (resultSet.next()) {
                Vehicle vehicle = mapResultset(resultSet);
                vehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
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
        String sql = "INSERT INTO vehicles(vin, year, make, model, vehicle_type, color, odometer, price, sold) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, vehicle.getVin());
            preparedStatement.setInt(2, vehicle.getYear());
            preparedStatement.setString(3, vehicle.getMake());
            preparedStatement.setString(4, vehicle.getModel());
            preparedStatement.setString(5, vehicle.getVehicleType());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setInt(7, vehicle.getOdometer());
            preparedStatement.setDouble(8, vehicle.getPrice());
            preparedStatement.setBoolean(9, vehicle.isSold());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();

                if (generatedKeys.next()) {
                    int vin = generatedKeys.getInt(1);

                    return getByVin(vin);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public void updateVehicle(int vin, Vehicle vehicle) {
        String sql = "UPDATE vehicles " +
                "SET year = ?, " +
                "make = ?, " +
                "model = ?, " +
                "vehicle_type = ?, " +
                "color = ?, " +
                "odometer = ?, " +
                "price = ?, " +
                "sold = ? " +
                "WHERE vin = ?;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, vehicle.getYear());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setString(4, vehicle.getVehicleType());
            preparedStatement.setString(5, vehicle.getColor());
            preparedStatement.setInt(6, vehicle.getOdometer());
            preparedStatement.setDouble(7, vehicle.getPrice());
            preparedStatement.setBoolean(8, vehicle.isSold());
            preparedStatement.setInt(9, vin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    @Override
    public void deleteVehicle(int vin) {
        String sql = "DELETE FROM vehicles" +
                " WHERE vin = ?;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, vin);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    protected static Vehicle mapResultset(ResultSet resultSet) throws SQLException {
        Integer vin = resultSet.getInt("vin");
        Integer year = resultSet.getInt("year");
        String make = resultSet.getString("make");
        String model = resultSet.getString("model");
        String vehicleType = resultSet.getString("vehicle_type");
        String color = resultSet.getString("color");
        Integer odometer = resultSet.getInt("odometer");
        Double price = resultSet.getDouble("price");
        Boolean sold = resultSet.getBoolean("sold");

        return new Vehicle(vin, year, make, model, vehicleType, color, odometer, price, sold);
    }
}
