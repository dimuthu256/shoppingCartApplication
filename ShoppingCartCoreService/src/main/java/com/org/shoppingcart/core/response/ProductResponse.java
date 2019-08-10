package com.org.shoppingcart.core.response;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.org.shoppingcart.core.bean.DataDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ProductResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private DataDto dataDto;
	private String status;
	private int statusCode;

}
