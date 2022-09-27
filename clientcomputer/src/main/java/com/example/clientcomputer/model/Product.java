package com.example.clientcomputer.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Product implements Serializable{
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
