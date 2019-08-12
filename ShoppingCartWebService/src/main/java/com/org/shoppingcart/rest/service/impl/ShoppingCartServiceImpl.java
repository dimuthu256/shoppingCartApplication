package com.org.shoppingcart.rest.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.org.shoppingcart.rest.config.GsonUtil;
import com.org.shoppingcart.rest.config.REST_CONTROLLER_URL;
import com.org.shoppingcart.rest.config.ServiceManager;
import com.org.shoppingcart.rest.request.ProductRequest;
import com.org.shoppingcart.rest.response.ProductResponse;
import com.org.shoppingcart.rest.service.ShoppingCartService;

@Service
public class ShoppingCartServiceImpl extends ServiceManager implements ShoppingCartService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	private static final long serialVersionUID = 1L;

	@Value("${shopping.cart.service.url}")
	String seviceUrl;

	@Value("${shopping.cart.file.location.temp}")
	String tempLocation;

	@Autowired
	public ShoppingCartServiceImpl() {
		super();
	}

	@Override
	@HystrixCommand(fallbackMethod = "fallBackfindAllExecutor", commandKey = "findAllProducts", groupKey = "shoppingCart")
	public ProductResponse findAllProducts() {
		logger.info("Begin Method Find All Products Service");
		restTemplate = new RestTemplate();
		String strResponse = restTemplate.getForObject(seviceUrl + REST_CONTROLLER_URL.GET_ALL_ITEMS, String.class);
		logger.info("End Method Find All Products Service. Response : {}", strResponse);
		return GsonUtil.getFromObject(strResponse, ProductResponse.class);
		
	}

	@Override

	@HystrixCommand(fallbackMethod = "fallBackAddProductExecutor", commandKey = "addProducts", groupKey = "shoppingCart")
	public ProductResponse addNewProduct(MultipartFile multipartFile, ProductRequest productDetailsRequest) {
		logger.info("Begin Method Add New Product Service : {}", productDetailsRequest);
		try {

			// Original file name
			String originalFileName = StringUtils.cleanPath(multipartFile.getOriginalFilename());

			File directory = new File(tempLocation);
			if (!directory.exists()) {
				logger.info("Create directory ");
				directory.mkdirs();
			}
			byte[] buf = new byte[1024];
			File files = new File(tempLocation + originalFileName);
			try (InputStream inputStream = multipartFile.getInputStream();
					FileOutputStream fileOutputStream = new FileOutputStream(files)) {
				int numRead = 0;
				while ((numRead = inputStream.read(buf)) >= 0) {
					fileOutputStream.write(buf, 0, numRead);
				}
			}

			restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.MULTIPART_FORM_DATA);

			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("image", new FileSystemResource(createFilePath(tempLocation, originalFileName)));
			body.add("productName", productDetailsRequest.getProductDto().getName());
			body.add("productDescription", productDetailsRequest.getProductDto().getDescription());
			body.add("productPrice", productDetailsRequest.getProductDto().getPrice());
			body.add("productQuentity", productDetailsRequest.getProductDto().getQuantity());
			body.add("productStatus", productDetailsRequest.getProductDto().isStatus());

			HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
			ResponseEntity<ProductResponse> responseEntity = restTemplate.exchange(
					seviceUrl + REST_CONTROLLER_URL.ADD_NEW_PRODUCT, HttpMethod.POST, entity, ProductResponse.class);
			return responseEntity.getBody();

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String createFilePath(String filePath, String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append(filePath);
		builder.append(fileName);
		return builder.toString();
	}

	public ProductResponse fallBackfindAllExecutor() {
		logger.info("Begin Method Fall Back Executor");
		return new ProductResponse(null, "Get Products Failed", 0);
	}

	public ProductResponse fallBackAddProductExecutor(MultipartFile multipartFile,
			ProductRequest productDetailsRequest) {
		logger.info("Begin Method Fall Back Executor : {} -> {}", multipartFile.getOriginalFilename(),
				productDetailsRequest);
		return new ProductResponse(null, "Add Product Failed", 0);
	}

}
