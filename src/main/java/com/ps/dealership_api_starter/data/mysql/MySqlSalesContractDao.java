package com.ps.dealership_api_starter.data.mysql;

import com.ps.dealership_api_starter.data.SalesContractDao;
import com.ps.dealership_api_starter.models.Dealership;
import com.ps.dealership_api_starter.models.SalesContract;
import com.ps.dealership_api_starter.models.Vehicle;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlSalesContractDao extends MySqlDaoBase implements SalesContractDao {
    public MySqlSalesContractDao(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public SalesContract getSalesContractsById(int contractId) {
        String sql = "SELECT * FROM sales_contracts WHERE contract_id = ?;";

        try (Connection connection = getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, contractId);

            ResultSet row = preparedStatement.executeQuery();

            if (row.next()) {
                return mapRow(row);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public SalesContract addSalesContracts(SalesContract salesContract) {
        return null;
    }

    protected static SalesContract mapRow(ResultSet row) throws SQLException {
         int contractId = row.getInt("contract_id");
         String contractDate = row.getString("contract_date");
         String customerName = row.getString("customer_name");
         String customerEmail = row.getString("customer_email");
         int vin = row.getInt("vin");
         double salesTax = row.getDouble("sales_tax");
         double recordingFee = row.getDouble("recording_fee");
         double processingFee = row.getDouble("processing_fee");
         double totalPrice = row.getDouble("total_price");
         String financeOption = row.getString("finance_option");
         double monthlyPayment = row.getDouble("monthly_payment");

        return new SalesContract(contractId, contractDate, customerName, customerEmail, vin, salesTax,recordingFee, processingFee, totalPrice, financeOption, monthlyPayment);
    }
}
