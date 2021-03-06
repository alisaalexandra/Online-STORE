package com.sda.onlinestore.persistence.repository;

import com.sda.onlinestore.persistence.model.OrderLineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineRepository extends JpaRepository<OrderLineModel,Long> {
}
