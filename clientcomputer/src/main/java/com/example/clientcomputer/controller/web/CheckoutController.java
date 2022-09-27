package com.example.clientcomputer.controller.web;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.clientcomputer.model.Bill;
import com.example.clientcomputer.payment.PaymentService;
import com.example.clientcomputer.service.BillService;
import com.example.clientcomputer.service.UserService;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("checkout")
@AllArgsConstructor
public class CheckoutController {
	private UserService userService;
	private BillService billService;
	private PaymentService paymentService;
	
	@GetMapping
	public String getCheckout(Model model, HttpSession session) {
		model.addAttribute("user", userService.findById(1L, session));
		model.addAttribute("bill", new Bill());
		return "web/checkout";
	}
	
	@PostMapping
	public String saveBill(@ModelAttribute Bill bill,
						HttpSession session) {
		return billService.saveBill(bill, session);
	}
	
	@PostMapping("/vnpay")
	public String payBill(@ModelAttribute Bill bill,
						HttpServletRequest request) throws UnsupportedEncodingException {
		return "redirect:"+paymentService.payWithVNPAY(bill, request);
	}
}
