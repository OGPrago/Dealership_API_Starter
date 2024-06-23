package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.SalesContract;

public interface SalesContractDao {
    SalesContract getSalesContractsById(int contractId);
    SalesContract addSalesContracts(SalesContract salesContract);
}
