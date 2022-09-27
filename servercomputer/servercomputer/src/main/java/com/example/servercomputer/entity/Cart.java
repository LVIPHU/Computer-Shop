package com.example.servercomputer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.ColumnDefault;

import com.example.servercomputer.entity.entitypk.CartPK;

import lombok.Data;

@Entity
@Data
public class Cart implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private CartPK id = new CartPK();
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "Product_id",referencedColumnName = "id")
	private Product product;
	
	@ManyToOne
	@MapsId("userId")
	@JoinColumn(name = "User_id", referencedColumnName = "id")
	private User user;
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Integer quantity;
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Double price;
	@Column(columnDefinition = "MEDIUMBLOB", nullable = false)
	private String image;
}