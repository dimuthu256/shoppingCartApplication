package com.org.shoppingcart.core.bean;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private int id;
	@NotNull(message = "name cannot be null")
	@ApiModelProperty(required = true, value = "required field")
	private String name;
	@NotNull(message = "price cannot be null")
	@ApiModelProperty(required = true, value = "required field")
	private double price;
	@NotNull(message = "quantity cannot be null")
	@ApiModelProperty(required = true, value = "required field")
	private int quantity;
	private byte[] image;
	private String description;
	private boolean status;

}
