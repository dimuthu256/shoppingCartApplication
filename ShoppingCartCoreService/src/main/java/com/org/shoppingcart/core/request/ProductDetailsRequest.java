package com.org.shoppingcart.core.request;

import java.io.Serializable;
import java.util.List;

import javax.validation.Valid;

import com.org.shoppingcart.core.bean.ProductDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailsRequest implements Serializable{

	private static final long serialVersionUID = 1L;
	@Valid
	private List<ProductDto> productList;
}
