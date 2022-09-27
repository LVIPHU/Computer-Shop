package com.example.servercomputer.dto;

import java.time.LocalDate;

import com.example.servercomputer.entity.entityenum.EGender;
import com.example.servercomputer.entity.entityenum.EStatusUser;

import lombok.Data;

@Data
public class UserDTO {
	private Long id;
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	private String address;
	private EGender gender;
	private LocalDate birthday;
	private EStatusUser status;
	private Long roleId;
}
