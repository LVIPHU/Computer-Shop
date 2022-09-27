package com.example.clientcomputer.controller.web;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.clientcomputer.service.BillService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
public class BillController {
	private BillService billService;
	
	@GetMapping("/bill")
	public String getTrackingBill(Model model, HttpSession session) {
		model.addAttribute("bills", billService.getBillByEmail(session));
		return "web/tracking-bill";
	}
	
	@GetMapping("/bill-detail/{billId}")
	public String getBillDetail(@PathVariable Long billId, Model model, HttpSession session) {
		model.addAttribute("billDetails", billService.getBillDetailsByBillId(billId, session));
		return "web/detail-bill";
	}
	
	@GetMapping("/bill/filter")
	public String getBillFilterByTime(@RequestParam String startDate,
									@RequestParam String endDate,
									Model model, 
									HttpSession session) {
		LocalDateTime start = LocalDateTime.parse(startDate, DateTimeFormatter.ISO_DATE_TIME);
		LocalDateTime end = LocalDateTime.parse(endDate, DateTimeFormatter.ISO_DATE_TIME);
		model.addAttribute("bills", billService.getBillFilterByEmailAndTime(start, end, session));
		return "web/tracking-bill";
	}
}
