package com.example.servercomputer.entity;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.ColumnDefault;

import com.example.servercomputer.entity.entitypk.ImportDetailPK;

import lombok.Data;

@Entity
@Data
public class ImportDetail {
	@EmbeddedId
	private ImportDetailPK id = new ImportDetailPK();
	
	@ManyToOne
	@MapsId("importId")
	@JoinColumn(name = "Import_Id",referencedColumnName = "id")
	private Import importId;
	@ManyToOne
	@MapsId("productId")
	@JoinColumn(name = "Product_id",referencedColumnName = "id")
	private Product product;
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Integer quantity;
	@Column(nullable = false)
	@ColumnDefault(value = "0")
	private Double price;
}
