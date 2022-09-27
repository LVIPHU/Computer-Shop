package com.example.clientcomputer.controller.admin;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clientcomputer.model.enums.EStatusBill;
import com.example.clientcomputer.service.BillService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("admin/")
@AllArgsConstructor
public class OrderAdminController {
	private BillService billService;
	
	@GetMapping("admin-order")
	public String getOrder(Model model, HttpSession session) {
		model.addAttribute("bills", billService.getBills(session));
		return "admin/order";
	}
	
	@GetMapping("admin-order-add")
	public String getAddOrder() {
		return "admin/addorder";
	}
	
	@GetMapping("admin-cancel")
	public String getCancelOrder() {
		return "admin/cancel";
	}
	
	@GetMapping("api-order-detail")
	public String getOrderDetail() {
		return "admin/orderdetail";
	}
	
	@GetMapping("admin-order-approve/{id}")
	public String aprroveOrder(@PathVariable Long id, 
							@RequestParam EStatusBill statusBill,
							HttpSession session) {
		billService.changeStatusBill(id, statusBill, session);
		return "redirect:/admin/admin-order";
	}
	
	@GetMapping("admin-order-cancel/{id}")
	public String cancelOrder(@PathVariable Long id,
							HttpSession session) {
		EStatusBill statusBill = EStatusBill.CANCELED;
		billService.changeStatusBill(id, statusBill, session);
		return "redirect:/admin/admin-order";
	}
}
