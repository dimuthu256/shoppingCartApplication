package com.org.shoppingcart.service.impl;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Collections;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.primefaces.model.UploadedFile;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.org.shoppingcart.controller.util.GsonUtil;
import com.org.shoppingcart.controller.util.REST_CONTROLLER_URL;
import com.org.shoppingcart.controller.util.ServiceManager;
import com.org.shoppingcart.controller.util.exception.ApplicationException;
import com.org.shoppingcart.request.ItemsRequest;
import com.org.shoppingcart.request.ProductRequest;
import com.org.shoppingcart.response.ProductResponse;

public class ProductServiceImpl extends ServiceManager {

	private static final long serialVersionUID = 1L;
	private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);
	private String tempLocation = IMG_UPLOAD_FILE_PATH;

	public ProductResponse findAll() throws ApplicationException {
		try {
			final String restURL = CONTEXT_PATH + REST_CONTROLLER_URL.GET_ALL_ITEMS;
			logger.info("Find All Rest Service URL :" + restURL);
			restTemplate = new RestTemplate();
			String strResponse = restTemplate.getForObject(restURL, String.class);
			logger.info("Find All Response :" + strResponse);
			return GsonUtil.getFromObject(strResponse, ProductResponse.class);
		} catch (Exception e) {
			logger.error("Error Occured in Product Service findAll: {}", e);
			throw new ApplicationException("Error Occured in Product Service findAll");
		}
	}

	public ProductResponse checkoutItems(ItemsRequest productDtos) throws ApplicationException {
		try {

			final String restURL = CONTEXT_PATH + REST_CONTROLLER_URL.SUBMIT_SELECTED_ITEMS;
			logger.info("Submit All Rest Service URL :" + restURL);
			RestTemplate restTemplate = new RestTemplate();
			String request = GsonUtil.getToString(productDtos, ItemsRequest.class);
			// send request and parse result
			HttpEntity<String> entity = new HttpEntity<>(request, headers());
			ResponseEntity<String> response = restTemplate.exchange(restURL, HttpMethod.POST, entity, String.class);
			return GsonUtil.getFromObject(response.getBody(), ProductResponse.class);

		} catch (Exception e) {
			logger.error("Error Occured in Product Service checkoutItems : {}", e);
			throw new ApplicationException("Error Occured in Product Service checkoutItems");
		}
	}

	public ProductResponse addNewProduct(UploadedFile imageFile, ProductRequest productDetailsRequest)
			throws ApplicationException {
		try {
			final String restURL = CONTEXT_PATH + REST_CONTROLLER_URL.ADD_NEW_PRODUCT;
			// create new directory to img upload
			createDirectory(tempLocation);
			byte[] bytes = imageFile.getContents();
			String filename = FilenameUtils.getName(imageFile.getFileName());
			BufferedOutputStream stream = new BufferedOutputStream(
					new FileOutputStream(new File(tempLocation + filename)));
			stream.write(bytes);
			stream.close();
			// Original file name
			String originalFileName = StringUtils.cleanPath(imageFile.getFileName());

			MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
			body.add("image", new FileSystemResource(createFilePath(tempLocation, originalFileName)));
			body.add("productName", productDetailsRequest.getProductDto().getName());
			body.add("productDescription", productDetailsRequest.getProductDto().getDescription());
			body.add("productPrice", productDetailsRequest.getProductDto().getPrice());
			body.add("productQuentity", productDetailsRequest.getProductDto().getQuantity());
			body.add("productStatus", productDetailsRequest.getProductDto().isStatus());

			restTemplate = new RestTemplate();
			HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, multipartHeader());
			ResponseEntity<ProductResponse> responseEntity = restTemplate.exchange(restURL, HttpMethod.POST, entity,
					ProductResponse.class);
			return responseEntity.getBody();

		} catch (Exception e) {
			logger.error("Error Occured in Product Service addNewProduct : {}", e);
			throw new ApplicationException("Error Occured in Product Service addNewProduct");
		}
	}

	private String createFilePath(String filePath, String fileName) {
		StringBuilder builder = new StringBuilder();
		builder.append(filePath);
		builder.append(fileName);
		return builder.toString();
	}

	private HttpHeaders headers() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		return headers;
	}

	private HttpHeaders multipartHeader() {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		return headers;
	}

	private void createDirectory(String tempLocation) {
		File directory = new File(tempLocation);
		if (!directory.exists()) {
			logger.info("Create directory ");
			directory.mkdirs();
		}
	}

}
