package com.org.shoppingcart.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.shoppingcart.core.entities.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>{

	public List<OrderDetails> findAllByProductsId(int productRequest);

}
