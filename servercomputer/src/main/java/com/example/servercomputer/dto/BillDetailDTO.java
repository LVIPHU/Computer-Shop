package com.example.servercomputer.dto;

import lombok.Data;

@Data
public class BillDetailDTO {
	private Long billId;
	private Long productId;
	private String productName;
	private Integer quantity;
	private Double price;
}
