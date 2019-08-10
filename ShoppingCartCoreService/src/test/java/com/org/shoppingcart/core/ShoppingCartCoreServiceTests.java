package com.org.shoppingcart.core;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.org.shoppingcart.core.bean.ProductDto;
import com.org.shoppingcart.core.config.MessageConfig;
import com.org.shoppingcart.core.controller.ShoppingCartController;
import com.org.shoppingcart.core.controller.ShoppingCartMessageListener;
import com.org.shoppingcart.core.request.ProductDetailsRequest;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.response.ProductResponse;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ShoppingCartCoreServiceTests {

	@Autowired
	ShoppingCartController shoppingCartController;

	@Autowired
	ShoppingCartMessageListener shoppingCartMessageListener;

	@Autowired
	private MessageConfig messageConfig;

	@Test
	@Order(1)
	public void findAllProducts_s1() {
		try {
			ProductResponse response = shoppingCartController.findAllProducts();
			assertEquals(200, response.getStatusCode());
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(2)
	public void addNewProducts_s1() {
		try {
			ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();
			List<ProductDto> productDtoList = new ArrayList<ProductDto>();
			for (int i = 1; i < 10; i++) {
				productDtoList.add(ProductDto.builder().name("item_" + i).description("item_desc_" + i).price(i * 100)
						.quantity(i * 10).status(true).build());
			}
			productDetailsRequest.setProductList(productDtoList);
			ProductResponse response = shoppingCartController.addNewProducts(productDetailsRequest);
			assertEquals(200, response.getStatusCode());
			assertEquals(messageConfig.getSuccess(), response.getStatus());
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(3)
	public void addNewProducts_f1() {
		ProductDetailsRequest productDetailsRequest = new ProductDetailsRequest();
		try {
			List<ProductDto> productDtoList = new ArrayList<ProductDto>();
			for (int i = 1; i < 10; i++) {
				productDtoList.add(ProductDto.builder().name(null).description("item_desc_" + i).price(i * 100)
						.quantity(i * 10).status(true).build());
			}
			productDetailsRequest.setProductList(productDtoList);
			ProductResponse response = shoppingCartController.addNewProducts(productDetailsRequest);
			assertEquals(0, response.getStatusCode());
		} catch (Exception e) {
			assert true;
		}
	}

	@Test
	@Order(4)
	public void findAllProducts_s2() {
		try {
			ProductResponse response = shoppingCartController.findAllProducts();
			assertEquals(200, response.getStatusCode());
			assertThat(response.getDataDto().getProductList().size() > 0);
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(5)
	public void findProductById_s1() {
		try {
			ProductResponse response = shoppingCartController.findProductById(1);
			assertEquals(200, response.getStatusCode());
			assertThat(response.getDataDto().getProductList().size() > 0);
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(6)
	public void findProductById_f1() {
		try {
			ProductResponse response = shoppingCartController.findProductById(100);
			assertEquals(0, response.getStatusCode());
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(7)
	public void saveAllItems_s1() {
		try {
			String message = "{\"itemList\":[{\"productDto\":{\"id\":1},\"quantity\":1,\"totalAmount\":100.0}]}";
			boolean result = shoppingCartMessageListener.onMessage(message);
			if (result)
				assert true;
			else
				assert false;
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(8)
	public void saveAllItems_s2() {
		try {
			String message = "{\"itemList\":[{\"productDto\":{\"id\":1},\"quantity\":2,\"totalAmount\":200.0}]}";
			boolean result = shoppingCartMessageListener.onMessage(message);
			if (result)
				assert true;
			else
				assert false;
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(9)
	public void saveAllItems_s3() {
		try {
			String message = "{\"itemList\":[{\"productDto\":{\"id\":1},\"quantity\":20,\"totalAmount\":2000.0}]}";
			boolean result = shoppingCartMessageListener.onMessage(message);
			if (result)
				assert true;
			else
				assert false;
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(10)
	public void saveAllItems_f1() {
		try {
			String message = "{\"itemList\":[{\"productDto\":{\"id\":100},\"quantity\":2,\"totalAmount\":200.0}]}";
			boolean result = shoppingCartMessageListener.onMessage(message);
			if (result)
				assert false;
			else
				assert true;
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(11)
	public void updateProductById_s1() {
		ProductRequest productRequest = new ProductRequest();
		try {
			productRequest.setProductDto(ProductDto.builder().id(1).name("item_name_1_up1")
					.description("item_name_desc_1_up1").price(200).quantity(20).status(true).build());
			ProductResponse response = shoppingCartController.updateProductById(productRequest);
			assertEquals(200, response.getStatusCode());
			assertEquals(messageConfig.getSuccess(), response.getStatus());
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(11)
	public void updateProductById_f1() {
		ProductRequest productRequest = new ProductRequest();
		try {
			productRequest.setProductDto(ProductDto.builder().id(1).name(null).description("item_name_desc_1_up1")
					.price(200).quantity(20).status(true).build());
			ProductResponse response = shoppingCartController.updateProductById(productRequest);
			assertEquals(0, response.getStatusCode());
		} catch (Exception e) {
			assert true;
		}
	}

	@Test
	@Order(12)
	public void updateProductById_f2() {
		ProductRequest productRequest = new ProductRequest();
		try {
			productRequest.setProductDto(ProductDto.builder().id(100).name("item_name_1_up1")
					.description("item_name_desc_1_up1").price(200).quantity(20).status(true).build());
			ProductResponse response = shoppingCartController.updateProductById(productRequest);
			assertEquals(0, response.getStatusCode());
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(13)
	public void deleteProductById_s1() {
		try {
			ProductResponse response = shoppingCartController.deleteProductById(1);
			assertEquals(200, response.getStatusCode());
			assertEquals(messageConfig.getSuccess(), response.getStatus());
		} catch (Exception e) {
			assert false;
		}
	}

	@Test
	@Order(14)
	public void deleteProductById_f1() {
		try {
			ProductResponse response = shoppingCartController.deleteProductById(100);
			assertEquals(0, response.getStatusCode());
		} catch (Exception e) {
			assert false;
		}
	}
}
