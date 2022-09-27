package com.example.servercomputer.dto;

import lombok.Data;

@Data
public class CartDTO {
	private Long userId;
	private Long productId;
	private String productName;
	private String productImage;
	private Integer quantity;
	private Double price;
}
