package com.example.clientcomputer.model;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.clientcomputer.model.enums.EGender;
import com.example.clientcomputer.model.enums.EStatusUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Users {
	private Long id;
	@NotBlank(message = "Vui lòng nhập email")
	private String email;
	@NotBlank(message = "Vui lòng nhập mật khẩu")
	private String password;
	@NotBlank(message = "Vui lòng nhập tên")
	private String firstName;
	@NotBlank(message = "Vui lòng nhập họ")
	private String lastName;
	@NotBlank(message = "Vui lòng nhập số ĐT")
	private String phoneNumber;
	private String address;
	private EGender gender;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birthday;
	private EStatusUser status;
	private Long roleId;
}
