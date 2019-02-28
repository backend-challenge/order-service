package com.invillia.acme.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderDto {

	private String address;
	private List<ItemDto> items;

}
