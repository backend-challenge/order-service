package com.invillia.acme.repository.specification;

import com.invillia.acme.model.db.Order;
import com.invillia.acme.model.dto.OrderStatus;
import com.invillia.acme.model.filter.OrderFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

public class OrderSpecification {

	public static Specification<Order> getFilter(OrderFilter filter) {
		return Specification
				.where(likeAddress(filter.getAddress())
						.and(equalConfirmationDate(filter.getConfirmationDate())
								.and(equalStatus(filter.getStatus()))));
	}

	private static Specification<Order> likeAddress(String address) {
		return (root, query, cb) -> {
			if (StringUtils.isEmpty(address)) {
				return null;
			}
			return cb.like(cb.upper(root.get("name")), "%" + address.toUpperCase() + "%");
		};
	}

	private static Specification<Order> equalStatus(OrderStatus status) {
		return (root, query, cb) -> {
			if (StringUtils.isEmpty(status)) {
				return null;
			}
			return cb.equal(root.get("status"), status.name());
		};
	}

	private static Specification<Order> equalConfirmationDate(LocalDate confirmationDate) {
		return (root, query, cb) -> {
			if (StringUtils.isEmpty(confirmationDate)) {
				return null;
			}
			return cb.equal(root.get("confirmationDate"), confirmationDate);
		};
	}
}
