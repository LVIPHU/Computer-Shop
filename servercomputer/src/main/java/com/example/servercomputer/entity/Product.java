package com.example.servercomputer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import lombok.Data;

@Entity
@Data
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Product name is mandatory")
	@Column(nullable = false)
	private String name;
	private String description;
	@Column(nullable = false)
	private Double price;
	private Integer quantity;
	@Lob
    @Column(columnDefinition="MEDIUMBLOB")
	private String image;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Category category;
}
