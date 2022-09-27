package com.example.clientcomputer.model;

import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class UserLogin {
	@NotEmpty(message = "Vui lòng nhập email")
	private String email;
	@NotEmpty(message = "Vui lòng nhập mật khẩu")
	private String password;
}
