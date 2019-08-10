package com.org.shoppingcart.core.service;

import com.org.shoppingcart.core.exception.ApplicationException;
import com.org.shoppingcart.core.request.ProductDetailsRequest;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.request.ItemsRequest;
import com.org.shoppingcart.core.response.ProductResponse;

public interface ShoppingCartService {

	public ProductResponse findAllProducts() throws ApplicationException;
	
	public ProductResponse findProductById(int productId) throws ApplicationException;

	public boolean saveAllItems(ItemsRequest request) throws ApplicationException;

	public ProductResponse addNewProducts(ProductDetailsRequest productDetailsRequest) throws ApplicationException;

	public ProductResponse updateProductById(ProductRequest productRequest) throws ApplicationException;
	
	public ProductResponse deleteProductById(int productRequest) throws ApplicationException;
	
}
