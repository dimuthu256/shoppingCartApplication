package com.org.shoppingcart.core.service.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
import com.org.shoppingcart.core.request.ProductRequest;
import com.org.shoppingcart.core.response.ImageResponse;
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

	@Value("${image.save.path}")
	private String imagePath;

	@Value("${service.url.path}")
	private String serviceUrlPath;

	@Autowired
	public ShoppingCartServiceImpl(ProductsRepository productsRepository,
			OrderDetailsRepository orderDetailsRepository) {
		super();
		this.logger = LoggerFactory.getLogger(this.getClass());
		this.productsRepository = productsRepository;
		this.orderDetailsRepository = orderDetailsRepository;
	}

	@Override
	public ProductResponse addNewProduct(MultipartFile multipartFile, ProductRequest productRequest)
			throws ApplicationException {
		try {
			logger.info("Begin method add products");
			ProductResponse response = new ProductResponse();
			if (productRequest != null) {
				Products productResponse = this.productsRepository.save(generateAddPayload(
						productRequest.getProductDto(), multipartFile == null ? null : saveImage(multipartFile)));
				if (productResponse.getId() > 0) {
					response.setStatus(messageConfig.getSuccess());
					response.setStatusCode(200);
				}
			}
			return response;
		} catch (Exception e) {
			throw new ApplicationException("Error in add products");
		}
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

	@Override
	public ProductResponse updateProductById(ProductRequest productRequest) throws ApplicationException {
		try {
			logger.info("Begin method update product by id");
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
			throw new ApplicationException("Error in update product by id");
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
						.status(product.isStatus())
						.image(serviceUrlPath.concat(product.getImage() == null ? "" : product.getImage())).build());
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

	public ImageResponse saveImage(MultipartFile multipartFile) {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		String fileNameSave = "";
		try {
			if (!multipartFile.isEmpty()) {
				String fileName = multipartFile.getOriginalFilename();
				String ext = FilenameUtils.getExtension(fileName);
				String fileNameString = FilenameUtils.getBaseName(fileName);
				fileNameSave = fileNameString + new Date().getTime() + "." + ext;
				String imagePathString = imagePath + fileNameSave;
				int read = 0;
				byte[] bytes = new byte[1024];

				createDerectory(imagePath);
				inputStream = multipartFile.getInputStream();
				outputStream = new FileOutputStream(new File(imagePathString));
				while ((read = inputStream.read(bytes)) != -1) {
					outputStream.write(bytes, 0, read);
				}
			}
		} catch (IOException e) {
			logger.error("Error : {}", e.getMessage());
			return null;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					logger.error("Error : {}", e.getMessage());
					return null;
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					logger.error("Error : {}", e.getMessage());
					return null;
				}
			}
		}
		return new ImageResponse("/images/" + fileNameSave);
	}

	private Products generateAddPayload(ProductDto reqProductList, ImageResponse saveImgDetails) {
		Products product = new Products();
		product.setName(reqProductList.getName());
		product.setPrice(reqProductList.getPrice());
		product.setQuantity(reqProductList.getQuantity());
		product.setDescription(reqProductList.getDescription());
		product.setImage(saveImgDetails != null ? saveImgDetails.getImageUrl() : null);
		product.setStatus(reqProductList.isStatus());

		return product;
	}

	private Products generateUpdatePayload(Products productById, ProductDto productList) {
		Products product = productById;
		product.setName(productList.getName());
		product.setPrice(productList.getPrice());
		product.setQuantity(productList.getQuantity());
		product.setDescription(productList.getDescription());
		product.setImage(productList.getImage() == null ? productById.getImage() : productList.getImage());
		product.setStatus(productList.isStatus());
		return product;
	}

	private void createDerectory(String tempLocation) {
		File directory = new File(tempLocation);
		if (!directory.exists()) {
			logger.info("Create directory ");
			directory.mkdirs();
		}
	}

}