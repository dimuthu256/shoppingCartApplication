package com.org.shoppingcart.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.shoppingcart.rest.request.ProductRequest;
import com.org.shoppingcart.rest.response.ProductResponse;
import com.org.shoppingcart.rest.service.ShoppingCartService;
import com.org.shoppingcart.rest.service.impl.QueueMessageServiceImpl;

@RestController
@RequestMapping(value = "/api")
public class ShoppingCartWebController {

	private Logger logger;

	@Autowired
	QueueMessageServiceImpl queueMessageServiceImpl;

	@Autowired
	private ShoppingCartService shoppingCartService;

	public ShoppingCartWebController(ShoppingCartService shoppingCartService) {
		super();
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.shoppingCartService = shoppingCartService;
	}

	@GetMapping(value = "products")
	public ProductResponse findAllProducts() {
		try {
			logger.info("Begin method find all products");
			return shoppingCartService.findAllProducts();
		} catch (Exception e) {
			logger.error("Error in method find all products : {}", e.getMessage());
		}
		return new ProductResponse();
	}

	@CrossOrigin
	@PostMapping(value = "products")
	public ProductResponse saveAllProducts(@RequestBody ProductRequest productList) {
		logger.info("Begin method save all products");
		ProductResponse response = new ProductResponse();
		try {
			queueMessageServiceImpl.send(productList);
			response.setStatus("Success");
			response.setStatusCode(200);
		} catch (Exception e) {
			logger.error("Error in method save all products : {}", e.getMessage());
			response.setStatus("Failed");
			response.setStatusCode(0);
		}
		return response;
	}

	@GetMapping(value = "test")
	public String test() {
		logger.info("Begin test method");
		return "Test Methed Executed";
	}

}