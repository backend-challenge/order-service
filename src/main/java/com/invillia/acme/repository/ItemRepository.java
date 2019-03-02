package com.invillia.acme.repository;

import com.invillia.acme.model.db.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
}
