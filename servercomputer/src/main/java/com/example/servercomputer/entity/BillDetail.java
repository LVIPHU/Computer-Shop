package com.example.servercomputer.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.ColumnDefault;

import com.example.servercomputer.entity.entitypk.BillDetailPK;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;

@Entity
@Data
public class BillDetail implements Serializable{
	private static final long serialVersionUID = 1L;
	@EmbeddedId
	private BillDetailPK id = new BillDetailPK();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("billId")
	@JoinColumn(name = "Bill_id",referencedColumnName = "id")
	@JsonBackReference
	private Bill bill;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("productId")
	@JoinColumn(name = "Product_id", referencedColumnName = "id")
	private Product product;
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Integer quantity;
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Double price;
}
