package com.org.shoppingcart.rest.response;

import java.io.Serializable;

import com.org.shoppingcart.rest.bean.DataDto;

import lombok.Data;

@Data
public class ProductResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private DataDto dataDto;
	private String status;
	private int statusCode;
	
	public ProductResponse(DataDto dataDto, String status, int statusCode) {
		super();
		this.dataDto = dataDto;
		this.status = status;
		this.statusCode = statusCode;
	}

	public ProductResponse() {
		super();
	}
	
}
