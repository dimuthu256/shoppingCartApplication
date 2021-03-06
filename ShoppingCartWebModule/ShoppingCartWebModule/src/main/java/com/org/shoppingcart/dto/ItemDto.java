package com.org.shoppingcart.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto implements Serializable{

	private static final long serialVersionUID = 1L;
	private ProductDto productDto;
	private int quantity;
	private double totalAmount;

}