package com.org.shoppingcart.core.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.org.shoppingcart.core.bean.DataDto;
import com.org.shoppingcart.core.bean.ItemDto;
import com.org.shoppingcart.core.bean.ProductDto;
import com.org.shoppingcart.core.config.MessageConfig;
import com.org.shoppingcart.core.entities.OrderDetails;
import com.org.shoppingcart.core.entities.Products;
import com.org.shoppingcart.core.exception.ApplicationException;
import com.org.shoppingcart.core.repository.OrderDetailsRepository;
import com.org.shoppingcart.core.repository.ProductsRepository;
import com.org.shoppingcart.core.request.ItemsRequest;
import com.org.shoppingcart.core.request.ProductDetailsRequest;
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.response.ProductResponse;
import com.org.shoppingcart.core.service.ShoppingCartService;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
public class ShoppingCartServiceImpl implements ShoppingCartService {

	private Logger logger;

	@Autowired
	private ProductsRepository productsRepository;

	@Autowired
	private OrderDetailsRepository orderDetailsRepository;

	@Autowired
	private MessageConfig messageConfig;

	@Autowired
	public ShoppingCartServiceImpl(ProductsRepository productsRepository,
			OrderDetailsRepository orderDetailsRepository) {
		super();
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.productsRepository = productsRepository;
		this.orderDetailsRepository = orderDetailsRepository;
	}

	@Override
	public ProductResponse findAllProducts() throws ApplicationException {
		try {
			logger.info("Begin method find all products");
			DataDto dataDto = new DataDto();
			dataDto.setProductList(addProductDetails(this.productsRepository.findByStatus(true)));
			return ProductResponse.builder().dataDto(dataDto).status(messageConfig.getSuccess()).statusCode(200)
					.build();
		} catch (Exception e) {
			throw new ApplicationException("Error in find all products");
		}
	}

	@Override
	public ProductResponse findProductById(int productId) throws ApplicationException {
		try {
			logger.info("Begin method find all products");
			DataDto dataDto = new DataDto();
			Optional<Products> product = this.productsRepository.findById(productId);
			if (product.isPresent()) {
				List<Products> productList = new ArrayList<>();
				productList.add(product.get());
				dataDto.setProductList(addProductDetails(productList));
			} else {
				throw new ApplicationException(messageConfig.getProductDetailsNotFound());
			}
			return ProductResponse.builder().dataDto(dataDto).status(messageConfig.getSuccess()).statusCode(200)
					.build();
		} catch (Exception e) {
			throw new ApplicationException("Error in find all products");
		}
	}

	public ProductResponse addNewProducts(ProductDetailsRequest productDetailsRequest) throws ApplicationException {
		try {
			logger.info("Begin method add products");
			ProductResponse response = new ProductResponse();
			if (!productDetailsRequest.getProductList().isEmpty()) {
				this.productsRepository.saveAll(generateAddPayload(productDetailsRequest.getProductList()));
				response.setStatus(messageConfig.getSuccess());
				response.setStatusCode(200);
			}
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Error in add products");
		}
	}

	public ProductResponse updateProductById(ProductRequest productRequest) throws ApplicationException {
		try {
			logger.info("Begin method update product");
			ProductResponse response = new ProductResponse();
			Optional<Products> productById = this.productsRepository.findById(productRequest.getProductDto().getId());
			if (productById.isPresent()) {
				this.productsRepository.save(generateUpdatePayload(productById.get(), productRequest.getProductDto()));
				response.setStatus(messageConfig.getSuccess());
				response.setStatusCode(200);
			} else {
				response.setStatus(messageConfig.getProductDetailsNotFound());
				response.setStatusCode(0);
			}
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Error in update product");
		}
	}

	@Override
	public ProductResponse deleteProductById(int productRequest) throws ApplicationException {
		try {
			logger.info("Begin method delete product");
			ProductResponse response = new ProductResponse();
			List<OrderDetails> orders = orderDetailsRepository.findAllByProductsId(productRequest);
			if (!orders.isEmpty()) {
				this.orderDetailsRepository.deleteAll(orders);
			}
			Optional<Products> productById = this.productsRepository.findById(productRequest);
			if (productById.isPresent()) {
				this.productsRepository.delete(productById.get());
				response.setStatus(messageConfig.getSuccess());
				response.setStatusCode(200);
			} else {
				response.setStatus(messageConfig.getProductDetailsNotFound());
				response.setStatusCode(0);
			}
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Error in delete product");
		}
	}

	private List<Products> generateAddPayload(List<ProductDto> reqProductList) {
		List<Products> productDetailsList = new ArrayList<>();
		for (ProductDto productList : reqProductList) {
			Products product = new Products();
			product.setName(productList.getName());
			product.setPrice(productList.getPrice());
			product.setQuantity(productList.getQuantity());
			product.setDescription(productList.getDescription());
			product.setImage(productList.getImage());
			product.setStatus(productList.isStatus());
			productDetailsList.add(product);
		}
		return productDetailsList;
	}

	private Products generateUpdatePayload(Products productById, ProductDto productList) {
		Products product = productById;
		product.setName(productList.getName());
		product.setPrice(productList.getPrice());
		product.setQuantity(productList.getQuantity());
		product.setDescription(productList.getDescription());
		product.setImage(productList.getImage());
		product.setStatus(productList.isStatus());
		return product;
	}

	@Override
	public boolean saveAllItems(ItemsRequest request) throws ApplicationException {
		try {
			logger.info("Begin method save all items : {}", request);
			List<ItemDto> itemDetailsList = request.getItemList();
			List<OrderDetails> orderDetailsList = new ArrayList<>();
			if (!itemDetailsList.isEmpty()) {
				for (ItemDto item : itemDetailsList) {
					Optional<Products> poductById = this.productsRepository.findById(item.getProductDto().getId());
					if (poductById.isPresent()) {
						double currentProductQuantity = poductById.get().getQuantity();
						updateProductQuentity(poductById.get(), item);
						orderDetailsList.add(addOrderDetails(poductById.get(), item, currentProductQuantity));
					} else {
						logger.error("Error : Product Id Not Found : PID = {}", item.getProductDto().getId());
						throw new ApplicationException("Product Details Not Found");
					}
				}
			}
			this.orderDetailsRepository.saveAll(orderDetailsList);
			logger.info("End method save all items");
			return true;
		} catch (Exception e) {
			throw new ApplicationException(messageConfig.getProductDetailsAddingFailed());
		}
	}

	private List<ProductDto> addProductDetails(List<Products> productList) throws ApplicationException {
		logger.info("Begin method add product details");
		List<ProductDto> productDtoList = new ArrayList<>();
		try {
			for (Products product : productList) {
				productDtoList.add(ProductDto.builder().id(product.getId()).name(product.getName())
						.description(product.getDescription()).price(product.getPrice()).quantity(product.getQuantity())
						.status(product.isStatus()).build());
			}
		} catch (Exception e) {
			throw new ApplicationException(messageConfig.getProductDetailsAddingFailed());
		}
		logger.info("End Method add product details : {}", productDtoList);
		return productDtoList;
	}

	private OrderDetails addOrderDetails(Products productDetails, ItemDto item, double currentProductQuantity)
			throws ApplicationException {
		logger.info("Begin method add order details");
		try {

			// if item quantity is lager than product quantity , item quantity should be
			// equal product quantity.and item amount should be calculated with the current
			// quantity
			double newItemsAmount = 0;
			if (item.getQuantity() > currentProductQuantity) {
				newItemsAmount = item.getTotalAmount() / item.getQuantity() * currentProductQuantity;
				item.setQuantity((int) currentProductQuantity);
			} else {
				newItemsAmount = item.getTotalAmount();
			}
			return OrderDetails.builder().name(productDetails.getName()).products(productDetails).amount(newItemsAmount)
					.quantity(item.getQuantity()).description(productDetails.getDescription()).status(true).build();
		} catch (Exception e) {
			throw new ApplicationException("Order details adding failed");
		}
	}

	private void updateProductQuentity(Products product, ItemDto item) throws ApplicationException {
		try {
			logger.info("Begin method update product quentity");
			// check whether product is still available with the selected quantity
			if (product.getQuantity() >= item.getQuantity()) {
				product.setQuantity(product.getQuantity() - item.getQuantity());
				productsRepository.save(product);
				logger.info("End method update product quentity");
			} else {
				product.setQuantity(0);
				product.setStatus(false);
				productsRepository.save(product);
			}
		} catch (Exception e) {
			throw new ApplicationException(messageConfig.getProductDetailsAddingFailed());
		}
	}

}