package com.org.shoppingcart.request;

import java.io.Serializable;
import java.util.List;

import com.org.shoppingcart.dto.ItemDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemsRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private List<ItemDto> itemList;

}
