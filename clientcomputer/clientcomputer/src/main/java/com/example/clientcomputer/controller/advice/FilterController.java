package com.example.clientcomputer.controller.advice;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clientcomputer.config.VerifyJWT;

@Controller
@RequestMapping("/admin/*")
public class FilterController {
	@GetMapping
	public String filter(HttpSession session) {
		String token = (String) session.getAttribute("token");
		if(token == null) {
			return "redirect:/login";
		}
		String role = VerifyJWT.checkRole(token);
		System.out.println(role);
		if(!role.equals("ROLE_ADMIN")) {
			return "common/page401";
		}
		return "redirect:/admin/";
	}
}
