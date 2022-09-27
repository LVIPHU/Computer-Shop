package com.example.clientcomputer.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ImportDetail {
	private Long importIdId;
	private Long productId;
	private Integer quantity;
	private Double price;
}
