package com.org.shoppingcart.core.service;

import com.org.shoppingcart.core.exception.ApplicationException;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.response.ProductResponse;

public interface ShoppingCartService {

	public ProductResponse findAllProducts() throws ApplicationException;

	public boolean saveAll(ProductRequest request) throws ApplicationException;
}
