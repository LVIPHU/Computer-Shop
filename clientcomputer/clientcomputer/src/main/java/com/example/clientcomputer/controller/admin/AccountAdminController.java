package com.example.clientcomputer.controller.admin;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("admin/")
@AllArgsConstructor
public class AccountAdminController {
	private UserService userService;
	
	@GetMapping("admin-account")
	public String getAccount() {
		return "admin/account";
	}
	
	@GetMapping("admin-account-add")
	public String getAddAccount() {
		return "admin/addaccount";
	}
	
	@ModelAttribute("users")
	public List<Users> getUsers(HttpSession session){
		return userService.findAllUsers(session);
	}
}
