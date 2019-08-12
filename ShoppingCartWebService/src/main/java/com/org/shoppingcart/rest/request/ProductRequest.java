package com.org.shoppingcart.rest.request;

import java.io.Serializable;

import com.org.shoppingcart.rest.bean.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	private ProductDto productDto;
}
