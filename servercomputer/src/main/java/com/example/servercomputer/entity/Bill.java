package com.example.servercomputer.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotBlank;

import com.example.servercomputer.entity.entityenum.EStatusBill;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;

@Entity
@Data
public class Bill implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private LocalDateTime createDate;
	@Column(nullable = false)
	private EStatusBill status;
	@Column(nullable = false)
	private Long amount;
	private String note;
	@NotBlank(message = "Received address is mandatory")
	@Column(nullable = false)
	private String receivedAddress;
	@NotBlank(message = "Received phone is mandatory")
	@Column(nullable = false)
	private String receivedPhoneNo;
	
	@Column(nullable = false)
	private String payment;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_id", nullable = false)
	private User user;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy = "bill", fetch = FetchType.LAZY)
	@JsonManagedReference
	private List<BillDetail> billDetails;
	
	@PrePersist
	public void postPersist() {
		this.createDate = LocalDateTime.now();
	}
}
