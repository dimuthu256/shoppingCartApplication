package com.org.shoppingcart.rest.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.org.shoppingcart.rest.bean.ProductDto;
import com.org.shoppingcart.rest.request.ItemsRequest;
import com.org.shoppingcart.rest.request.ProductRequest;
import com.org.shoppingcart.rest.response.ProductResponse;
import com.org.shoppingcart.rest.service.ShoppingCartService;
import com.org.shoppingcart.rest.service.impl.QueueMessageServiceImpl;

@RestController
@RequestMapping(value = "/api/products")
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

	@GetMapping()
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
	@PostMapping(value = "items")
	public ProductResponse saveAllItems(@RequestBody ItemsRequest itemList) {
		logger.info("Begin method save all items");
		ProductResponse response = new ProductResponse();
		try {
			queueMessageServiceImpl.send(itemList);
			response.setStatus("Success");
			response.setStatusCode(200);
		} catch (Exception e) {
			logger.error("Error in method save all items : {}", e.getMessage());
			response.setStatus("Failed");
			response.setStatusCode(0);
		}
		return response;
	}

	@CrossOrigin
	@PostMapping()
	public ProductResponse addNewProduct(@RequestParam("image") MultipartFile multipartFile,
			@RequestParam("productName") String productName, @RequestParam("productDescription") String productDesc,
			@RequestParam("productPrice") double price, @RequestParam("productQuentity") int quentity,
			@RequestParam("productStatus") boolean status) {
		logger.info("Begin method save all items");
		ProductResponse response = new ProductResponse();
		try {
			ProductRequest productDetailsRequest = new ProductRequest();
			productDetailsRequest.setProductDto(ProductDto.builder().name(productName).description(productDesc)
					.price(price).quantity(quentity).status(status).build());
			response = shoppingCartService.addNewProduct(multipartFile,productDetailsRequest);
			response.setStatus("Success");
			response.setStatusCode(200);
		} catch (Exception e) {
			logger.error("Error in method save all items : {}", e.getMessage());
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
