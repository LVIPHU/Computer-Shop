package com.example.servercomputer.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servercomputer.dto.UserDTO;
import com.example.servercomputer.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserAPI {
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public UserDTO getUser(@PathVariable Long id) {
		return userService.findOneById(id);
	}
	
	@GetMapping
	public List<UserDTO> getAllUser() {
		return userService.findAll();
	}
	
	@GetMapping("/mail")
	public UserDTO getUserByEmail(@RequestParam String userMail) {
		return userService.findByEmail(userMail);
	}
	
	@PostMapping
	public UserDTO signUp(@Valid @RequestBody UserDTO user) {
		return userService.save(user);
	}
	
//	@PostMapping("/login")
//	public UserDTO login(@RequestBody UserDTO user) {
//		return userService.login(user);
//	}
	
	@PutMapping("/password")
	public UserDTO changePassword(@RequestBody UserDTO user) {
		return userService.changePassword(user);
	}
	
	@PutMapping("/{id}")
	public UserDTO updateUser(@PathVariable Long id, @RequestBody UserDTO user) {
		user.setId(id);
		return userService.save(user);
	}
	
	@PutMapping("/resetToken")
	public void updateResetPasswordToken(@RequestParam String token) {
		String array[] = token.split("_");
		String email = array[0];
		token = array[1];
		userService.createResetToken(token, email);
	}
	
	@GetMapping("/resetToken")
	public UserDTO getByToken(@RequestParam String token) {
		return userService.findByResetToken(token);
	}
	
	@PutMapping("/resetToken/{userMail}")
	public void resetPassword(@PathVariable String userMail, @RequestParam String password) {
		userService.updateNewPassword(userMail, password);
	}
}
