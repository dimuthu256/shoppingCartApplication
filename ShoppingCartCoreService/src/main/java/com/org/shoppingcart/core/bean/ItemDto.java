package com.org.shoppingcart.core.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {

	private ProductDto productDto;
	private int quantity;
	private double totalAmount;
	
}