package com.org.shoppingcart.core.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.org.shoppingcart.core.exception.ApplicationException;
import com.org.shoppingcart.core.response.ProductResponse;
import com.org.shoppingcart.core.service.ShoppingCartService;

@RestController
@RequestMapping(value = "/api")
public class ShoppingCartController {

	private Logger logger;

	@Autowired
	private ShoppingCartService shoppingCartService;

	public ShoppingCartController(ShoppingCartService shoppingCartService) {
		super();
		this.shoppingCartService = shoppingCartService;
		this.logger = LoggerFactory.getLogger(this.getClass());
	}

	//@HystrixCommand(fallbackMethod = "fallBackExecutor", commandKey = "findAllProducts", groupKey = "shoppingCart")
	@GetMapping(value = "products")
	public ProductResponse findAllProducts() throws ApplicationException {
		logger.info("Begin method find all products");
		//ProductResponse response = new ProductResponse();
		return shoppingCartService.findAllProducts();
		/*
		 * try { return shoppingCartService.findAllProducts();
		 * 
		 * } catch (Exception e) { logger.error("Error method find all products : {}",
		 * e.getMessage()); response.setStatus("Failed"); response.setStatusCode(0); }
		 * return new ProductResponse();
		 */
	}
	
	public ProductResponse fallBackExecutor() {
		return new ProductResponse(null,"Get Products Failed",0);
	}

}