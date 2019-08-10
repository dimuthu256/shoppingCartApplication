package com.org.shoppingcart.service;

import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.request.ItemsRequest;
import com.org.shoppingcart.response.ProductResponse;

public interface ProductService {

	public ProductResponse findAll() throws ApplicationException;

	public ProductResponse checkoutItems(ItemsRequest productDtos) throws ApplicationException;
}
