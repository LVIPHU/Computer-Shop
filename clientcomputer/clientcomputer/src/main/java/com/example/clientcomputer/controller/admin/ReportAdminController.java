package com.example.clientcomputer.controller.admin;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clientcomputer.service.ReportService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/admin/")
@AllArgsConstructor
public class ReportAdminController {
	private ReportService reportService;
	
	@GetMapping("admin-sales/product")
	public String getSales(Model model, HttpSession session) {
		model.addAttribute("sales", reportService.getSalesByProduct(session));
		return "admin/salesproduct";
	}
	
	@GetMapping("admin-sales/time")
	public String getSalesTime(Model model, HttpSession session) {
		LocalDate startDate = LocalDate.now().minusMonths(1);
		LocalDate endDate = LocalDate.now().plusMonths(1);
		model.addAttribute("sales", reportService.getSalesByTime(startDate, endDate, session));
		return "admin/salestime";
	}
}
