package com.example.clientcomputer.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clientcomputer.model.Import;
import com.example.clientcomputer.model.Users;
import com.example.clientcomputer.service.ImportService;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("admin")
@AllArgsConstructor
public class ImportController {
	private ImportService importService;
	private UserService userService;
	
	@GetMapping("/admin-product-import")
	public String getImportView(Model model, HttpSession session) {
		Import import1 = new Import();
		Users users = userService.findByEmail((String) session.getAttribute("userMail"), session);
		model.addAttribute("import", import1);
		model.addAttribute("userId", users.getId());
		return "admin/productimport";
	}
	
	@PostMapping("/admin-product-import")
	public String addImportDetail(@ModelAttribute("import") Import importObject,
								HttpSession session) {
		System.out.println(importObject);
		importService.save(importObject, session);
		
		return "redirect:/admin/admin-product-import";
	}
}
