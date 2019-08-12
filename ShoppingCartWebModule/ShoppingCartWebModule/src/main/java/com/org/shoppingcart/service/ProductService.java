package com.org.shoppingcart.service;

import org.primefaces.model.UploadedFile;

import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.request.ItemsRequest;
import com.org.shoppingcart.request.ProductDetailsRequest;
import com.org.shoppingcart.response.ProductResponse;

public interface ProductService {

	public ProductResponse findAll() throws ApplicationException;

	public ProductResponse checkoutItems(ItemsRequest productDtos) throws ApplicationException;

	public ProductResponse addNewProduct(UploadedFile multipartFile,ProductDetailsRequest productDetailsRequest) throws ApplicationException;
}
