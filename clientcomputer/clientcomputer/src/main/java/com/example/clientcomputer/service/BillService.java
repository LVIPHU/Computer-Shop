package com.example.clientcomputer.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.example.clientcomputer.model.Bill;
import com.example.clientcomputer.model.BillDetail;
import com.example.clientcomputer.model.enums.EStatusBill;

public interface BillService {
	String saveBill(Bill bill, HttpSession session);
	
	List<Bill> getBills(HttpSession session);

	List<Bill> getBillByEmail(HttpSession session);

	void changeStatusBill(Long id, EStatusBill statusBill, HttpSession session);

	List<BillDetail> getBillDetailsByBillId(Long billId, HttpSession session);

	List<Bill> getBillFilterByEmailAndTime(LocalDateTime startDate, LocalDateTime endDate, HttpSession session);
}
