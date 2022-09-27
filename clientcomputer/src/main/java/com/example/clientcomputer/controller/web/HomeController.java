package com.example.clientcomputer.controller.web;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class HomeController {
	private UserService userService;
	
	@GetMapping
	public String getHome() {
		return "web/index";
	}
	
	@GetMapping("/blank")
	public String getBlank() {
		return "web/blank"; 
	}
	
	@GetMapping("/information")
	public String getInformation(@RequestParam(required = false) String action,
								Model model, HttpSession session) {
		if(action!=null) {
			model.addAttribute("edit", true);
		}
		String userMail = (String) session.getAttribute("userMail");
		if(userMail != null) {
			model.addAttribute("user", userService.findByEmail(userMail, session));			
			return "web/information";
		}
		return "redirect:/login";
	}
	
	@PostMapping("/information/{id}")
	public String updateInformation(@PathVariable Long id, @ModelAttribute Users user, HttpSession session) {
		userService.updateInformation(id, user, session);
		
		return "redirect:/information";
	}
	
	@GetMapping("/change-password")
	public String getChangePassword() {
		return "web/change-password";
	}
	
	@PostMapping("/change-password")
	public String changePassword(@RequestParam String oldPass,
								@RequestParam String newPass,
								@RequestParam String rePass,
								HttpSession session,
								Model model) {
		String userMail = (String) session.getAttribute("userMail");
		if(oldPass.isBlank() || newPass.isBlank() || rePass.isBlank()) {
			model.addAttribute("message", "Không được bỏ trống bất kì trường nào");
			return "web/change-password";
		} 
		if(!userService.checkPassword(userMail, oldPass, session)) {
			model.addAttribute("message", "Không đúng mật khẩu cũ");
			return "web/change-password";
		}
		if(!newPass.equals(rePass)) {
			model.addAttribute("message", "Sai mật khẩu xác nhận");
			return "web/change-password";
		}
		
		userService.changePassword(userMail, newPass, session);
		
		return "redirect:/";
	}
	
	@GetMapping("/forget-pass")
	public String getForget(Model model) {
		model.addAttribute("userLogin", new Users());
		return "web/forget-pass";
	}
}
