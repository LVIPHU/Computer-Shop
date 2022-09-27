package com.example.clientcomputer.model;

import lombok.Data;

@Data
public class BillDetail{
	private Long billId;
	private Long productId;
	private String productName;
	private Integer quantity;
	private Double price;
}
