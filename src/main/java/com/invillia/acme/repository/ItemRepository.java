package com.invillia.acme.repository;

import com.invillia.acme.model.db.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ItemRepository extends JpaRepository<Order, Integer> {
}
