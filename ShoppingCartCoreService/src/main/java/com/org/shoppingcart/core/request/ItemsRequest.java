package com.org.shoppingcart.core.request;

import java.io.Serializable;
import java.util.List;

import com.org.shoppingcart.core.bean.ItemDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemsRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<ItemDto> itemList;

}
