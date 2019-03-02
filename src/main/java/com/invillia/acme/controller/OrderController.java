package com.invillia.acme.controller;

import com.invillia.acme.business.OrderBusiness;
import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.dto.OrderDto;
import com.invillia.acme.model.dto.OrderStatus;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {

	@Autowired
	private OrderBusiness orderBusiness;

	@ApiOperation("Cria uma nova ordem com os itens")
	@RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
	public ResponseEntity<Order> createOrder(@RequestBody OrderDto dto) {
		return ResponseEntity.ok(orderBusiness.createOrder(dto));
	}

	@ApiOperation("Confirma uma ordem já criada")
	@RequestMapping(value = "/confirm/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<?> confirmOrder(@PathVariable("id") Integer id) {
		return ResponseEntity.ok(orderBusiness.confirmOrder(id));
	}

	@ApiOperation("Busca todas as ordens de acordo com o filtro")
	@RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
	public ResponseEntity<List<Order>> getOrderByFilter(@RequestParam(value = "address", required = false) String address, @RequestParam(value = "status", required = false) OrderStatus status, @RequestParam(value = "confirmationDate", required = false) String confirmationDate) {

		return ResponseEntity.ok(orderBusiness.getOrderByFilter(address, status, confirmationDate));
	}

}
