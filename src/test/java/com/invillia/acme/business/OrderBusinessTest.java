package com.invillia.acme.business;

import com.invillia.acme.model.db.Item;
import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.dto.ItemDto;
import com.invillia.acme.model.dto.OrderDto;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.repository.ItemRepository;
import com.invillia.acme.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.HttpClientErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class OrderBusinessTest {

	private static final String ADDRESS = "address";
	private static final String DESCRIPTION = "Macbook";
	private static final Integer QUANTITY = 1;
	private static final int NUMBER_ITEMS = 1;
	private static final Integer ID = 1;
	private static final BigDecimal UNIT_PRICE = BigDecimal.valueOf(13900.0);
	private static final OrderStatus STATUS = OrderStatus.PENDING_PAYMENT;
	@InjectMocks
	private OrderBusiness business;
	@Mock
	private OrderRepository orderRepository;
	@Mock
	private ItemRepository itemRepository;

	@Test
	public void testCreateOrder() {
		business.createOrder(this.getOrderDto());

		ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);
		ArgumentCaptor<List<Item>> itemArgumentCaptor = ArgumentCaptor.forClass(List.class);
		Mockito.verify(orderRepository).save(orderArgumentCaptor.capture());
		Mockito.verify(itemRepository).saveAll(itemArgumentCaptor.capture());

		Order orderResult = orderArgumentCaptor.getValue();
		List<Item> itemResultList = itemArgumentCaptor.getValue();
		Item itemResult = itemResultList.get(0);

		Assert.assertEquals(ADDRESS, orderResult.getAddress());
		Assert.assertEquals(STATUS, orderResult.getStatus());
		Assert.assertEquals(NUMBER_ITEMS, itemResultList.size());
		Assert.assertEquals(DESCRIPTION, itemResult.getDescription());
		Assert.assertEquals(QUANTITY, itemResult.getQuantity());
		Assert.assertEquals(UNIT_PRICE, itemResult.getUnitPrice());
	}

	@Test
	public void confirmOrderTest() {
		Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.of(new Order()));
		business.confirmOrder(ID);

		ArgumentCaptor<Order> argumentCaptor = ArgumentCaptor.forClass(Order.class);
		Mockito.verify(orderRepository).save(argumentCaptor.capture());
		Order result = argumentCaptor.getValue();

		Assert.assertNotNull(result.getConfirmationDate());
	}

	@Test(expected = HttpClientErrorException.class)
	public void confirmOrderTestItemNotFound() {
		Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.empty());
		business.confirmOrder(1);
	}

	@Test(expected = RuntimeException.class)
	public void confirmOrderError() {
		Mockito.when(orderRepository.findById(ID)).thenReturn(Optional.of(Order.builder().confirmationDate(LocalDate.now()).build()));
		business.confirmOrder(ID);
	}

	private OrderDto getOrderDto() {
		OrderDto dto = new OrderDto();
		dto.setAddress(ADDRESS);
		ItemDto itemDto = new ItemDto();
		itemDto.setDescription(DESCRIPTION);
		itemDto.setQuantity(QUANTITY);
		itemDto.setUnitPrice(UNIT_PRICE);
		dto.setItems(Collections.singletonList(itemDto));
		return dto;
	}

}
