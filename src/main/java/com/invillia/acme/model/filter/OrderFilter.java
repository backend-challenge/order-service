package com.invillia.acme.model.filter;

import com.invillia.acme.model.dto.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OrderFilter {

    private String address;
    private LocalDate confirmationDate;
    private OrderStatus status;
}
