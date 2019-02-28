package com.invillia.acme.controller;

import com.invillia.acme.model.db.Item;
import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.dto.OrderDto;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.model.filter.OrderFilter;
import com.invillia.acme.repository.ItemRepository;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.specification.OrderSpecification;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private ItemRepository itemRepository;

    @ApiOperation("Cria uma nova ordem com os itens")
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public ResponseEntity<Order> createOrder(@RequestBody OrderDto dto) {
        List<Item> items = new ArrayList<>();
        dto.getItems()
                .stream()
                .forEach(d -> {
                    Item itemEntity = new Item();
                    BeanUtils.copyProperties(d, itemEntity, "id");
                    items.add(itemEntity);
                });
        Order orderEntity = new Order();
        orderEntity.setAddress(dto.getAddress());
        orderEntity.setItems(items);
        orderEntity.setStatus(OrderStatus.PENDING_PAYMENT);
        return ResponseEntity.ok(repository.save(orderEntity));
    }

    @ApiOperation("Confirma uma ordem já criada")
    @RequestMapping(value = "/confirm/{id}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Order> confirmOrder(@PathVariable("id") Integer id) {
        Order entity = repository.findById(id).orElseThrow(
                () -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Ordem não encontrada")
        );
        if (Objects.nonNull(entity.getConfirmationDate())) {
            throw new RuntimeException("Ordem já confimada");
        }

        entity.setId(id);
        entity.setConfirmationDate(LocalDate.now());
        return ResponseEntity.ok(repository.save(entity));
    }

    @ApiOperation("Cria uma nova ordem com os itens")
    @RequestMapping(method = RequestMethod.GET, produces = "application/json", consumes = "application/json")
    public ResponseEntity<List<Order>> getOrderByFilter(@RequestParam(value = "address", required = false) String address, @RequestParam(value = "status", required = false) OrderStatus status, @RequestParam(value = "confirmationDate", required = false) LocalDate confirmationDate) {
        OrderFilter filter =
                OrderFilter.builder()
                        .address(address)
                        .status(status)
                        .confirmationDate(confirmationDate)
                        .build();
        // TODO: Validar os filtros
        return ResponseEntity.ok(repository.findAll(OrderSpecification.getFilter(filter)));
    }

}
