package com.ps.dealership_api_starter.data;

import com.ps.dealership_api_starter.models.LeaseContract;

public interface LeaseContractDao {
    LeaseContract getLeaseContracts(int contractId);
    LeaseContract addLeaseContracts(LeaseContract leaseContract);
}
