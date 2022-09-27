package com.example.servercomputer.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class ProductDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String name;
	private String description;
	private Double price;
	private Integer quantity;
	private String image;
	private Long categoryId;
	private String categoryName;
}
