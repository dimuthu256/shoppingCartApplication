package com.org.shoppingcart.core.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.shoppingcart.core.exception.ApplicationException;
import com.org.shoppingcart.core.request.ProductDetailsRequest;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.response.ProductResponse;
import com.org.shoppingcart.core.service.ShoppingCartService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(value = "/api/products")
public class ShoppingCartController {

	private Logger logger;

	@Autowired
	private ShoppingCartService shoppingCartService;

	public ShoppingCartController(ShoppingCartService shoppingCartService) {
		super();
		this.shoppingCartService = shoppingCartService;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	@GetMapping()
	@ApiOperation("Get All Active Products")
	public ProductResponse findAllProducts() {
		logger.info("Begin method find all products");
		try {
			return shoppingCartService.findAllProducts();
		} catch (ApplicationException e) {
			logger.error("Error method find all products : {}", e.getMessage());
			return ProductResponse.builder().status(e.getMessage()).statusCode(0).build();
		}
	}

	@GetMapping(value = "{productId}")
	@ApiOperation("Find Product By Product ID")
	public ProductResponse findProductById(@Valid @PathVariable("productId") Integer productId) {
		logger.info("Begin method find product by id");
		try {
			return shoppingCartService.findProductById(productId);
		} catch (ApplicationException e) {
			logger.error("Error method find product by id : {}", e.getMessage());
			return ProductResponse.builder().status(e.getMessage()).statusCode(0).build();
		}
	}

	@PostMapping()
	@ApiOperation("Add New Products")
	public ProductResponse addNewProducts(@Valid @RequestBody ProductDetailsRequest productDetailsRequest) {
		logger.info("Begin method add products");
		try {
			return this.shoppingCartService.addNewProducts(productDetailsRequest);
		} catch (ApplicationException e) {
			logger.error("Error method find all products : {}", e.getMessage());
			return ProductResponse.builder().status(e.getMessage()).statusCode(0).build();
		}
	}

	@PutMapping()
	@ApiOperation("Update Existing Product")
	public ProductResponse updateProductById(@Valid @RequestBody ProductRequest productRequest) {
		logger.info("Begin method update product by id");
		try {
			return this.shoppingCartService.updateProductById(productRequest);
		} catch (ApplicationException e) {
			logger.error("Error method update product by id : {}", e.getMessage());
			return ProductResponse.builder().status(e.getMessage()).statusCode(0).build();
		}
	}

	@DeleteMapping(value = "{productId}")
	@ApiOperation("Delete Selected Product")
	public ProductResponse deleteProductById(@Valid @PathVariable("productId") int productId) {
		logger.info("Begin method delete product by id");
		try {
			return this.shoppingCartService.deleteProductById(productId);
		} catch (ApplicationException e) {
			logger.error("Error method delete product : {}", e.getMessage());
			return ProductResponse.builder().status(e.getMessage()).statusCode(0).build();
		}
	}
}
