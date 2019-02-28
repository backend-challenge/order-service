package com.invillia.acme.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ItemDto {

	private String description;
	private BigDecimal unitPrice;
	private Integer quantity;

}
