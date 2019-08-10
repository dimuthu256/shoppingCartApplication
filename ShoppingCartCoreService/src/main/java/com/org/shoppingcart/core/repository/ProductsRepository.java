package com.org.shoppingcart.core.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.org.shoppingcart.core.entities.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer>{

	public List<Products> findByStatus(boolean status);
}
