package com.org.shoppingcart.service;

import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.request.ProductRequest;
import com.org.shoppingcart.response.ProductResponse;

public interface ProductService {

	public ProductResponse findAll() throws ApplicationException;

	public ProductResponse checkoutItems(ProductRequest productDtos) throws ApplicationException;
}
