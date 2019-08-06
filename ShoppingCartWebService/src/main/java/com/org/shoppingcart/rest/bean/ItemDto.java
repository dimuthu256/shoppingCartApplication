package com.org.shoppingcart.rest.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemDto {

	private ProductDto productDto;
	private int quantity;
	private double totalAmount;
	
}