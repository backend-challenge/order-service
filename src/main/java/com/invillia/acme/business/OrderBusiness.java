package com.invillia.acme.business;

import com.invillia.acme.model.db.Item;
import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.dto.OrderDto;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.model.filter.OrderFilter;
import com.invillia.acme.repository.ItemRepository;
import com.invillia.acme.repository.OrderRepository;
import com.invillia.acme.repository.specification.OrderSpecification;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class OrderBusiness {

	@Autowired
	private OrderRepository repository;

	@Autowired
	private ItemRepository itemRepository;

	public Order createOrder(OrderDto dto) {
		List<Item> items = new ArrayList<>();
		dto.getItems()
				.stream()
				.forEach(d -> {
					Item itemEntity = new Item();
					BeanUtils.copyProperties(d, itemEntity, "id");
					items.add(itemEntity);
				});
		List<Item> itemList = itemRepository.saveAll(items);

		Order orderEntity = new Order();
		orderEntity.setAddress(dto.getAddress());
		orderEntity.setItems(itemList);
		orderEntity.setStatus(OrderStatus.PENDING_PAYMENT);
		return repository.save(orderEntity);
	}

	public Order confirmOrder(Integer id) {
		Order entity = repository.findById(id).orElseThrow(
				() -> new HttpClientErrorException(HttpStatus.NOT_FOUND, "Ordem não encontrada")
		);
		if (Objects.nonNull(entity.getConfirmationDate())) {
			throw new RuntimeException("Ordem já confimada");
		}
		entity.setConfirmationDate(LocalDate.now());
		return repository.save(entity);
	}

	public List<Order> getOrderByFilter(String address, OrderStatus status, String confirmationDate) {
		OrderFilter filter =
				OrderFilter.builder()
						.address(address)
						.status(status)
						.confirmationDate(Objects.nonNull(confirmationDate) ? confirmationDate.toString() : null)
						.build();
		return repository.findAll(OrderSpecification.getFilter(filter));
	}
}
