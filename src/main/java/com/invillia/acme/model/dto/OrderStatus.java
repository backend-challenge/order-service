package com.invillia.acme.model.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {

    PENDING_PAYMENT,
    APPROVED,
    REFUNDED,
    REFUND_REQUEST
}
