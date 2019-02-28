package com.invillia.acme.model.filter;

import com.invillia.acme.model.dto.OrderStatus;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@Builder
public class OrderFilter {

	private String address;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate confirmationDate;
	private OrderStatus status;
}
