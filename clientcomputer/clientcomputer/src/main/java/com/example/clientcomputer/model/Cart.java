package com.example.clientcomputer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Cart{
	private Long userId;
	private Long productId;
	private String productName;
	private String productImage;
	private Integer quantity;
	private Double price;
}
