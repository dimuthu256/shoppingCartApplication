
package com.org.shoppingcart.rest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.org.shoppingcart.rest.response.ProductResponse;
import com.org.shoppingcart.rest.service.ShoppingCartService;
import com.org.shoppingcart.rest.service.impl.QueueMessageServiceImpl;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingCartWebServiceTests {

	@Autowired
	ShoppingCartService shoppingCartService;

	@Autowired
	QueueMessageServiceImpl queueMessageServiceImpl;

	public ShoppingCartWebServiceTests() {
	}
	
	//@Test
	public void findAllProducts() {
		try {
			ProductResponse response = shoppingCartService.findAllProducts();
			assertEquals(200, response.getStatusCode());
		} catch (Exception e) {
			assert false;
		}
	}

}
