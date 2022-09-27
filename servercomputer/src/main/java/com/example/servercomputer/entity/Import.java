package com.example.servercomputer.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

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

import lombok.Data;

@Entity
@Data
public class Import implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(nullable = false)
	private LocalDateTime createdDate;
	private String note;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "User_Id", referencedColumnName = "id")
	private User user;
	
	@OneToMany(mappedBy = "importId")
	private List<ImportDetail> importDetails;
	
	@PrePersist
	public void prePersist() {
		this.createdDate = LocalDateTime.now();
	}
}
