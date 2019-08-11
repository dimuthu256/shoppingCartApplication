package com.rg.shoppingcart.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;

import org.apache.log4j.Logger;
import org.springframework.web.context.annotation.RequestScope;

import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.request.ItemsRequest;
import com.org.shoppingcart.response.ProductResponse;
import com.org.shoppingcart.service.impl.ProductServiceImpl;

@RequestScope
@ManagedBean
public class ProductManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ItemsRequest productDtos;
	private ProductResponse productResponse;
	
	private static final Logger logger = Logger.getLogger(ProductManagedBean.class);

	public ProductManagedBean() throws ApplicationException {
		logger.info("Begin [ProductManagedBean] Loading product details");
		ProductServiceImpl productServiceImpl = new ProductServiceImpl();
		// get all products
		this.productResponse = productServiceImpl.findAll();
		logger.info("End [ProductManagedBean] Loading product details {}"+this.productResponse.toString());
	}

	public String index() throws ApplicationException {
		logger.info("Begin [ProductManagedBean - Index] Loading product details");
		ProductServiceImpl productServiceImpl = new ProductServiceImpl();
		// get all products
		this.productResponse = productServiceImpl.findAll();
		logger.info("End [ProductManagedBean - Index] Loading product details");
		return "index?faces-redirect=true";
	}

	public ItemsRequest getProductDtos() {
		return productDtos;
	}

	public void setProductDtos(ItemsRequest productDtos) {
		this.productDtos = productDtos;
	}

	public ProductResponse getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(ProductResponse productResponse) {
		this.productResponse = productResponse;
	}

}