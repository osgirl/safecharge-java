package com.safecharge.request;

import com.safecharge.request.basic.CashierUserRequest;
import com.safecharge.util.Constants;
import com.safecharge.util.ValidChecksum;

@ValidChecksum(orderMappingName = Constants.ChecksumOrderMapping.ADD_CASHIER_USER)
public class CreateUserRequest extends CashierUserRequest {

    private static final long serialVersionUID = 3945508076987109885L;

    public CreateUserRequest() {
        super();
    }
}