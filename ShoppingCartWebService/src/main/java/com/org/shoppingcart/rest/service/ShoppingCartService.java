package com.org.shoppingcart.rest.service;

import org.springframework.web.multipart.MultipartFile;

import com.org.shoppingcart.rest.request.ProductRequest;
import com.org.shoppingcart.rest.response.ProductResponse;

public interface ShoppingCartService {

	public ProductResponse findAllProducts();

	public ProductResponse addNewProduct(MultipartFile multipartFile, ProductRequest productDetailsRequest);
}
