package com.example.servercomputer.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.servercomputer.dto.BillDTO;
import com.example.servercomputer.dto.BillDetailDTO;
import com.example.servercomputer.entity.entityenum.EStatusBill;
import com.example.servercomputer.service.BillService;

@RestController
@RequestMapping("/api/bill")
public class BillAPI {
	private BillService billService;

	public BillAPI(BillService billService) {
		super();
		this.billService = billService;
	}

	@GetMapping("/{userId}")
	public List<BillDTO> getBill(@PathVariable Long userId) {
		return billService.getBillByUser(userId);
	}

	@PostMapping
	public BillDTO save(@Valid @RequestBody BillDTO bill) {
		return billService.save(bill);
	}

	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
		billService.deleteBill(id);
	}
	
	@GetMapping
	public List<BillDTO> getAllBills() {
		return billService.getAllBill();
	}
	
	@GetMapping("/tracking")
	public List<BillDTO> getBillByEmail(@RequestParam String userMail) {
		return billService.getByEmail(userMail);
	}
	
	@GetMapping("/tracking/{billId}")
	public List<BillDetailDTO> getBillDetailsByBillId(@PathVariable Long billId) {
		return billService.getBillDetailsByBillId(billId);
	}
	
	@PutMapping("/{id}")
	public void approveBill(@PathVariable Long id, @RequestParam EStatusBill statusBill){
		billService.approveBill(id, statusBill);
	}
}
