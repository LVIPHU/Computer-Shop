package com.example.servercomputer.service;

import java.util.List;

import com.example.servercomputer.dto.BillDTO;
import com.example.servercomputer.dto.BillDetailDTO;
import com.example.servercomputer.entity.entityenum.EStatusBill;

public interface BillService {

	List<BillDTO> getBillByUser(Long userId);

	BillDTO save(BillDTO bill);

	void deleteBill(Long id);

	List<BillDTO> getAllBill();

	List<BillDTO> getByEmail(String userMail);

	void approveBill(Long id, EStatusBill statusBill);

	List<BillDetailDTO> getBillDetailsByBillId(Long billId);

}
