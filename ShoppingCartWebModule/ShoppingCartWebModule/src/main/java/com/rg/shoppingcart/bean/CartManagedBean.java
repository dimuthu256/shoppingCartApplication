package com.rg.shoppingcart.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import org.apache.log4j.Logger;

import com.org.shoppingcart.dto.ItemDto;
import com.org.shoppingcart.dto.ProductDto;
import com.org.shoppingcart.request.ItemsRequest;
import com.org.shoppingcart.response.ProductResponse;
import com.org.shoppingcart.service.impl.ProductServiceImpl;

@SessionScoped
@ManagedBean
public class CartManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private ProductResponse productResponse;
	private ItemsRequest productDtos;

	private List<ItemDto> itemDtos;

	private static final Logger logger = Logger.getLogger(CartManagedBean.class);

	public CartManagedBean() {
		this.itemDtos = new ArrayList<>();
		productDtos = new ItemsRequest();
		productResponse = new ProductResponse();
	}

	// add a selected item to the cart + items quantity increasing
	public String buy(ProductDto productDto) {
		int index = this.exists(productDto);
		if (index == -1) {
			this.itemDtos.add(new ItemDto(productDto, 1, productDto.getPrice()));
		} else {
			int quantity = this.itemDtos.get(index).getQuantity() + 1;
			double totalItemAmount = this.itemDtos.get(index).getTotalAmount() + productDto.getPrice();
			this.itemDtos.get(index).setQuantity(quantity);
			this.itemDtos.get(index).setTotalAmount(totalItemAmount);
		}
		return "cart?faces-redirect=true";
	}

	// delete a selected item from the cart
	public String delete(ProductDto productDto) {
		int index = this.exists(productDto);
		this.itemDtos.remove(index);
		return "cart?faces-redirect=true";
	}

	// total amount of the selected items
	public double total() {
		double amount = 0;
		for (ItemDto itemDto : this.itemDtos) {
			amount += itemDto.getProductDto().getPrice() * itemDto.getQuantity();
		}
		return amount;
	}

	private int exists(ProductDto productDto) {
		for (int i = 0; i < this.itemDtos.size(); i++) {
			if (this.itemDtos.get(i).getProductDto().getId() == productDto.getId()) {
				// if found a selected item in the list
				return i;
			}
		}
		return -1;
	}

	// checkout selected items
	public String checkout() {
		logger.info("Begin Method Checkout Item List.");
		ProductServiceImpl productServiceImpl = new ProductServiceImpl();
		try {
			List<ItemDto> itemDtosList = this.itemDtos;
			this.productResponse = productServiceImpl
					.checkoutItems(ItemsRequest.builder().itemList(itemDtosList).build());
			this.productResponse.setStatus(productResponse.getStatusCode() == 200 ? "Success" : "Failed");
		} catch (Exception e) {
			logger.error("Error in Checkout Item List. " + e.getMessage());
			this.productResponse.setStatus("Failed");
		}
		this.itemDtos = new ArrayList<>();
		logger.info("End Method Checkout Item List.");
		return "status?faces-redirect=true";
	}

	public List<ItemDto> getItemDtos() {
		return itemDtos;
	}

	public void setItemDtos(List<ItemDto> itemDtos) {
		this.itemDtos = itemDtos;
	}

	public ProductResponse getProductResponse() {
		return productResponse;
	}

	public void setProductResponse(ProductResponse productResponse) {
		this.productResponse = productResponse;
	}

	public ItemsRequest getProductDtos() {
		return productDtos;
	}

	public void setProductDtos(ItemsRequest productDtos) {
		this.productDtos = productDtos;
	}

}