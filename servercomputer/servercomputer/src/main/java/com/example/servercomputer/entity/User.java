package com.example.servercomputer.entity;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

import com.example.servercomputer.entity.entityenum.EGender;
import com.example.servercomputer.entity.entityenum.EStatusUser;

import lombok.Data;

@Entity
@Data
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@NotBlank(message = "Email is mandatory")
	@Column(nullable = false, unique = true)
	private String email;
	@NotBlank(message = "Password is mandatory")
	@Column(nullable = false)
	private String password;
	@NotBlank(message = "First Name is mandatory")
	@Column(nullable = false)
	private String firstName;
	@NotBlank(message = "Last Name is mandatory")
	@Column(nullable = false)
	private String lastName;
	private String phoneNumber;
	private String address;
	private EGender gender;
	private LocalDate birthday;
	private EStatusUser status;
	private String resetPassToken;
	@ManyToOne
	@JoinColumn(referencedColumnName = "id", nullable = false)
	private Role role;
}